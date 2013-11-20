package org.thymeleaf.extras.idea.lang.expression.psi;

import com.intellij.psi.tree.TokenSet;
import org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes;

public class ThymeleafExpressionTokenGroups implements ThymeleafExpressionElementTypes {
    // Expression delimiters: ${, *{, #{, @{ and }
    public static final TokenSet EXPRESSION_BOUNDS = TokenSet.create(
            VARIABLE_EXPR_START, SELECTION_EXPR_START,
            MESSAGE_EXPR_START, LINK_EXPR_START, EXPRESSION_END);
    //
    // literals
    public static final TokenSet TEXT = TokenSet.create(STRING);
}
