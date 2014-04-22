package org.thymeleaf.extras.idea.inspection;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.javaee.web.WebRoot;
import com.intellij.javaee.web.WebUtil;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceUtil;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileSystemItemUtil;
import com.intellij.psi.jsp.WebDirectoryElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.spring.contexts.model.SpringModel;
import com.intellij.spring.model.SpringBeanPointer;
import com.intellij.spring.model.xml.DomSpringBean;
import com.intellij.spring.model.xml.DomSpringBeanPointer;
import com.intellij.spring.model.xml.mvc.Resources;
import com.intellij.spring.web.mvc.SpringMVCModel;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.SmartList;
import com.intellij.xml.XmlNamespaceHelper;
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
import static org.thymeleaf.extras.idea.util.MyXmlUtil.addAttributeBefore;
import static org.thymeleaf.extras.idea.util.MyXmlUtil.buildQName;

public class HardcodedResourceUrlInspection extends XmlSuppressableInspectionTool {
    private static final ElementPattern<XmlAttributeValue> SRC_ATTRIBUTE_PATTERN = xmlAttributeValue()
            .withParent(xmlAttribute("src"))
            .withSuperParent(2, xmlTag().andNot(xmlTag().withChild(xmlAttribute("src").withNamespace(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL))));
    private static final ElementPattern<XmlAttributeValue> HREF_ATTRIBUTE_PATTERN = xmlAttributeValue()
            .withParent(xmlAttribute("href"))
            .withSuperParent(2, xmlTag().andNot(xmlTag().withChild(xmlAttribute("href").withNamespace(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL))));
    private static final ElementPattern<XmlAttributeValue> SRC_OR_HREF_ATTRIBUTE_PATTERN = or(SRC_ATTRIBUTE_PATTERN, HREF_ATTRIBUTE_PATTERN);

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

                if (!SRC_OR_HREF_ATTRIBUTE_PATTERN.accepts(value)) {
                    return;
                }


                XmlTag tag = PsiTreeUtil.getParentOfType(value, XmlTag.class);
                if (tag == null) return;

                XmlAttribute attr = PsiTreeUtil.getParentOfType(value, XmlAttribute.class);
                if (attr == null) return;

                PsiFile referencedFile = FileReferenceUtil.findFile(value);
                if (referencedFile == null) return;

                String nsPrefix = tag.getPrefixByNamespace(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL);
                if (nsPrefix == null) return;

                String localName = attr.getLocalName();
                if (StringUtil.isEmpty(localName)) return;

                // Register an error message
                TextRange range = ElementManipulators.getValueTextRange(value);
                String message = MessageFormat.format("Use {0} attribute in addition to hardcoded URL", buildQName(nsPrefix, localName));

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

                XmlTag tag = PsiTreeUtil.getParentOfType(value, XmlTag.class, false);
                if (tag == null) return;

                // TODO What is with conditional comments, which create a separate tree?
                final XmlFile file = XmlUtil.getContainingFile(value);
                if (file == null) return;

                // TODO Handle cases where the Thymeleaf namespace doesn't exist
                String prefix = tag.getPrefixByNamespace(myNamespaceUri);

                if (StringUtil.isEmpty(prefix)) {
                    XmlNamespaceHelper extension = XmlNamespaceHelper.getHelper(file);
                    XmlNamespaceHelper.Runner<String, IncorrectOperationException> after = new XmlNamespaceHelper.Runner<String, IncorrectOperationException>() {
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
            final List<String> potentialPaths = determineWebPaths(value);

            if (potentialPaths == null || potentialPaths.isEmpty()) return;

            chooseResourceToAdd(tag, originalAttr, buildQName(prefix, originalAttr.getLocalName()), potentialPaths);

            /*if (potentialPaths.size() == 1) {
                final String newValue = potentialPaths.get(0);
                addAttributeBefore(tag, originalAttr, buildQName(prefix, originalAttr.getLocalName()), newValue);
            } else {
                chooseResourceToAdd(tag, originalAttr, buildQName(prefix, originalAttr.getLocalName()), potentialPaths);
            }*/
        }

        private static void chooseResourceToAdd(@NotNull final XmlTag tag, @NotNull final XmlAttribute originalAttr,
                                                @NotNull final String name, final List<String> potentialPaths) {
            //noinspection rawtypes
            final BaseListPopupStep<String> step = new BaseListPopupStep<String>("Resource to refer to", potentialPaths) {
                @Override
                public PopupStep onChosen(final String selectedValue, boolean finalChoice) {
                    if (selectedValue == null) {
                        return FINAL_CHOICE;
                    }

                    if (finalChoice) {
                        CommandProcessor.getInstance().executeCommand(tag.getProject(), new Runnable() {
                            @Override
                            public void run() {
                                ApplicationManager.getApplication().runWriteAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        addAttributeBefore(tag, originalAttr, name, selectedValue);
                                    }
                                });
                            }
                        }, String.format("Add %s attribute", name), null);
                        return FINAL_CHOICE;
                    }

                    // TODO Implement this
                    return FINAL_CHOICE;
                }
            };

            // TODO Use showInBestPositionFor(...)
            final ListPopup listPopup = JBPopupFactory.getInstance().createListPopup(step);
            final Editor editor = PsiUtilBase.findEditor(tag);
            if (editor == null) {
                // TODO Turn the assert into a LOG message here (or remove this conditional branch altogether)
                assert false : "Failed to get the editor";
                listPopup.showInFocusCenter();
            } else {
                listPopup.showInBestPositionFor(editor);
            }
        }

        @Nullable
        private static List<String> determineWebPaths(@NotNull XmlAttributeValue value) {
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

            // Setup the list that collects the results
            List<String> items = new SmartList<String>();

            // TODO Define something like FileToWebPathResolver (comparable to Spring's ViewResolver)

            // TODO Is there an ordering of the models inside SpringMVCModel.getServletModels()?
            // TODO Should we use SpringMVCModel.getServletModels() here, or something different?
            for (final SpringModel model : springMVCModel.getServletModels()) {
                // TODO Is there an explicit ordering in the returned list?
                for (final SpringBeanPointer pointer : model.findBeansByPsiClassWithInheritance("org.springframework.web.servlet.resource.ResourceHttpRequestHandler")) {
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
                                final String linkExpr = ThymeleafUtil.createLinkExpression(mapping.replace("**", relativePath));
                                items.add(linkExpr);
                            }
                        }
                    }
                }
            }

            return items;
        }
    }
}
