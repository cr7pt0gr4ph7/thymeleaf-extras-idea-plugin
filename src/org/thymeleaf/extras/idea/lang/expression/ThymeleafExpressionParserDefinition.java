package org.thymeleaf.extras.idea.lang.expression;

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
import org.thymeleaf.extras.idea.lang.expression.lexer.ThymeleafExpressionLexer;
import org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes;
import org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionParser;
import org.thymeleaf.extras.idea.lang.expression.psi.ThymeleafExpressionTokenGroups;

public class ThymeleafExpressionParserDefinition implements ParserDefinition {
    public static final IFileElementType THYMELEAF_EXPRESSION_FILE_ELEMENT_TYPE = new IFileElementType(ThymeleafExpressionLanguage.ID, ThymeleafExpressionLanguage.INSTANCE);
    public static final TokenSet WHITE_SPACE = TokenSet.create(TokenType.WHITE_SPACE);

    @NotNull
    public Lexer createLexer(Project project) {
        return new ThymeleafExpressionLexer();
    }

    public PsiParser createParser(Project project) {
        return new ThymeleafExpressionParser();
    }

    public IFileElementType getFileNodeType() {
        return THYMELEAF_EXPRESSION_FILE_ELEMENT_TYPE;
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
        return ThymeleafExpressionTokenGroups.TEXT;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return ThymeleafExpressionElementTypes.Factory.createElement(node);
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new ThymeleafExpressionFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        final Lexer lexer = createLexer(left.getPsi().getProject());
        return LanguageUtil.canStickTokensTogetherByLexer(left, right, lexer);
    }
}