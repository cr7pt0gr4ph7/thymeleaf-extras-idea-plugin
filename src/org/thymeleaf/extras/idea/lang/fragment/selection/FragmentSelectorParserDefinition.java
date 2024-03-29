package org.thymeleaf.extras.idea.lang.fragment.selection;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.fragment.selection.lexer.FragmentSelectorLexer;
import org.thymeleaf.extras.idea.lang.fragment.selection.parser.FragmentSelectorElementTypes;
import org.thymeleaf.extras.idea.lang.fragment.selection.parser.FragmentSelectorParser;

public class FragmentSelectorParserDefinition implements ParserDefinition {
    public static final IFileElementType FRAGMENT_SELECTOR_FILE_ELEMENT_TYPE = new IFileElementType("ThFS", FragmentSelectorLanguage.INSTANCE);
    public static final TokenSet WHITE_SPACE = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet LITERALS = TokenSet.create(FragmentSelectorElementTypes.STRING);

    @NotNull
    public Lexer createLexer(Project project) {
        return new FragmentSelectorLexer();
    }

    public PsiParser createParser(Project project) {
        return new FragmentSelectorParser();
    }

    public IFileElementType getFileNodeType() {
        return FRAGMENT_SELECTOR_FILE_ELEMENT_TYPE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACE;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return LITERALS;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return FragmentSelectorElementTypes.Factory.createElement(node);
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new FragmentSelectorFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        final Lexer lexer = createLexer(left.getPsi().getProject());
        return LanguageUtil.canStickTokensTogetherByLexer(left, right, lexer);
    }
}