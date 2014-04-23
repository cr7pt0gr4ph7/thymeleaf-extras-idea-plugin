package org.thymeleaf.extras.idea.lang.expression.lexer;

import com.intellij.lexer.FlexAdapter;

public class ThymeleafExpressionLexer extends FlexAdapter {
    public ThymeleafExpressionLexer() {
        super(new _ThymeleafExpressionLexer());
    }
}
