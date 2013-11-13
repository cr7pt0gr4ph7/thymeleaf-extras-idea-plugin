package org.thymeleaf.extras.idea.fragment.selection;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.fragment.selection.parser.FragmentSelectorElementTypes;
import org.thymeleaf.extras.idea.fragment.selection.lexer.FragmentSelectorLexer;

public class FragmentSelectorHighlighter extends SyntaxHighlighterBase {
    public FragmentSelectorHighlighter() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType elementType) {
        System.out.println(elementType.toString());

        if (elementType == FragmentSelectorElementTypes.STRING)
            return pack(DefaultLanguageHighlighterColors.STRING);
        else if (elementType == FragmentSelectorElementTypes.OPERATOR)
            return pack(DefaultLanguageHighlighterColors.KEYWORD);
        else if (elementType == TokenType.BAD_CHARACTER)
            return pack(HighlighterColors.BAD_CHARACTER);

        return EMPTY;
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FragmentSelectorLexer();
    }
}
