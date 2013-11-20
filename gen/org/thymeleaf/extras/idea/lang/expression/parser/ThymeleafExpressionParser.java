// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.parser;

import org.jetbrains.annotations.*;
import com.intellij.lang.LighterASTNode;
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

  public static Logger LOG_ = Logger.getInstance("org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionParser");

  @NotNull
  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    int level_ = 0;
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this);
    if (root_ == EXPRESSION) {
      result_ = expression(builder_, level_ + 1);
    }
    else if (root_ == GENERIC_SELECTION_EXPR) {
      result_ = generic_selection_expr(builder_, level_ + 1);
    }
    else if (root_ == LINK_EXPR) {
      result_ = link_expr(builder_, level_ + 1);
    }
    else if (root_ == MESSAGE_EXPR) {
      result_ = message_expr(builder_, level_ + 1);
    }
    else if (root_ == SELECTION_EXPR) {
      result_ = selection_expr(builder_, level_ + 1);
    }
    else if (root_ == VARIABLE_EXPR) {
      result_ = variable_expr(builder_, level_ + 1);
    }
    else {
      Marker marker_ = builder_.mark();
      enterErrorRecordingSection(builder_, level_, _SECTION_RECOVER_, null);
      result_ = parse_root_(root_, builder_, level_);
      exitErrorRecordingSection(builder_, level_, result_, true, _SECTION_RECOVER_, TOKEN_ADVANCER);
      marker_.done(root_);
    }
    return builder_.getTreeBuilt();
  }

  protected boolean parse_root_(final IElementType root_, final PsiBuilder builder_, final int level_) {
    return root(builder_, level_ + 1);
  }

  private static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    TokenSet.create(EXPRESSION, GENERIC_SELECTION_EXPR, LINK_EXPR, MESSAGE_EXPR,
      SELECTION_EXPR, VARIABLE_EXPR),
    TokenSet.create(GENERIC_SELECTION_EXPR, LINK_EXPR, MESSAGE_EXPR, SELECTION_EXPR,
      VARIABLE_EXPR),
  };

  public static boolean type_extends_(IElementType child_, IElementType parent_) {
    for (TokenSet set : EXTENDS_SETS_) {
      if (set.contains(child_) && set.contains(parent_)) return true;
    }
    return false;
  }

  /* ********************************************************** */
  // generic_selection_expr
  public static boolean expression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expression")) return false;
    boolean result_ = false;
    int start_ = builder_.getCurrentOffset();
    Marker marker_ = builder_.mark();
    enterErrorRecordingSection(builder_, level_, _SECTION_GENERAL_, "<expression>");
    result_ = generic_selection_expr(builder_, level_ + 1);
    LighterASTNode last_ = result_? builder_.getLatestDoneMarker() : null;
    if (last_ != null && last_.getStartOffset() == start_ && type_extends_(last_.getTokenType(), EXPRESSION)) {
      marker_.drop();
    }
    else if (result_) {
      marker_.done(EXPRESSION);
    }
    else {
      marker_.rollbackTo();
    }
    result_ = exitErrorRecordingSection(builder_, level_, result_, false, _SECTION_GENERAL_, null);
    return result_;
  }

  /* ********************************************************** */
  // variable_expr | selection_expr | link_expr | message_expr
  public static boolean generic_selection_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "generic_selection_expr")) return false;
    boolean result_ = false;
    int start_ = builder_.getCurrentOffset();
    Marker marker_ = builder_.mark();
    enterErrorRecordingSection(builder_, level_, _SECTION_RECOVER_, "<expression>");
    result_ = variable_expr(builder_, level_ + 1);
    if (!result_) result_ = selection_expr(builder_, level_ + 1);
    if (!result_) result_ = link_expr(builder_, level_ + 1);
    if (!result_) result_ = message_expr(builder_, level_ + 1);
    LighterASTNode last_ = result_? builder_.getLatestDoneMarker() : null;
    if (last_ != null && last_.getStartOffset() == start_ && type_extends_(last_.getTokenType(), GENERIC_SELECTION_EXPR)) {
      marker_.drop();
    }
    else if (result_) {
      marker_.done(GENERIC_SELECTION_EXPR);
    }
    else {
      marker_.rollbackTo();
    }
    result_ = exitErrorRecordingSection(builder_, level_, result_, false, _SECTION_RECOVER_, selection_expr_recover_parser_);
    return result_;
  }

  /* ********************************************************** */
  // '@{' string '}'
  public static boolean link_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "link_expr")) return false;
    if (!nextTokenIs(builder_, LINK_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = builder_.mark();
    enterErrorRecordingSection(builder_, level_, _SECTION_GENERAL_, null);
    result_ = consumeToken(builder_, LINK_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, consumeToken(builder_, STRING));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    if (result_ || pinned_) {
      marker_.done(LINK_EXPR);
    }
    else {
      marker_.rollbackTo();
    }
    result_ = exitErrorRecordingSection(builder_, level_, result_, pinned_, _SECTION_GENERAL_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // '#{' string '}'
  public static boolean message_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "message_expr")) return false;
    if (!nextTokenIs(builder_, MESSAGE_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = builder_.mark();
    enterErrorRecordingSection(builder_, level_, _SECTION_GENERAL_, null);
    result_ = consumeToken(builder_, MESSAGE_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, consumeToken(builder_, STRING));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    if (result_ || pinned_) {
      marker_.done(MESSAGE_EXPR);
    }
    else {
      marker_.rollbackTo();
    }
    result_ = exitErrorRecordingSection(builder_, level_, result_, pinned_, _SECTION_GENERAL_, null);
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
    Marker marker_ = builder_.mark();
    enterErrorRecordingSection(builder_, level_, _SECTION_GENERAL_, null);
    result_ = consumeToken(builder_, SELECTION_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, consumeToken(builder_, STRING));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    if (result_ || pinned_) {
      marker_.done(SELECTION_EXPR);
    }
    else {
      marker_.rollbackTo();
    }
    result_ = exitErrorRecordingSection(builder_, level_, result_, pinned_, _SECTION_GENERAL_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // !('}')
  static boolean selection_expr_recover(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "selection_expr_recover")) return false;
    boolean result_ = false;
    Marker marker_ = builder_.mark();
    enterErrorRecordingSection(builder_, level_, _SECTION_NOT_, null);
    result_ = !selection_expr_recover_0(builder_, level_ + 1);
    marker_.rollbackTo();
    result_ = exitErrorRecordingSection(builder_, level_, result_, false, _SECTION_NOT_, null);
    return result_;
  }

  // ('}')
  private static boolean selection_expr_recover_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "selection_expr_recover_0")) return false;
    boolean result_ = false;
    Marker marker_ = builder_.mark();
    result_ = consumeToken(builder_, EXPRESSION_END);
    if (!result_) {
      marker_.rollbackTo();
    }
    else {
      marker_.drop();
    }
    return result_;
  }

  /* ********************************************************** */
  // '${' string '}'
  public static boolean variable_expr(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variable_expr")) return false;
    if (!nextTokenIs(builder_, VARIABLE_EXPR_START)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = builder_.mark();
    enterErrorRecordingSection(builder_, level_, _SECTION_GENERAL_, null);
    result_ = consumeToken(builder_, VARIABLE_EXPR_START);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, consumeToken(builder_, STRING));
    result_ = pinned_ && consumeToken(builder_, EXPRESSION_END) && result_;
    if (result_ || pinned_) {
      marker_.done(VARIABLE_EXPR);
    }
    else {
      marker_.rollbackTo();
    }
    result_ = exitErrorRecordingSection(builder_, level_, result_, pinned_, _SECTION_GENERAL_, null);
    return result_ || pinned_;
  }

  final static Parser selection_expr_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder builder_, int level_) {
      return selection_expr_recover(builder_, level_ + 1);
    }
  };
}
