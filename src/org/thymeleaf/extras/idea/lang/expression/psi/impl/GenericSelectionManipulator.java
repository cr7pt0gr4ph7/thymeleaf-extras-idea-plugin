package org.thymeleaf.extras.idea.lang.expression.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.psi.GenericSelectionExpr;

import static org.thymeleaf.extras.idea.util.MyTextRangeUtil.makeRelativeTo;

public class GenericSelectionManipulator extends AbstractElementManipulator<GenericSelectionExpr> {
    @Override
    public GenericSelectionExpr handleContentChange(@NotNull GenericSelectionExpr element, @NotNull TextRange range, String newContent) throws IncorrectOperationException {
        final String oldText = element.getText();
        final String newText = range.replace(oldText, newContent);
        // TODO Does this always work?
        return (GenericSelectionExpr) ThymeleafElementFactory.createExpressionFromText(element.getProject(), newText);
    }

    @NotNull
    @Override
    public TextRange getRangeInElement(@NotNull GenericSelectionExpr expr) {
        // TODO Improve GenericSelectionManipulator.getRangeInElement
        // TODO How to work around the valueNode == null case? The current workaround produces incorrect result.
        final PsiElement valueNode = expr.getString();
        if (valueNode == null) return new TextRange(0, 0);
        return makeRelativeTo(expr.getTextRange(), valueNode.getTextRange());
    }
}
