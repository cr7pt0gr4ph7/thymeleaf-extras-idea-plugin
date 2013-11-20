package org.thymeleaf.extras.idea.lang.expression;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.lexer.ThymeleafExpressionLexer;
import org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementType;
import org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes;
import org.thymeleaf.extras.idea.lang.expression.psi.ThymeleafExpressionTokenGroups;

import java.util.HashMap;
import java.util.Map;

public class ThymeleafExpressionHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey BACKGROUND = TextAttributesKey.createTextAttributesKey(
            "THEL.BACKGROUND", DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR);
    /**/
    public static final TextAttributesKey EXPRESSION_BOUNDS = TextAttributesKey.createTextAttributesKey(
            "THEL.EXPRESSION", DefaultLanguageHighlighterColors.KEYWORD);
    /**/
    public static final TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey(
            "THEL.BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
    /**/
    public static final TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey(
            "THEL.STRING", DefaultLanguageHighlighterColors.STRING);
    /**/
    /**/
    private static final Map<IElementType, TextAttributesKey> keys;

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new ThymeleafExpressionLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType elementType) {
        return pack(BACKGROUND, keys.get(elementType));
    }

    static {
        // TODO: HashMap vs. THashMap
        keys = new HashMap<IElementType, TextAttributesKey>();

        // Single characters
        keys.put(TokenType.BAD_CHARACTER, BAD_CHARACTER);

        // Nested expression delimiters
        SyntaxHighlighterBase.fillMap(keys, ThymeleafExpressionTokenGroups.EXPRESSION_BOUNDS, EXPRESSION_BOUNDS);

        // String literals
        SyntaxHighlighterBase.fillMap(keys, ThymeleafExpressionTokenGroups.TEXT, STRING);
    }
}
