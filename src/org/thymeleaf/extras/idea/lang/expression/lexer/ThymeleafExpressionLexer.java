package org.thymeleaf.extras.idea.lang.expression.lexer;

import com.intellij.lexer.LookAheadLexer;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.spring.el.parser.SpringELElementType;

import java.util.Collections;
import java.util.Map;

import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes.EXPRESSION_STRING;

public class ThymeleafExpressionLexer extends LookAheadLexer {
    private static final TokenSet tokensToMerge = TokenSet.create(
            EXPRESSION_STRING
    );

    private static final Map<IElementType, IElementType> tokenSubstitutions = Collections.<IElementType, IElementType>singletonMap(
            EXPRESSION_STRING, SpringELElementType.SPEL_HOLDER
    );

    public ThymeleafExpressionLexer() {
        super(new TokenTypeSubstitutingLexer(new MergingLexerAdapter(new ThymeleafExpressionFlexLexer(), tokensToMerge), tokenSubstitutions));
    }
}
