package org.thymeleaf.extras.idea.lang.expression.lexer;

import com.intellij.lexer.FlexAdapter;

/**
 * Infrastructure class. See {@link org.thymeleaf.extras.idea.lang.expression.lexer.ThymeleafExpressionLexer} instead.
 */
public class ThymeleafExpressionFlexLexer extends FlexAdapter {
    public ThymeleafExpressionFlexLexer() {
        super(new _ThymeleafExpressionLexer());
    }
}
