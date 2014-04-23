package org.thymeleaf.extras.idea.integration.spring.references;

import com.intellij.codeInsight.daemon.EmptyResolveMessageProvider;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.spring.web.mvc.SpringMVCModel;
import com.intellij.spring.web.mvc.views.ViewResolver;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.integration.spring.MySpringMVCUtil;

import java.util.ArrayList;
import java.util.List;

// TODO Make ThymeleafViewReference package-scoped again
public class ThymeleafViewReference extends PsiReferenceBase<PsiElement>
        implements EmptyResolveMessageProvider {
    private static final Logger LOG = Logger.getInstance(ThymeleafViewReference.class);
    private final List<ViewResolver> myResolvers;
    private ViewResolver myResolver;

    public ThymeleafViewReference(PsiElement element, List<ViewResolver> resolvers, TextRange range, boolean soft) {
        super(element, range, soft);
        this.myResolvers = resolvers;
    }

    public PsiElement resolve() {
        SpringMVCModel model = MySpringMVCUtil.getSpringMVCModelForPsiElement(getElement());
        if (model == null) return null;

        String viewName = getCanonicalText();
        for (ViewResolver resolver : this.myResolvers) {
            PsiElement psiElement = resolver.resolveView(viewName, model);
            if (psiElement != null) {
                this.myResolver = resolver;
                return psiElement;
            }
        }
        return null;
    }

    @NotNull
    public Object[] getVariants() {
        final SpringMVCModel model = MySpringMVCUtil.getSpringMVCModelForPsiElement(getElement());
        if (model == null) {
            return EMPTY_ARRAY;
        }
        final List<LookupElement> allViews = new ArrayList<LookupElement>();

        for (final ViewResolver resolver : myResolvers) {
            allViews.addAll(resolver.getAllViews(model));
        }

        return ArrayUtil.toObjectArray(allViews);
    }

    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        LOG.assertTrue(this.myResolver != null, "Trying to bind a non-resolved reference? Resolvers: " + this.myResolvers + ", element: " + element);

        String newName = this.myResolver.bindToElement(element);
        return newName == null ? getElement() : ElementManipulators.getManipulator(getElement()).handleContentChange(getElement(), newName);
    }

    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return super.handleElementRename(this.myResolver.handleElementRename(newElementName));
    }

    @NotNull
    public String getUnresolvedMessagePattern() {
        return (this.myResolvers.isEmpty() ? "No view resolvers found" : "Cannot resolve MVC View ''{0}''");
    }
}