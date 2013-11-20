package org.thymeleaf.extras.idea.lang.expression.parser;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.ThymeleafExpressionLanguage;

/**
 * Distinct interface to distinguish the leaf elements we get from the lexer from the synthetic
 * composite elements we create in the parser
 */
class ThymeleafExpressionCompositeElementType extends IElementType {
    public ThymeleafExpressionCompositeElementType(@NotNull @NonNls String debugName) {
        super(debugName, ThymeleafExpressionLanguage.INSTANCE);
    }
}

