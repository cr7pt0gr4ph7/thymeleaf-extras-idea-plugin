// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.openapi.diagnostic.Logger;
import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes.*;
import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionParserUtil.*;
import com.intellij.lang.LighterASTNode;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class ThymeleafExpressionParser implements PsiParser {

  public static final Logger LOG_ = Logger.getInstance("org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionParser");

  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this, EXTENDS_SETS_);
    Marker marker_ = enter_section_(builder_, 0, _COLLAPSE_, null);
    if (root_ == AND_EXPR) {
      result_ = expression(builder_, 0, 2);
    }
    else if (root_ == CONDITIONAL_EXPR) {
      result_ = expression(builder_, 0, -1);
    }
    else if (root_ == DEFAULT_EXPR) {
      result_ = expression(builder_, 0, 0);
    }
    else if (root_ == DIV_EXPR) {
      result_ = expression(builder_, 0, 6);
    }
    else if (root_ == EQ_EXPR) {
      result_ = expression(builder_, 0, 3);
    }
    else if (root_ == EXPRESSION) {
      result_ = expression(builder_, 0, -1);
    }
    else if (root_ == GENERIC_SELECTION_EXPR) {
      result_ = generic_selection_expr(builder_, 0);
    }
    else if (root_ == GT_EQ_EXPR) {
      result_ = expression(builder_, 0, 4);
    }
    else if (root_ == GT_EXPR) {
      result_ = expression(builder_, 0, 4);
    }
    else if (root_ == LINK_EXPR) {
      result_ = link_expr(builder_, 0);
    }
    else if (root_ == LT_EQ_EXPR) {
      result_ = expression(builder_, 0, 4);
    }
    else if (root_ == LT_EXPR) {
      result_ = expression(builder_, 0, 4);
    }
    else if (root_ == MESSAGE_EXPR) {
      result_ = message_expr(builder_, 0);
    }
    else if (root_ == MINUS_EXPR) {
      result_ = expression(builder_, 0, 5);
    }
    else if (root_ == MUL_EXPR) {
      result_ = expression(builder_, 0, 6);
    }
    else if (root_ == NEGATION_EXPR) {
      result_ = negation_expr(builder_, 0);
    }
    else if (root_ == NEQ_EXPR) {
      result_ = expression(builder_, 0, 3);
    }
    else if (root_ == OR_EXPR) {
      result_ = expression(builder_, 0, 1);
    }
    else if (root_ == PLUS_EXPR) {
      result_ = expression(builder_, 0, 5);
    }
    else if (root_ == REMAINDER_EXPR) {
      result_ = expression(builder_, 0, 6);
    }
    else if (root_ == SELECTION_EXPR) {
      result_ = selection_expr(builder_, 0);
    }
    else if (root_ == TEXT_LITERAL_EXPR) {
      result_ = text_literal_expr(builder_, 0);
    }
    else if (root_ == TOKEN_EXPR) {
      result_ = token_expr(builder_, 0);
    }
    else if (root_ == UNARY_MINUS_EXPR) {
      result_ = unary_minus_expr(builder_, 0);
    }
    else if (root_ == VARIABLE_EXPR) {
      result_ = variable_expr(builder_, 0);
    }
    else {
      result_ = parse_root_(root_, builder_, 0);
    }
    exit_section_(builder_, 0, marker_, root_, result_, true, TRUE_CONDITION);
    return builder_.getTreeBuilt();
  }

  protected boolean parse_root_(final IElementType root_, final PsiBuilder builder_, final int level_) {
    return root(builder_, level_ + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(AND_EXPR, CONDITIONAL_EXPR, DEFAULT_EXPR, DIV_EXPR,
      EQ_EXPR, EXPRESSION, GENERIC_SELECTION_EXPR, GT_EQ_EXPR,
      GT_EXPR, LINK_EXPR, LT_EQ_EXPR, LT_EXPR,
      MESSAGE_EXPR, MINUS_EXPR, MUL_EXPR, NEGATION_EXPR,
      NEQ_EXPR, OR_EXPR, PLUS_EXPR, REMAINDER_EXPR,
      SELECTION_EXPR, TEXT_LITERAL_EXPR, TOKEN_EXPR, UNARY_MINUS_EXPR,
      VARIABLE_EXPR),
    create_token_set_(GENERIC_SELECTION_EXPR, LINK_EXPR, MESSAGE_EXPR, SELECTION_EXPR,
      VARIABLE_EXPR),
  };

  /* ********************************************************** */
  // expression_string
  static boolean converted_expr_content(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "converted_expr_content")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, EXPRESSION_STRING);
    exit_section_(builder_, level_, marker_, null, result_, false, converted_expr_recover_parser_);
    return result_;
  }

  /* ********************************************************** */
  // !('}}')
  static boolean converted_expr_recover(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "converted_expr_recover")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NOT_, null);
    result_ = !converted_expr_recover_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, null, result_, false, null);
    return result_;
  }

  // ('}}')
  private static boolean converted_expr_recover_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "converted_expr_recover_0")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, CONVERTED_EXPRESSION_END);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // '*{{' converted_expr_content '}}'
  static boolean converted_selection_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "converted_selection_expr")) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, "<expression>");
    result_ = consumeToken(builder_, CONVERTED_SELECTION_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, converted_expr_content(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, CONVERTED_EXPRESSION_END) && result_;
    exit_section_(builder_, level_, marker_, null, result_, pinned_, converted_expr_recover_parser_);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // '${{' converted_expr_content '}}'
  static boolean converted_variable_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "converted_variable_expr")) return false;
    if (!nextTokenIs(builder_, CONVERTED_VARIABLE_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, CONVERTED_VARIABLE_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, converted_expr_content(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, CONVERTED_EXPRESSION_END) && result_;
    exit_section_(builder_, level_, marker_, null, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // '@{' standard_expr_content '}'
  public static boolean link_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "link_expr")) return false;
    if (!nextTokenIs(builder_, LINK_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, LINK_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, standard_expr_content(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    exit_section_(builder_, level_, marker_, LINK_EXPR, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // '#{' standard_expr_content '}'
  public static boolean message_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "message_expr")) return false;
    if (!nextTokenIs(builder_, MESSAGE_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, MESSAGE_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, standard_expr_content(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    exit_section_(builder_, level_, marker_, MESSAGE_EXPR, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // expression
  static boolean root(PsiBuilder builder_, int level_) {
    return expression(builder_, level_ + 1, -1);
  }

  /* ********************************************************** */
  // converted_selection_expr | standard_selection_expr
  public static boolean selection_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "selection_expr")) return false;
    if (!nextTokenIs(builder_, "<expression>", SELECTION_EXPR_START, CONVERTED_SELECTION_EXPR_START)) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_, level_, _COLLAPSE_, "<expression>");
    result_ = converted_selection_expr(builder_, level_ + 1);
    if (!result_) result_ = standard_selection_expr(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, SELECTION_EXPR, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // expression_string
  static boolean standard_expr_content(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "standard_expr_content")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, EXPRESSION_STRING);
    exit_section_(builder_, level_, marker_, null, result_, false, standard_expr_recover_parser_);
    return result_;
  }

  /* ********************************************************** */
  // !('}')
  static boolean standard_expr_recover(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "standard_expr_recover")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NOT_, null);
    result_ = !standard_expr_recover_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, null, result_, false, null);
    return result_;
  }

  // ('}')
  private static boolean standard_expr_recover_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "standard_expr_recover_0")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, EXPRESSION_END);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // '*{' standard_expr_content '}'
  static boolean standard_selection_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "standard_selection_expr")) return false;
    if (!nextTokenIs(builder_, SELECTION_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, SELECTION_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, standard_expr_content(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    exit_section_(builder_, level_, marker_, null, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // '${' standard_expr_content '}'
  static boolean standard_variable_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "standard_variable_expr")) return false;
    if (!nextTokenIs(builder_, VARIABLE_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, VARIABLE_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, standard_expr_content(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    exit_section_(builder_, level_, marker_, null, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // converted_variable_expr | standard_variable_expr
  public static boolean variable_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variable_expr")) return false;
    if (!nextTokenIs(builder_, "<expression>", VARIABLE_EXPR_START, CONVERTED_VARIABLE_EXPR_START)) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_, level_, _COLLAPSE_, "<expression>");
    result_ = converted_variable_expr(builder_, level_ + 1);
    if (!result_) result_ = standard_variable_expr(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, VARIABLE_EXPR, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // Expression root: expression
  // Operator priority table:
  // 0: BINARY(conditional_expr)
  // 1: BINARY(default_expr)
  // 2: BINARY(or_expr)
  // 3: BINARY(and_expr)
  // 4: BINARY(eq_expr) BINARY(neq_expr)
  // 5: BINARY(gt_expr) BINARY(gt_eq_expr) BINARY(lt_expr) BINARY(lt_eq_expr)
  // 6: BINARY(plus_expr) BINARY(minus_expr)
  // 7: BINARY(mul_expr) BINARY(div_expr) BINARY(remainder_expr)
  // 8: PREFIX(unary_minus_expr)
  // 9: PREFIX(negation_expr)
  // 10: ATOM(token_expr) ATOM(text_literal_expr) ATOM(generic_selection_expr)
  public static boolean expression(PsiBuilder builder_, int level_, int priority_) {
    if (!recursion_guard_(builder_, level_, "expression")) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, "<expression>");
    result_ = unary_minus_expr(builder_, level_ + 1);
    if (!result_) result_ = negation_expr(builder_, level_ + 1);
    if (!result_) result_ = token_expr(builder_, level_ + 1);
    if (!result_) result_ = text_literal_expr(builder_, level_ + 1);
    if (!result_) result_ = generic_selection_expr(builder_, level_ + 1);
    pinned_ = result_;
    result_ = result_ && expression_0(builder_, level_ + 1, priority_);
    exit_section_(builder_, level_, marker_, null, result_, pinned_, null);
    return result_ || pinned_;
  }

  public static boolean expression_0(PsiBuilder builder_, int level_, int priority_) {
    if (!recursion_guard_(builder_, level_, "expression_0")) return false;
    boolean result_ = true;
    while (true) {
      Marker left_marker_ = (Marker) builder_.getLatestDoneMarker();
      if (!invalid_left_marker_guard_(builder_, left_marker_, "expression_0")) return false;
      Marker marker_ = builder_.mark();
      if (priority_ < 0 && consumeToken(builder_, OP_CONDITIONAL)) {
        result_ = report_error_(builder_, expression(builder_, level_, 0));
        result_ = report_error_(builder_, conditional_expr_1(builder_, level_ + 1)) && result_;
        marker_.drop();
        left_marker_.precede().done(CONDITIONAL_EXPR);
      }
      else if (priority_ < 1 && consumeToken(builder_, OP_DEFAULT)) {
        result_ = report_error_(builder_, expression(builder_, level_, 1));
        marker_.drop();
        left_marker_.precede().done(DEFAULT_EXPR);
      }
      else if (priority_ < 2 && consumeToken(builder_, OP_OR)) {
        result_ = report_error_(builder_, expression(builder_, level_, 2));
        marker_.drop();
        left_marker_.precede().done(OR_EXPR);
      }
      else if (priority_ < 3 && consumeToken(builder_, OP_AND)) {
        result_ = report_error_(builder_, expression(builder_, level_, 3));
        marker_.drop();
        left_marker_.precede().done(AND_EXPR);
      }
      else if (priority_ < 4 && consumeToken(builder_, OP_EQ)) {
        result_ = report_error_(builder_, expression(builder_, level_, 4));
        marker_.drop();
        left_marker_.precede().done(EQ_EXPR);
      }
      else if (priority_ < 4 && consumeToken(builder_, OP_NOT_EQ)) {
        result_ = report_error_(builder_, expression(builder_, level_, 4));
        marker_.drop();
        left_marker_.precede().done(NEQ_EXPR);
      }
      else if (priority_ < 5 && consumeToken(builder_, OP_GT)) {
        result_ = report_error_(builder_, expression(builder_, level_, 5));
        marker_.drop();
        left_marker_.precede().done(GT_EXPR);
      }
      else if (priority_ < 5 && consumeToken(builder_, OP_GT_EQ)) {
        result_ = report_error_(builder_, expression(builder_, level_, 5));
        marker_.drop();
        left_marker_.precede().done(GT_EQ_EXPR);
      }
      else if (priority_ < 5 && consumeToken(builder_, OP_LT)) {
        result_ = report_error_(builder_, expression(builder_, level_, 5));
        marker_.drop();
        left_marker_.precede().done(LT_EXPR);
      }
      else if (priority_ < 5 && consumeToken(builder_, OP_LT_EQ)) {
        result_ = report_error_(builder_, expression(builder_, level_, 5));
        marker_.drop();
        left_marker_.precede().done(LT_EQ_EXPR);
      }
      else if (priority_ < 6 && consumeToken(builder_, OP_PLUS)) {
        result_ = report_error_(builder_, expression(builder_, level_, 6));
        marker_.drop();
        left_marker_.precede().done(PLUS_EXPR);
      }
      else if (priority_ < 6 && consumeToken(builder_, OP_MINUS)) {
        result_ = report_error_(builder_, expression(builder_, level_, 6));
        marker_.drop();
        left_marker_.precede().done(MINUS_EXPR);
      }
      else if (priority_ < 7 && consumeToken(builder_, OP_MUL)) {
        result_ = report_error_(builder_, expression(builder_, level_, 7));
        marker_.drop();
        left_marker_.precede().done(MUL_EXPR);
      }
      else if (priority_ < 7 && consumeToken(builder_, OP_DIV)) {
        result_ = report_error_(builder_, expression(builder_, level_, 7));
        marker_.drop();
        left_marker_.precede().done(DIV_EXPR);
      }
      else if (priority_ < 7 && consumeToken(builder_, OP_REMAINDER)) {
        result_ = report_error_(builder_, expression(builder_, level_, 7));
        marker_.drop();
        left_marker_.precede().done(REMAINDER_EXPR);
      }
      else {
        exit_section_(builder_, marker_, null, false);
        break;
      }
    }
    return result_;
  }

  // [':' expression]
  private static boolean conditional_expr_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "conditional_expr_1")) return false;
    conditional_expr_1_0(builder_, level_ + 1);
    return true;
  }

  // ':' expression
  private static boolean conditional_expr_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "conditional_expr_1_0")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, OP_COLON);
    result_ = result_ && expression(builder_, level_ + 1, -1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  public static boolean unary_minus_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "unary_minus_expr")) return false;
    if (!nextTokenIs(builder_, "<expression>", OP_MINUS)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, OP_MINUS);
    pinned_ = result_;
    result_ = pinned_ && expression(builder_, level_, 8);
    exit_section_(builder_, level_, marker_, UNARY_MINUS_EXPR, result_, pinned_, null);
    return result_ || pinned_;
  }

  public static boolean negation_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "negation_expr")) return false;
    if (!nextTokenIs(builder_, "<expression>", OP_NOT_SYM, OP_NOT)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = negation_expr_0(builder_, level_ + 1);
    pinned_ = result_;
    result_ = pinned_ && expression(builder_, level_, 9);
    exit_section_(builder_, level_, marker_, NEGATION_EXPR, result_, pinned_, null);
    return result_ || pinned_;
  }

  // '!' | 'not'
  private static boolean negation_expr_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "negation_expr_0")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, OP_NOT_SYM);
    if (!result_) result_ = consumeToken(builder_, OP_NOT);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // token
  public static boolean token_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "token_expr")) return false;
    if (!nextTokenIs(builder_, TOKEN)) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, TOKEN);
    exit_section_(builder_, marker_, TOKEN_EXPR, result_);
    return result_;
  }

  // string
  public static boolean text_literal_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "text_literal_expr")) return false;
    if (!nextTokenIs(builder_, STRING)) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, STRING);
    exit_section_(builder_, marker_, TEXT_LITERAL_EXPR, result_);
    return result_;
  }

  // variable_expr | selection_expr | link_expr | message_expr
  public static boolean generic_selection_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_selection_expr")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_, level_, _COLLAPSE_, "<expression>");
    result_ = variable_expr(builder_, level_ + 1);
    if (!result_) result_ = selection_expr(builder_, level_ + 1);
    if (!result_) result_ = link_expr(builder_, level_ + 1);
    if (!result_) result_ = message_expr(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, GENERIC_SELECTION_EXPR, result_, false, null);
    return result_;
  }

  final static Parser converted_expr_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder builder_, int level_) {
      return converted_expr_recover(builder_, level_ + 1);
    }
  };
  final static Parser standard_expr_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder builder_, int level_) {
      return standard_expr_recover(builder_, level_ + 1);
    }
  };
}
