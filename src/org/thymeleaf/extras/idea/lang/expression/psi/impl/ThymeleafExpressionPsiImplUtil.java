package org.thymeleaf.extras.idea.lang.expression.psi.impl;

import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.lang.expression.psi.*;

public class ThymeleafExpressionPsiImplUtil {

    // TODO Get rid of this placeholder method (which is currently required to provide the type of getString() to Grammar-Kit)
    @Nullable
    public static PsiElement getExpressionString(GenericSelectionExpr expr) {
        throw new UnsupportedOperationException("Fake method that should be overriden");
    }

    /**
     * Implementation of {@link com.intellij.psi.ContributedReferenceHost#getReferences}
     * for {@link org.thymeleaf.extras.idea.lang.expression.psi.GenericSelectionExpr}.
     *
     * @param expr
     * @return
     */
    @NotNull
    public static PsiReference[] getReferences(GenericSelectionExpr expr) {
        return PsiReferenceService.getService().getContributedReferences(expr);
    }

    /**
     * Implementation of {@link com.intellij.psi.PsiLanguageInjectionHost#isValidHost()}
     * for {@link org.thymeleaf.extras.idea.lang.expression.psi.GenericSelectionExpr}.
     *
     * @param expr
     * @return
     */
    public static boolean isValidHost(GenericSelectionExpr expr) {
        return true;
    }

    /**
     * Implementation of {@link com.intellij.psi.PsiLanguageInjectionHost#updateText(String)}
     * for {@link org.thymeleaf.extras.idea.lang.expression.psi.GenericSelectionExpr}.
     *
     * @param expr
     * @param text
     * @return
     */
    public static PsiLanguageInjectionHost updateText(GenericSelectionExpr expr, @NotNull String text) {
        return new GenericSelectionManipulator().handleContentChange(expr, text);
    }

    /**
     * Implementation of {@link com.intellij.psi.PsiLanguageInjectionHost#createLiteralTextEscaper()}
     * for {@link org.thymeleaf.extras.idea.lang.expression.psi.GenericSelectionExpr}.
     *
     * @param expr
     * @return
     */
    @NotNull
    public static LiteralTextEscaper<GenericSelectionExpr> createLiteralTextEscaper(GenericSelectionExpr expr) {
        // return new GenericSelectionEscaper(expr);
        return new GenericSelectionEscaper(expr);
    }
}
