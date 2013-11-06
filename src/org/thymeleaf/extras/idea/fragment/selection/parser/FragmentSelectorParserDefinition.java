package org.thymeleaf.extras.idea.fragment.selection.parser;

import com.intellij.lang.ASTNode;
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
import org.thymeleaf.extras.idea.fragment.selection.FragmentSelectorLanguage;

public class FragmentSelectorParserDefinition implements ParserDefinition {
    public static final IFileElementType FRAGMENT_SELECTOR_FILE_ELEMENT_TYPE = new IFileElementType("ThymeleafFragmentSelector", FragmentSelectorLanguage.INSTANCE);
    public static final TokenSet WS = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create();
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
        return WS;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
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
        return null;
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }
}
