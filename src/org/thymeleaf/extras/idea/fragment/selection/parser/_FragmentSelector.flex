package org.thymeleaf.extras.idea.fragment.selection.parser;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static org.thymeleaf.extras.idea.fragment.selection.parser.FragmentSelectorElementTypes.*;

%%

%{
  public _FragmentSelectorLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _FragmentSelectorLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode
%eof{ return;
%eof}

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+

STRING=([^ \n:(),]+)

%%
<YYINITIAL> {
  {WHITE_SPACE}       { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "::"                { return OPERATOR; }
  "("                 { return OPEN_PARENS; }
  ")"                 { return CLOSE_PARENS; }
  "["                 { return OPEN_ARRAY; }
  "]"                 { return CLOSE_ARRAY; }
  ","                 { return COMMA; }

  {STRING}            { return STRING; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
