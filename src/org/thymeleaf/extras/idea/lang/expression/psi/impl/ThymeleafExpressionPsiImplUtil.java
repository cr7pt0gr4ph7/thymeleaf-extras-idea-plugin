package org.thymeleaf.extras.idea.lang.expression.psi.impl;

import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceService;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.psi.GenericSelectionExpr;

public class ThymeleafExpressionPsiImplUtil {
    /**
     * Implementation of {@link com.intellij.psi.ContributedReferenceHost#getReferences}
     * for {@link GenericSelectionExpr}.
     *
     * @param expr
     * @return
     */
    @NotNull
    public static PsiReference[] getReferences(GenericSelectionExpr expr) {
        return PsiReferenceService.getService().getContributedReferences(expr);
    }
}
