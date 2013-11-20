package org.thymeleaf.extras.idea.lang.expression.lexer;

import com.intellij.lexer.FlexAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Lukas Waslowski
 * Date: 20.11.13
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */
public class ThymeleafExpressionLexer extends FlexAdapter {
    public ThymeleafExpressionLexer() {
        super(new _ThymeleafExpressionLexer());
    }
}
