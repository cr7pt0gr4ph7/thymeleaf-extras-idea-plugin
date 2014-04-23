package org.thymeleaf.extras.idea.inspection;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.IncorrectOperationException;
import com.intellij.xml.XmlNamespaceHelper;
import com.intellij.xml.util.XmlUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider;
import org.thymeleaf.extras.idea.editor.ThymeleafUtil;
import org.thymeleaf.extras.idea.util.MyXmlUtil;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import static com.intellij.patterns.StandardPatterns.or;
import static com.intellij.patterns.XmlPatterns.*;
import static org.thymeleaf.extras.idea.util.MyXmlUtil.*;

public class NonLocalizedTextInspection extends XmlSuppressableInspectionTool {
    private static final ElementPattern<XmlAttributeValue> ALT_ATTRIBUTE_PATTERN = buildPattern("alt");
    private static final ElementPattern<XmlAttributeValue> PLACEHOLDER_ATTRIBUTE_PATTERN = buildPattern("placeholder");
    private static final ElementPattern<XmlAttributeValue> ACTION_ATTRIBUTE_PATTERN = buildPattern("action");
    private static final ElementPattern<XmlAttributeValue> ANY_LOCALIZABLE_ATTRIBUTE_PATTERN =
            or(ALT_ATTRIBUTE_PATTERN, PLACEHOLDER_ATTRIBUTE_PATTERN, ACTION_ATTRIBUTE_PATTERN);



    private static ElementPattern<XmlAttributeValue> buildPattern(@NonNls @NotNull String localName) {
        return xmlAttributeValue()
                .withParent(xmlAttribute(localName))
                .withSuperParent(2, xmlTag().andNot(xmlTag().withChild(xmlAttribute(localName).withNamespace(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL))));
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(final ProblemsHolder holder, final boolean isOnTheFly) {
        // TODO This is always true currently
        final boolean isXmlFileWithThymeleafSupport = ThymeleafUtil.canBeThymeleafFile(holder.getFile().getFileType());

        return new XmlElementVisitor() {
            @Override
            public void visitXmlAttributeValue(XmlAttributeValue value) {
                if (!isXmlFileWithThymeleafSupport) {
                    return;
                }

                if (!ANY_LOCALIZABLE_ATTRIBUTE_PATTERN.accepts(value)) {
                    return;
                }

                String nsPrefix = getPrefixByNamespace(value, ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL);
                String localName = MyXmlUtil.getNameOfAttribute(value);
                if (nsPrefix == null || localName == null) return;

                PsiFile referencedFile = FileReferenceUtil.findFile(value);
                if (referencedFile == null) return;

                // Register an error message
                TextRange range = ElementManipulators.getValueTextRange(value);
                String message = MessageFormat.format("Use {0} attribute in addition to hardcoded text", buildQName(nsPrefix, localName));

                holder.registerProblem(value, range, message,
                        new AddThymeleafAttributeFix(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL, nsPrefix, localName));
            }
        };
    }



    private static class AddThymeleafAttributeFix implements LocalQuickFix {
        private final String myNamespaceUri;
        private final String myNamespacePrefix;
        private final String myLocalName;
        //
        private final String myQuickFixName;

        private AddThymeleafAttributeFix(String namespaceUri, String namespacePrefix, String localName) {
            this.myNamespaceUri = namespaceUri;
            this.myNamespacePrefix = namespacePrefix;
            this.myLocalName = localName;

            this.myQuickFixName = MessageFormat.format("Add a {0} attribute", buildQName(namespacePrefix, localName));
        }

        @NotNull
        @Override
        public String getName() {
            return myQuickFixName;
        }

        @NotNull
        @Override
        public String getFamilyName() {
            return myQuickFixName;
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            final PsiElement element = descriptor.getPsiElement();
            if (element instanceof XmlAttributeValue) {
                final XmlAttributeValue value = (XmlAttributeValue) element;

                // TODO What is with conditional comments, which create a separate tree?
                final XmlFile file = XmlUtil.getContainingFile(value);
                if (file == null) return;

                // Handle cases where the Thymeleaf namespace doesn't exist
                final String prefix = getPrefixByNamespace(value, myNamespaceUri);
                if (StringUtil.isEmpty(prefix)) {
                    // Insert namespace declaration first (xmlns:th="..." or similar)
                    XmlNamespaceHelper extension = XmlNamespaceHelper.getHelper(file);
                    XmlNamespaceHelper.Runner<String, IncorrectOperationException> after = new XmlNamespaceHelper.Runner<String, IncorrectOperationException>() {
                        @Override
                        public void run(String prefix) throws IncorrectOperationException {
                            addThymeleafAttribute(prefix, value);
                        }
                    };
                    extension.insertNamespaceDeclaration(file, null, Collections.singleton(myNamespaceUri), prefix, after);
                } else {
                    addThymeleafAttribute(prefix, value);
                }
            }
        }

        private static void addThymeleafAttribute(String prefix, XmlAttributeValue value) {
            // TODO Don't add the xml namespace declaration if adding the attribute fails!
            XmlTag tag = PsiTreeUtil.getParentOfType(value, XmlTag.class, false);
            if (tag == null) return;

            final XmlAttribute originalAttr = PsiTreeUtil.getParentOfType(value, XmlAttribute.class);
            if (originalAttr == null) return;

            // TODO Warn if adding the attribute fails, e.g. because of a missing SpringFacet?
//            final List<InsertUrlItem> potentialPaths = determineWebPaths(value);
//            if (potentialPaths == null || potentialPaths.isEmpty()) return;
//
//            if (potentialPaths.size() == 1) {
//                final InsertUrlItem newValue = potentialPaths.get(0);
//                addAttributeBefore(tag, originalAttr, buildQName(prefix, originalAttr.getLocalName()), newValue.getLookupString());
//            }
        }
    }
}
