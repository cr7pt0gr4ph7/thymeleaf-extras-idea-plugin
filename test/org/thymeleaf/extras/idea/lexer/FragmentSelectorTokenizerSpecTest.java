package org.thymeleaf.extras.idea.lexer;

import com.intellij.lexer.Lexer;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.fragment.selection.lexer.FragmentSelectorLexer;

import static org.thymeleaf.extras.idea.lang.fragment.selection.parser.FragmentSelectorElementTypes.*;
import static com.intellij.psi.TokenType.WHITE_SPACE;

public class FragmentSelectorTokenizerSpecTest extends GeneralLexerTest {
    @NotNull
    @Override
    protected Lexer createLexer() throws Exception {
        return new FragmentSelectorLexer();
    }

    public void testOtherDocumentSelector_WithWhitespace() {
        TokenizerResult result = tokenize("  path/to/documentName ");
        result.shouldMatchTokenTypes(WHITE_SPACE, STRING, WHITE_SPACE);
        result.shouldBeToken(1, STRING, "path/to/documentName");
    }

    /**
     * Supports selectors of the form "path/to/documentName :: domSelector"
     */
    public void testOtherDocumentWithFragmentSelector_WithWhitespace() {
        TokenizerResult result = tokenize("path/to/documentName :: domSelector");
        result.shouldMatchTokenTypes(STRING, WHITE_SPACE, OPERATOR, WHITE_SPACE, STRING);
        result.shouldBeToken(0, STRING, "path/to/documentName");
        result.shouldBeToken(4, STRING, "domSelector");
    }

    /**
     * Supports selectors of the form "path/to/documentName::domSelector" (note that there is no whitespace)
     */
    public void testOtherDocumentWithFragmentSelector() {
        TokenizerResult result = tokenize("path/to/documentName::domSelector");
        result.shouldMatchTokenTypes(STRING, OPERATOR, STRING);
        result.shouldBeToken(0, STRING, "path/to/documentName");
        result.shouldBeToken(2, STRING, "domSelector");
    }

    /**
     * Supports selectors of the form ":: domSelector"
     */
    public void testThisDocumentWithFragmentSelector_WithWhitespace() {
        TokenizerResult result = tokenize(":: domSelector");
        result.shouldMatchTokenTypes(OPERATOR, WHITE_SPACE, STRING);
        result.shouldBeToken(2, STRING, "domSelector");
    }
}
