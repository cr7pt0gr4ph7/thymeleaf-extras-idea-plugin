package org.thymeleaf.extras.idea.inspection;

import com.intellij.codeInsight.completion.ExtendedTagInsertHandler;
import com.intellij.codeInsight.completion.XmlAttributeInsertHandler;
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
import com.intellij.xml.XmlExtension;
import com.intellij.xml.util.XmlUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider;
import org.thymeleaf.extras.idea.editor.ThymeleafUtil;
import org.thymeleaf.extras.idea.util.MyXmlUtil;

import java.text.MessageFormat;
import java.util.Collections;

import static com.intellij.patterns.XmlPatterns.*;
import static org.thymeleaf.extras.idea.util.MyXmlUtil.buildQName;

public class HardcodedResourceUrlInspection extends XmlSuppressableInspectionTool {
    private static final ElementPattern<XmlAttributeValue> SRC_ATTRIBUTE_PATTERN = xmlAttributeValue()
            .withParent(xmlAttribute("src"))
            .withSuperParent(2, xmlTag().andNot(xmlTag().withChild(xmlAttribute("src").withNamespace(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL))));

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, final boolean isOnTheFly) {
        // TODO This is always true currently
        final boolean isXmlFileWithThymeleafSupport = ThymeleafUtil.canBeThymeleafFile(holder.getFile().getFileType());

        return new XmlElementVisitor() {
            @Override
            public void visitXmlAttributeValue(XmlAttributeValue value) {
                if (!isXmlFileWithThymeleafSupport) {
                    return;
                }

                if (!SRC_ATTRIBUTE_PATTERN.accepts(value)) {
                    return;
                }

                XmlTag tag = PsiTreeUtil.getParentOfType(value, XmlTag.class);
                if (tag == null) return;

                PsiFile referencedFile = FileReferenceUtil.findFile(value);
                if (referencedFile == null) return;

                // This is a local file reference
                TextRange range = ElementManipulators.getValueTextRange(value);
                holder.registerProblem(value, range, "Use th:src attribute in addition to hardcoded URL",
                        new AddThymeleafAttributeFix(
                                ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL,
                                tag.getPrefixByNamespace(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL),
                                "src"));
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
                XmlTag tag = PsiTreeUtil.getParentOfType(value, XmlTag.class, false);
                if (tag == null) return;

                // TODO What is with conditional comments, which create a separate tree?
                final XmlFile file = XmlUtil.getContainingFile(value);
                if (file == null) return;

                // TODO Handle cases where the Thymeleaf namespace doesn't exist
                String prefix = tag.getPrefixByNamespace(myNamespaceUri);

                if (StringUtil.isEmpty(prefix)) {
                    XmlExtension extension = XmlExtension.getExtensionByElement(value);
                    XmlExtension.Runner<String, IncorrectOperationException> after = new XmlExtension.Runner<String, IncorrectOperationException>() {
                        @Override
                        public void run(String prefix) throws IncorrectOperationException {
                            addAttribute(prefix, value);
                        }
                    };
                    extension.insertNamespaceDeclaration(file, null, Collections.singleton(myNamespaceUri), prefix, after);
                } else {
                    addAttribute(prefix, value);
                }

            }
        }

        private static void addAttribute(String prefix, XmlAttributeValue value) {
            XmlTag tag = PsiTreeUtil.getParentOfType(value, XmlTag.class, false);
            if (tag == null) return;

            final XmlAttribute originalAttr = PsiTreeUtil.getParentOfType(value, XmlAttribute.class);
            if (originalAttr == null) return;

            final String newValue = "...";
            final XmlElementFactory factory = XmlElementFactory.getInstance(value.getProject());
            final XmlAttribute newAttribute = factory.createXmlAttribute(buildQName(prefix, originalAttr.getLocalName()), newValue);

            tag.addBefore(newAttribute, originalAttr);
        }
    }
}
