/* The following code was generated by JFlex 1.4.3 on 03.06.14 09:16 */

package org.thymeleaf.extras.idea.lang.expression.lexer;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 03.06.14 09:16 from the specification file
 * <tt>C:/Users/Lukas Waslowski/IdeaProjects/tools/thymeleaf-extras-idea-plugin/src/org/thymeleaf/extras/idea/lang/expression/lexer/_ThymeleafExpression.flex</tt>
 */
public class _ThymeleafExpressionLexer implements FlexLexer {
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int EMBEDDED_EXPR_END = 6;
  public static final int EMBEDDED_EXPR = 4;
  public static final int YYINITIAL = 0;
  public static final int INSTRING = 8;
  public static final int NESTEDBRACES_IN_EMBEDDED_EXPR = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4, 4
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\1\1\1\1\0\1\1\1\1\22\0\1\1\1\16\1\0"+
    "\1\30\1\27\1\24\1\0\1\4\2\0\1\22\1\25\1\0\1\26"+
    "\1\2\1\23\12\2\1\7\1\0\1\20\1\17\1\21\1\6\1\31"+
    "\33\2\1\0\1\2\1\0\2\2\1\10\2\2\1\12\11\2\1\11"+
    "\1\13\2\2\1\14\1\2\1\15\6\2\1\5\1\2\1\3\72\2"+
    "\10\0\27\2\1\0\37\2\1\0\u0286\2\1\0\u1c81\2\14\0\2\2"+
    "\61\0\2\2\57\0\u0120\2\u0a70\0\u03f0\2\21\0\ua7ff\2\u2100\0\u04d0\2"+
    "\40\0\u020e\2\2\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\5\0\1\1\1\2\1\3\1\4\1\1\1\5\1\6"+
    "\3\3\1\7\1\1\1\10\1\11\1\12\1\13\1\14"+
    "\1\15\1\16\3\1\1\17\1\20\1\21\1\22\1\23"+
    "\1\24\1\0\1\25\2\3\1\26\1\27\1\30\1\31"+
    "\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41"+
    "\1\42\1\43\1\44";

  private static int [] zzUnpackAction() {
    int [] result = new int[52];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\32\0\64\0\116\0\150\0\150\0\202\0\234"+
    "\0\266\0\320\0\352\0\150\0\u0104\0\u011e\0\u0138\0\u0152"+
    "\0\u016c\0\u0186\0\u01a0\0\u01ba\0\150\0\150\0\150\0\150"+
    "\0\u01d4\0\u01ee\0\u0208\0\150\0\150\0\150\0\u0222\0\150"+
    "\0\150\0\u023c\0\150\0\u0256\0\u0270\0\234\0\150\0\150"+
    "\0\150\0\150\0\u028a\0\u02a4\0\150\0\150\0\150\0\150"+
    "\0\234\0\234\0\150\0\150";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[52];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\6\1\7\1\10\1\11\1\12\1\6\1\13\1\14"+
    "\1\15\1\16\1\10\1\17\2\10\1\20\1\21\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32"+
    "\1\33\3\34\1\35\1\34\1\36\27\34\1\37\1\34"+
    "\1\40\24\34\3\0\1\11\61\0\1\7\32\0\1\10"+
    "\5\0\6\10\17\0\1\41\26\0\4\42\1\0\25\42"+
    "\7\0\1\43\24\0\1\10\5\0\1\10\1\44\4\10"+
    "\16\0\1\10\5\0\3\10\1\45\2\10\16\0\1\10"+
    "\5\0\4\10\1\46\1\10\33\0\1\47\31\0\1\50"+
    "\31\0\1\51\31\0\1\52\17\0\1\53\31\0\1\54"+
    "\31\0\1\55\31\0\1\56\27\0\1\57\26\0\4\42"+
    "\1\60\25\42\2\0\1\10\5\0\2\10\1\61\3\10"+
    "\16\0\1\10\5\0\5\10\1\62\21\0\1\63\31\0"+
    "\1\64\24\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[702];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  private static final char[] EMPTY_BUFFER = new char[0];
  private static final int YYEOF = -1;
  private static java.io.Reader zzReader = null; // Fake

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\4\0\1\10\1\11\5\1\1\11\10\1\4\11\3\1"+
    "\3\11\1\1\2\11\1\0\1\11\3\1\4\11\2\1"+
    "\4\11\2\1\2\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[52];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** this buffer may contains the current text array to be matched when it is cheap to acquire it */
  private char[] zzBufferArray;

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
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


  public _ThymeleafExpressionLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public _ThymeleafExpressionLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 146) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart(){
    return zzStartRead;
  }

  public final int getTokenEnd(){
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end,int initialState){
    zzBuffer = buffer;
    zzBufferArray = com.intellij.util.text.CharArrayUtil.fromSequenceWithoutCopying(buffer);
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzPushbackPos = 0;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBufferArray != null ? zzBufferArray[zzStartRead+pos]:zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;
    char[] zzBufferArrayL = zzBufferArray;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = (zzBufferArrayL != null ? zzBufferArrayL[zzCurrentPosL++] : zzBufferL.charAt(zzCurrentPosL++));
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = (zzBufferArrayL != null ? zzBufferArrayL[zzCurrentPosL++] : zzBufferL.charAt(zzCurrentPosL++));
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 5: 
          { return OP_CONDITIONAL;
          }
        case 37: break;
        case 6: 
          { return OP_COLON;
          }
        case 38: break;
        case 9: 
          { return OP_GT;
          }
        case 39: break;
        case 13: 
          { return OP_PLUS;
          }
        case 40: break;
        case 23: 
          { return OP_NOT_EQ;
          }
        case 41: break;
        case 12: 
          { return OP_REMAINDER;
          }
        case 42: break;
        case 18: 
          { yypushback(1); yybegin(EMBEDDED_EXPR_END); return getExpressionStringToken();
          }
        case 43: break;
        case 7: 
          { return OP_NOT_SYM;
          }
        case 44: break;
        case 24: 
          { return OP_EQ;
          }
        case 45: break;
        case 25: 
          { return OP_LT_EQ;
          }
        case 46: break;
        case 10: 
          { return OP_MUL;
          }
        case 47: break;
        case 14: 
          { return OP_MINUS;
          }
        case 48: break;
        case 31: 
          { yypushback(2); yybegin(EMBEDDED_EXPR_END); return getExpressionStringToken();
          }
        case 49: break;
        case 4: 
          { yybegin(YYINITIAL); return EXPRESSION_END;
          }
        case 50: break;
        case 29: 
          { yybegin(EMBEDDED_EXPR); setExpressionMode(SIMPLE_EXPRESSION_STRING); return MESSAGE_EXPR_START;
          }
        case 51: break;
        case 21: 
          { return OP_DEFAULT;
          }
        case 52: break;
        case 8: 
          { return OP_LT;
          }
        case 53: break;
        case 27: 
          { yybegin(EMBEDDED_EXPR); setExpressionMode(EL_EXPRESSION_STRING); return SELECTION_EXPR_START;
          }
        case 54: break;
        case 30: 
          { yybegin(EMBEDDED_EXPR); setExpressionMode(SIMPLE_EXPRESSION_STRING); return LINK_EXPR_START;
          }
        case 55: break;
        case 36: 
          { yybegin(EMBEDDED_EXPR); setExpressionMode(EL_EXPRESSION_STRING); return CONVERTED_VARIABLE_EXPR_START;
          }
        case 56: break;
        case 35: 
          { yybegin(EMBEDDED_EXPR); setExpressionMode(EL_EXPRESSION_STRING); return CONVERTED_SELECTION_EXPR_START;
          }
        case 57: break;
        case 15: 
          { return getExpressionStringToken();
          }
        case 58: break;
        case 28: 
          { yybegin(EMBEDDED_EXPR); setExpressionMode(EL_EXPRESSION_STRING); return VARIABLE_EXPR_START;
          }
        case 59: break;
        case 16: 
          { if(nestedBraceCount > 0) { nestedBraceCount--; }
                                else { // Outer ${ } (or not inside ${ })
                                  yypushback(1);
                                  yybegin(EMBEDDED_EXPR);
                                  return getExpressionStringToken();
                                }
          }
        case 60: break;
        case 20: 
          { yybegin(YYINITIAL); return CONVERTED_EXPRESSION_END;
          }
        case 61: break;
        case 26: 
          { return OP_GT_EQ;
          }
        case 62: break;
        case 32: 
          { return STRING;
          }
        case 63: break;
        case 2: 
          { return com.intellij.psi.TokenType.WHITE_SPACE;
          }
        case 64: break;
        case 11: 
          { return OP_DIV;
          }
        case 65: break;
        case 22: 
          { return OP_OR;
          }
        case 66: break;
        case 33: 
          { return OP_AND;
          }
        case 67: break;
        case 34: 
          { return OP_NOT;
          }
        case 68: break;
        case 19: 
          { yypushback(1); yybegin(NESTEDBRACES_IN_EMBEDDED_EXPR);
          }
        case 69: break;
        case 17: 
          { nestedBraceCount++;
          }
        case 70: break;
        case 1: 
          { return com.intellij.psi.TokenType.BAD_CHARACTER;
          }
        case 71: break;
        case 3: 
          { return TOKEN;
          }
        case 72: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            return null;
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
