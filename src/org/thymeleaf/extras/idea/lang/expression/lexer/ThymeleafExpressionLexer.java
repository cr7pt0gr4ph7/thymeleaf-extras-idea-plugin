package org.thymeleaf.extras.idea.lang.expression.lexer;

import com.intellij.lexer.LookAheadLexer;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;

import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes.EXPRESSION_STRING;

public class ThymeleafExpressionLexer extends LookAheadLexer {
    private static final TokenSet tokensToMerge = TokenSet.create(
            EXPRESSION_STRING
    );

    public ThymeleafExpressionLexer() {
        super(new MergingLexerAdapter(new ThymeleafExpressionFlexLexer(), tokensToMerge));
    }
}
