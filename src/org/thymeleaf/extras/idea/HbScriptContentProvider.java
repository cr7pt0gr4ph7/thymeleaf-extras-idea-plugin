package org.thymeleaf.extras.idea;

import org.thymeleaf.extras.idea.file.HbFileType;
import org.thymeleaf.extras.idea.parsing.HbTokenTypes;
import com.intellij.lang.HtmlScriptContentProvider;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.Nullable;

public class HbScriptContentProvider implements HtmlScriptContentProvider {
    @Override
    public IElementType getScriptElementType() {
        return HbTokenTypes.EMBEDDED_CONTENT;
    }

    @Nullable
    @Override
    public Lexer getHighlightingLexer() {
        SyntaxHighlighter syntaxHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(HbFileType.INSTANCE, null, null);
        return syntaxHighlighter != null ? syntaxHighlighter.getHighlightingLexer() : null;
    }
}