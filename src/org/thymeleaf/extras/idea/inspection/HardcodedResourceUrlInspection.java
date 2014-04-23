package org.thymeleaf.extras.idea.inspection;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.jam.JamElement;
import com.intellij.jam.JamPomTarget;
import com.intellij.javaee.web.WebRoot;
import com.intellij.javaee.web.WebUtil;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.ElementPattern;
import com.intellij.pom.PomTarget;
import com.intellij.pom.PomTargetPsiElement;
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
import com.intellij.spring.model.CommonSpringBean;
import com.intellij.spring.model.SpringBeanPointer;
import com.intellij.spring.model.SpringBeanPsiTarget;
import com.intellij.spring.model.xml.DomSpringBean;
import com.intellij.spring.model.xml.DomSpringBeanPointer;
import com.intellij.spring.model.xml.mvc.Resources;
import com.intellij.spring.model.xml.mvc.ViewController;
import com.intellij.spring.web.mvc.SpringControllerClassInfo;
import com.intellij.spring.web.mvc.SpringMVCModel;
import com.intellij.spring.web.mvc.jam.SpringMVCRequestMapping;
import com.intellij.spring.web.mvc.views.ViewResolver;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.SmartList;
import com.intellij.util.containers.MultiMap;
import com.intellij.xml.XmlNamespaceHelper;
import com.intellij.xml.util.XmlUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider;
import org.thymeleaf.extras.idea.editor.ThymeleafUtil;
import org.thymeleaf.extras.idea.integration.spring.MySpringMVCUtil;
import org.thymeleaf.extras.idea.util.MyXmlUtil;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.intellij.patterns.XmlPatterns.*;
import static org.thymeleaf.extras.idea.util.MyXmlUtil.*;

public class HardcodedResourceUrlInspection extends XmlSuppressableInspectionTool {
    private static final Logger LOGGER = Logger.getInstance(HardcodedResourceUrlInspection.class);

    private static final ElementPattern<XmlAttributeValue> SRC_ATTRIBUTE_PATTERN = buildPattern("src");
    private static final ElementPattern<XmlAttributeValue> HREF_ATTRIBUTE_PATTERN = buildPattern("href");
    private static final ElementPattern<XmlAttributeValue> ACTION_ATTRIBUTE_PATTERN = buildPattern("action");
    private static final ElementPattern<XmlAttributeValue> ANY_URL_ATTRIBUTE_PATTERN =
            or(SRC_ATTRIBUTE_PATTERN, HREF_ATTRIBUTE_PATTERN, ACTION_ATTRIBUTE_PATTERN);

    private static ElementPattern<XmlAttributeValue> buildPattern(@NonNls @NotNull String localName) {
        return xmlAttributeValue()
                .withParent(xmlAttribute(localName))
                .withSuperParent(2, xmlTag().andNot(xmlTag().withChild(xmlAttribute(localName).withNamespace(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL))));
    }

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

                if (!ANY_URL_ATTRIBUTE_PATTERN.accepts(value)) {
                    return;
                }

                String nsPrefix = getPrefixByNamespace(value, ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL);
                String localName = MyXmlUtil.getNameOfAttribute(value);
                if (nsPrefix == null || localName == null) return;

                PsiFile referencedFile = FileReferenceUtil.findFile(value);
                if (referencedFile == null) return;

                // Register an error message
                TextRange range = ElementManipulators.getValueTextRange(value);
                String message = MessageFormat.format("Use {0} attribute in addition to hardcoded URL", buildQName(nsPrefix, localName));

                holder.registerProblem(value, range, message,
                        new AddThymeleafUrlAttributeFix(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL, nsPrefix, localName));
            }
        };
    }

    private static class AddThymeleafUrlAttributeFix implements LocalQuickFix {
        private final String myNamespaceUri;
        private final String myNamespacePrefix;
        private final String myLocalName;
        //
        private final String myQuickFixName;

        private AddThymeleafUrlAttributeFix(String namespaceUri, String namespacePrefix, String localName) {
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
            final List<InsertUrlItem> potentialPaths = determineWebPaths(value);
            if (potentialPaths == null || potentialPaths.isEmpty()) return;

            if (potentialPaths.size() == 1) {
                final InsertUrlItem newValue = potentialPaths.get(0);
                addAttributeBefore(tag, originalAttr, buildQName(prefix, originalAttr.getLocalName()), newValue.getLookupString());
            } else {
                chooseResourceToAdd(tag, originalAttr, buildQName(prefix, originalAttr.getLocalName()), potentialPaths);
            }
        }

        private static void chooseResourceToAdd(@NotNull final XmlTag tag, @NotNull final XmlAttribute originalAttr,
                                                @NotNull final String newAttrName, final List<InsertUrlItem> potentialPaths) {

            //noinspection rawtypes
            final BaseListPopupStep<InsertUrlItem> step = new BaseListPopupStep<InsertUrlItem>("Resources To Refer To", potentialPaths) {
                @NotNull
                @Override
                public String getTextFor(InsertUrlItem value) {
                    return value.getPresentation();
                }

                @Override
                public PopupStep onChosen(final InsertUrlItem selectedValue, boolean finalChoice) {
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
                                        addAttributeBefore(tag, originalAttr, newAttrName, selectedValue.getLookupString());
                                    }
                                });
                            }
                        }, String.format("Add %s attribute", newAttrName), null);
                        return FINAL_CHOICE;
                    }

                    // TODO Implement this
                    return FINAL_CHOICE;
                }
            };

            final ListPopup listPopup = JBPopupFactory.getInstance().createListPopup(step);
            final Editor editor = PsiUtilBase.findEditor(tag);
            if (editor == null) {
                LOGGER.warn("Failed to get the editor");
                listPopup.showInFocusCenter();
            } else {
                listPopup.showInBestPositionFor(editor);
            }
        }

        @Nullable
        private static List<InsertUrlItem> determineWebPaths(@NotNull XmlAttributeValue value) {
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
            List<InsertUrlItem> items = new SmartList<InsertUrlItem>();

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

                                //noinspection ObjectAllocationInLoop
                                items.add(new InsertUrlItem(linkExpr, String.format("%s (%s)", linkExpr, targetFile.getPresentableUrl())));
                            }
                        }
                    }
                }

                final List<SpringMVCModel.Variant> urls = springMVCModel.getAllUrls();
                final List<ViewResolver> resolvers = springMVCModel.getViewResolvers();

                final MultiMap<PsiMethod, SpringMVCModel.Variant> method2urls = new MultiMap<PsiMethod, SpringMVCModel.Variant>() {
                    @Override
                    protected Collection<SpringMVCModel.Variant> createCollection() {
                        return new HashSet<SpringMVCModel.Variant>();
                    }
                };

                for (SpringMVCModel.Variant url : urls) //
                {
                    final PsiElement psiElement = url.psiElementPointer.getPsiElement();
                    if (!(psiElement instanceof PomTargetPsiElement)) continue;

                    final PomTarget pomTarget = ((PomTargetPsiElement) psiElement).getTarget();
                    if ((pomTarget instanceof JamPomTarget)) //
                    {
                        final JamElement jamElement = ((JamPomTarget) pomTarget).getJamElement();
                        if (!(jamElement instanceof SpringMVCRequestMapping.MethodMapping)) continue;

                        final PsiMethod psiMethod = ((SpringMVCRequestMapping.MethodMapping) jamElement).getPsiElement();
                        method2urls.putValue(psiMethod, url);
                    } //
                    else if ((pomTarget instanceof SpringBeanPsiTarget)) //
                    {
                        final CommonSpringBean springBean = ((SpringBeanPsiTarget) pomTarget).getSpringBean();
                        if (!(springBean instanceof ViewController)) continue;

                        final ViewController viewController = (ViewController) springBean;
                        for (ViewResolver resolver : resolvers) //
                        {
                            PsiElement viewPsiElement = resolver.resolveFinalView(viewController.getViewName().getValue(), springMVCModel);
                            if ((viewPsiElement != null) && (Comparing.equal(targetFile, viewPsiElement.getContainingFile().getVirtualFile()))) //
                            {
                                // TODO Improve the generated URL for HardcodedResourceUrlInspection
                                final String linkExpr = ThymeleafUtil.createLinkExpression(viewController.getPath().getValue());
                                final PsiFile containingFile = viewController.getContainingFile();
                                final String containingFileName = containingFile != null ? containingFile.getName() : "?";

                                items.add(new InsertUrlItem(linkExpr, String.format("%s (%s)", linkExpr, containingFileName)));
                            }
                        }
                    }
                }

                for (SpringBeanPointer pointer : springMVCModel.getControllers()) //
                {
                    final PsiClass beanClass = pointer.getBeanClass();
                    if (beanClass == null) continue;

                    final SpringControllerClassInfo info = SpringControllerClassInfo.getInfo(beanClass);
                    final MultiMap<String, PsiMethod> views = info.getViews(null);

                    for (String view : views.keySet()) //
                    {
                        for (ViewResolver resolver : resolvers) //
                        {
                            PsiElement psiElement = resolver.resolveFinalView(view, springMVCModel);
                            if ((psiElement != null) && (Comparing.equal(targetFile, psiElement.getContainingFile().getVirtualFile()))) //
                            {
                                for (PsiMethod method : views.get(view)) //
                                {
                                    for (SpringMVCModel.Variant url : method2urls.get(method)) {
                                        // TODO Improve the generated URL for HardcodedResourceUrlInspection
                                        final String linkExpr = ThymeleafUtil.createLinkExpression("/" + url.lookupString);
                                        final PsiFile containingFile = method.getContainingFile();
                                        final String containingFileName = containingFile != null ? containingFile.getName() : "?";

                                        items.add(new InsertUrlItem(linkExpr, String.format("%s (%s)", linkExpr, containingFileName)));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return items;
        }
    }
}
