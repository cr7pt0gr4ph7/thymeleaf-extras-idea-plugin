package org.thymeleaf.extras.idea.integration.spring;

import com.intellij.javaee.web.WebUtil;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.intellij.spring.facet.SpringFacet;
import com.intellij.spring.web.mvc.SpringMVCModel;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.fragment.selection.parser.FragmentSelectorElementTypes;
import org.thymeleaf.extras.idea.fragment.selection.psi.FragmentSelectionExpression;
import org.thymeleaf.extras.idea.fragment.selection.psi.TemplateName;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.PlatformPatterns.psiFile;

public class ThymeleafViewReferenceProvider extends PsiReferenceProvider implements DumbAware {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (!(element instanceof TemplateName)) {
            return PsiReference.EMPTY_ARRAY;
        }

        String templateName = ((TemplateName) element).getString().getText();

        SpringMVCModel model = MySpringMVCUtil.getSpringMVCModelForPsiElement(element);
        if (model != null) {
            return new PsiReference[]{new ThymeleafViewReference(element, model.getViewResolvers(), element.getTextRange(), false)};
        }
        return PsiReference.EMPTY_ARRAY;
    }

    public static void register(PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(psiElement(TemplateName.class), new ThymeleafViewReferenceProvider());
    }
}
