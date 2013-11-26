package org.thymeleaf.extras.idea.inspection;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.javaee.web.WebRoot;
import com.intellij.javaee.web.WebUtil;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceUtil;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileSystemItemUtil;
import com.intellij.psi.jsp.WebDirectoryElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.spring.contexts.model.SpringModel;
import com.intellij.spring.model.xml.DomSpringBean;
import com.intellij.spring.model.xml.beans.DomSpringBeanPointer;
import com.intellij.spring.model.xml.beans.SpringBaseBeanPointer;
import com.intellij.spring.model.xml.mvc.Resources;
import com.intellij.spring.web.mvc.SpringMVCModel;
import com.intellij.util.IncorrectOperationException;
import com.intellij.xml.XmlExtension;
import com.intellij.xml.util.XmlUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider;
import org.thymeleaf.extras.idea.editor.ThymeleafUtil;
import org.thymeleaf.extras.idea.integration.spring.MySpringMVCUtil;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

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
            // TODO Don't add the xml namespace declaration if adding the attribute fails!
            XmlTag tag = PsiTreeUtil.getParentOfType(value, XmlTag.class, false);
            if (tag == null) return;

            final XmlAttribute originalAttr = PsiTreeUtil.getParentOfType(value, XmlAttribute.class);
            if (originalAttr == null) return;

            // TODO Warn if adding the attribute fails, e.g. because of a missing SpringFacet?
            final String newValue = determineWebPath(value);
            if (newValue == null) return;
            final XmlElementFactory factory = XmlElementFactory.getInstance(value.getProject());
            final XmlAttribute newAttribute = factory.createXmlAttribute(buildQName(prefix, originalAttr.getLocalName()), newValue);

            tag.addBefore(newAttribute, originalAttr);
        }

        @Nullable
        private static String determineWebPath(@NotNull XmlAttributeValue value) {
            // TODO This is a hack to get the web path for the local file
            SpringMVCModel springMVCModel = MySpringMVCUtil.getSpringMVCModelForPsiElement(value);
            if (springMVCModel == null) return null;

            final PsiFile targetPsiFile = FileReferenceUtil.findFile(value);
            if (targetPsiFile == null) return null;

            final VirtualFile targetFile = targetPsiFile.getVirtualFile();
            if (targetFile == null) return null;

            final WebFacet webFacet = springMVCModel.getWebFacet();
            final WebRoot webRoot = WebUtil.findParentWebRoot(targetFile, webFacet.getWebRoots());

            if (webRoot == null) return null;

            // TODO Is there an ordering of the models inside SpringMVCModel.getServletModels()?
            // TODO Should we use SpringMVCModel.getServletModels() here, or something different?
            for (final SpringModel model : springMVCModel.getServletModels()) {
                // TODO Is there an explicit ordering in the returned list?
                for (final SpringBaseBeanPointer pointer : model.findBeansByPsiClassWithInheritance("org.springframework.web.servlet.resource.ResourceHttpRequestHandler")) {
                    if (pointer instanceof DomSpringBeanPointer) {
                        final DomSpringBean bean = ((DomSpringBeanPointer) pointer).getSpringBean();

                        if (bean instanceof Resources) {
                            final Resources resources = (Resources) bean;

                            final String mapping = resources.getMapping().getValue();
                            if (mapping == null) continue;

                            final List<PsiFileSystemItem> locations = resources.getLocation().getValue();
                            if (locations == null) continue;

                            final WebDirectoryElement targetWebDirectory = WebUtil.findWebDirectoryByFile(targetPsiFile);

                            outer_loop:
                            for (PsiFileSystemItem location : locations) {
                                // Determine if "location" is a parent of "targetPsiFile"
                                PsiFileSystemItem parent = targetWebDirectory;
                                while (!location.equals(parent)) {
                                    if (parent == null) continue outer_loop;
                                    parent = parent.getParent();
                                }
                                // Found a matching mvc:resources tag where one of the locations
                                // mentioned in @location match.

                                final String relativePath = PsiFileSystemItemUtil.getRelativePath(location, targetWebDirectory);
                                // TODO Dirty hack
                                final String result = ThymeleafUtil.createLinkExpression(mapping.replace("**", relativePath));
                                return result;
                            }
                        }
                    }
                }
                // Resources resources = (Resources)pointer.getSpringBean();
                // resources.getMapping();
            }
            return null;
        }
    }
}
