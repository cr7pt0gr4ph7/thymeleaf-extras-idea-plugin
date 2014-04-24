// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.openapi.diagnostic.Logger;
import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes.*;
import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionParserUtil.*;
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
    if (root_ == EXPRESSION) {
      result_ = expression(builder_, 0);
    }
    else if (root_ == GENERIC_SELECTION_EXPR) {
      result_ = generic_selection_expr(builder_, 0);
    }
    else if (root_ == LINK_EXPR) {
      result_ = link_expr(builder_, 0);
    }
    else if (root_ == MESSAGE_EXPR) {
      result_ = message_expr(builder_, 0);
    }
    else if (root_ == SELECTION_EXPR) {
      result_ = selection_expr(builder_, 0);
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
    create_token_set_(EXPRESSION, GENERIC_SELECTION_EXPR, LINK_EXPR, MESSAGE_EXPR,
      SELECTION_EXPR, VARIABLE_EXPR),
    create_token_set_(GENERIC_SELECTION_EXPR, LINK_EXPR, MESSAGE_EXPR, SELECTION_EXPR,
      VARIABLE_EXPR),
  };

  /* ********************************************************** */
  // generic_selection_expr
  public static boolean expression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expression")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_, level_, _COLLAPSE_, "<expression>");
    result_ = generic_selection_expr(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, EXPRESSION, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // variable_expr | selection_expr | link_expr | message_expr
  public static boolean generic_selection_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_selection_expr")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_, level_, _COLLAPSE_, "<expression>");
    result_ = variable_expr(builder_, level_ + 1);
    if (!result_) result_ = selection_expr(builder_, level_ + 1);
    if (!result_) result_ = link_expr(builder_, level_ + 1);
    if (!result_) result_ = message_expr(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, GENERIC_SELECTION_EXPR, result_, false, selection_expr_recover_parser_);
    return result_;
  }

  /* ********************************************************** */
  // '@{' string '}'
  public static boolean link_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "link_expr")) return false;
    if (!nextTokenIs(builder_, LINK_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, LINK_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, consumeToken(builder_, STRING));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    exit_section_(builder_, level_, marker_, LINK_EXPR, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // '#{' string '}'
  public static boolean message_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "message_expr")) return false;
    if (!nextTokenIs(builder_, MESSAGE_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, MESSAGE_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, consumeToken(builder_, STRING));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    exit_section_(builder_, level_, marker_, MESSAGE_EXPR, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // expression
  static boolean root(PsiBuilder builder_, int level_) {
    return expression(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // '*{' string '}'
  public static boolean selection_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "selection_expr")) return false;
    if (!nextTokenIs(builder_, SELECTION_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, SELECTION_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, consumeToken(builder_, STRING));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    exit_section_(builder_, level_, marker_, SELECTION_EXPR, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // !('}')
  static boolean selection_expr_recover(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "selection_expr_recover")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NOT_, null);
    result_ = !selection_expr_recover_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, null, result_, false, null);
    return result_;
  }

  // ('}')
  private static boolean selection_expr_recover_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "selection_expr_recover_0")) return false;
    boolean result_ = false;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, EXPRESSION_END);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // '${' string '}'
  public static boolean variable_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variable_expr")) return false;
    if (!nextTokenIs(builder_, VARIABLE_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, null);
    result_ = consumeToken(builder_, VARIABLE_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, consumeToken(builder_, STRING));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    exit_section_(builder_, level_, marker_, VARIABLE_EXPR, result_, pinned_, null);
    return result_ || pinned_;
  }

  final static Parser selection_expr_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder builder_, int level_) {
      return selection_expr_recover(builder_, level_ + 1);
    }
  };
}
