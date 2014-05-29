package org.thymeleaf.extras.idea.lang.expression.lexer;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes.*;

%%

%{
  public _ThymeleafExpressionLexer() {
    this((java.io.Reader)null);
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

STRING=([^\{\n\}]+)

%%
<YYINITIAL> {
  {WHITE_SPACE}               { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "${"                        { return VARIABLE_EXPR_START; }
  "*{"                        { return SELECTION_EXPR_START; }
  "#{"                        { return MESSAGE_EXPR_START; }
  "@{"                        { return LINK_EXPR_START; }
  "}"                         { return EXPRESSION_END; }

  "${{"                       { return CONVERTED_VARIABLE_EXPR_START; }
  "*{{"                       { return CONVERTED_SELECTION_EXPR_START; }
  "}}"                        { return CONVERTED_EXPRESSION_END; }

  {STRING}                    { return STRING; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
