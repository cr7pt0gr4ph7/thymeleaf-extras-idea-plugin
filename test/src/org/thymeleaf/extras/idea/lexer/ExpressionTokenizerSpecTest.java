package org.thymeleaf.extras.idea.lexer;

import com.intellij.lexer.Lexer;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.lexer.ThymeleafExpressionLexer;

import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes.*;

public class ExpressionTokenizerSpecTest extends GeneralLexerTest {
    @NotNull
    @Override
    protected Lexer createLexer() throws Exception {
        return new ThymeleafExpressionLexer();
    }

    public void testVariableExpression() {
        TokenizerResult result = tokenize("${simple}");
        result.shouldMatchTokenTypes(VARIABLE_EXPR_START, STRING, EXPRESSION_END);
        result.shouldBeToken(1, STRING, "simple");
    }

    public void testConvertToStringVariableExpression() {
        TokenizerResult result = tokenize("${{simple}}");
        result.shouldMatchTokenTypes(CONVERTED_VARIABLE_EXPR_START, STRING, CONVERTED_EXPRESSION_END);
        result.shouldBeToken(1, STRING, "simple");
    }

    public void testSelectionExpression() {
        TokenizerResult result = tokenize("*{simple}");
        result.shouldMatchTokenTypes(SELECTION_EXPR_START, STRING, EXPRESSION_END);
        result.shouldBeToken(1, STRING, "simple");
    }

    public void testConvertToStringSelectionExpression() {
        TokenizerResult result = tokenize("*{{simple}}");
        result.shouldMatchTokenTypes(CONVERTED_SELECTION_EXPR_START, STRING, CONVERTED_EXPRESSION_END);
        result.shouldBeToken(1, STRING, "simple");
    }

    public void testOpAddAlone() {
        TokenizerResult result = tokenize("+");
        result.shouldMatchTokenTypes(OP_PLUS);
        result.shouldBeToken(0, OP_PLUS, "+");
    }

    public void testOpAdd2() {
        TokenizerResult result = tokenize("+ +");
        result.shouldMatchTokenTypes(OP_PLUS, WHITE_SPACE, OP_PLUS);
        result.shouldBeToken(0, OP_PLUS, "+");
    }

    public void testOpAdd() {
        TokenizerResult result = tokenize("do + it+now");
        result.shouldMatchTokenTypes(TOKEN, WHITE_SPACE, OP_PLUS, WHITE_SPACE, TOKEN, OP_PLUS, TOKEN);
        result.shouldBeToken(0, TOKEN, "do");
        result.shouldBeToken(4, TOKEN, "it");
        result.shouldBeToken(6, TOKEN, "now");
    }

    public void testOpConditional() {
        TokenizerResult result = tokenize("a ? b : c");
        result.shouldMatchTokenTypes(TOKEN, WHITE_SPACE, OP_CONDITIONAL, WHITE_SPACE, TOKEN,
                WHITE_SPACE, OP_COLON, WHITE_SPACE, TOKEN);
    }
}
