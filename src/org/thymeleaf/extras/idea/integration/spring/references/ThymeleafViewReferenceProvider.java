package org.thymeleaf.extras.idea.integration.spring.references;

import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.*;
import com.intellij.spring.web.mvc.SpringMVCModel;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.integration.spring.MySpringMVCUtil;
import org.thymeleaf.extras.idea.lang.fragment.selection.psi.TemplateName;

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
