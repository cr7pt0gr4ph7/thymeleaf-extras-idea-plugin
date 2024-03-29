package org.thymeleaf.extras.idea.lang.expression.lexer;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes.*;

%%

%{
  public _ThymeleafExpressionLexer() {
    this((java.io.Reader)null);
  }

  private IElementType myExpressionStringToken = null;
  private int nestedBraceCount = 0;

  private void setExpressionMode(IElementType expressionStringToken) {
    myExpressionStringToken = expressionStringToken;
  }

  private IElementType getExpressionStringToken() {
    assert myExpressionStringToken != null;
    return myExpressionStringToken;
  }
%}

%public
%class _ThymeleafExpressionLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode
%eof{ return;
%eof}

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+

// The definition of Token is taken from the Thymeleaf method isTokenChar(...)
// Mind the special role of '-'!
SURELY_NOT_A_TOKEN_CHAR=[\ \n()\'\"<>{}=,;:+*$%&#]
MAYBE_A_TOKEN_CHAR=[a-zA-Z0-9\[\]\._-\u00B7\u00c0-\u00d6\u00d8-\u00f6\u00f8-\u02ff\u0300-\u036f\u0370-\u037d\u037f-\u1fff\u200c-\u200d\u203f-\u2040\u2070-\u218f\u2c00-\u2fef\u3001-\ud7ff\uf900-\ufdcf\ufdf0-\ufffd]
TOKEN_CHAR=!({SURELY_NOT_A_TOKEN_CHAR}|!{MAYBE_A_TOKEN_CHAR})
TOKEN=({TOKEN_CHAR}+)

// Text literals: '...'
STRING=\' ([^\']+) \'

%state NESTEDBRACES_IN_EMBEDDED_EXPR, EMBEDDED_EXPR, EMBEDDED_EXPR_END, INSTRING

%%
// Selection expressions:
<NESTEDBRACES_IN_EMBEDDED_EXPR> {
  "{"                         { nestedBraceCount++; }
  "}"                         { if(nestedBraceCount > 0) { nestedBraceCount--; }
                                else { // Outer ${ } (or not inside ${ })
                                  yypushback(1);
                                  yybegin(EMBEDDED_EXPR);
                                  return getExpressionStringToken();
                                } }
}
<EMBEDDED_EXPR> {
  "{"                         { yypushback(1); yybegin(NESTEDBRACES_IN_EMBEDDED_EXPR); }
  "}"                         { yypushback(1); yybegin(EMBEDDED_EXPR_END); return getExpressionStringToken(); }
  "}}"                        { yypushback(2); yybegin(EMBEDDED_EXPR_END); return getExpressionStringToken(); }
}
<NESTEDBRACES_IN_EMBEDDED_EXPR, EMBEDDED_EXPR> {
  [^\}\{]                     { return getExpressionStringToken(); }
}
<YYINITIAL, EMBEDDED_EXPR_END> {
  // These rules are specified for both states for better error recovery in the parser
  "}"                         { yybegin(YYINITIAL); return EXPRESSION_END; }
  "}}"                        { yybegin(YYINITIAL); return CONVERTED_EXPRESSION_END; }
}
<YYINITIAL> {
  {WHITE_SPACE}               { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "?"                         { return OP_CONDITIONAL; }
  ":"                         { return OP_COLON; }
  "?:"                        { return OP_DEFAULT; }

  "and"                       { return OP_AND; }
  "or"                        { return OP_OR; }
  "not"                       { return OP_NOT; }
  "!"                         { return OP_NOT_SYM; }

  "=="                        { return OP_EQ; }
  "!="                        { return OP_NOT_EQ; }

  "<"                         { return OP_LT; }
  "<="                        { return OP_LT_EQ; }
  ">"                         { return OP_GT; }
  ">="                        { return OP_GT_EQ; }

  "*"                         { return OP_MUL; }
  "/"                         { return OP_DIV; }
  "%"                         { return OP_REMAINDER; }
  "+"                         { return OP_PLUS; }
  "-"                         { return OP_MINUS; }

  "${"                        { yybegin(EMBEDDED_EXPR); setExpressionMode(EL_EXPRESSION_STRING); return VARIABLE_EXPR_START; }
  "*{"                        { yybegin(EMBEDDED_EXPR); setExpressionMode(EL_EXPRESSION_STRING); return SELECTION_EXPR_START; }
  "#{"                        { yybegin(EMBEDDED_EXPR); setExpressionMode(SIMPLE_EXPRESSION_STRING); return MESSAGE_EXPR_START; }
  "@{"                        { yybegin(EMBEDDED_EXPR); setExpressionMode(SIMPLE_EXPRESSION_STRING); return LINK_EXPR_START; }

  "${{"                       { yybegin(EMBEDDED_EXPR); setExpressionMode(EL_EXPRESSION_STRING); return CONVERTED_VARIABLE_EXPR_START; }
  "*{{"                       { yybegin(EMBEDDED_EXPR); setExpressionMode(EL_EXPRESSION_STRING); return CONVERTED_SELECTION_EXPR_START; }

  {TOKEN}                     { return TOKEN; }
  {STRING}                    { return STRING; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
