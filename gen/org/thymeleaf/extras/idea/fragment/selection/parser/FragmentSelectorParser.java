// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.fragment.selection.parser;

import org.jetbrains.annotations.*;
import com.intellij.lang.LighterASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.openapi.diagnostic.Logger;
import static org.thymeleaf.extras.idea.fragment.selection.parser.FragmentSelectorElementTypes.*;
import static org.thymeleaf.extras.idea.fragment.selection.parser.FragmentSelectorParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class FragmentSelectorParser implements PsiParser {

  public static Logger LOG_ = Logger.getInstance("org.thymeleaf.extras.idea.fragment.selection.parser.FragmentSelectorParser");

  @NotNull
  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    int level_ = 0;
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this);
    if (root_ == DOM_SELECTOR) {
      result_ = dom_selector(builder_, level_ + 1);
    }
    else if (root_ == EXPRESSION) {
      result_ = expression(builder_, level_ + 1);
    }
    else if (root_ == FRAGMENT_SELECTION_EXPRESSION) {
      result_ = fragment_selection_expression(builder_, level_ + 1);
    }
    else if (root_ == TEMPLATE_NAME) {
      result_ = template_name(builder_, level_ + 1);
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
    TokenSet.create(EXPRESSION, FRAGMENT_SELECTION_EXPRESSION),
  };

  public static boolean type_extends_(IElementType child_, IElementType parent_) {
    for (TokenSet set : EXTENDS_SETS_) {
      if (set.contains(child_) && set.contains(parent_)) return true;
    }
    return false;
  }

  /* ********************************************************** */
  // string
  public static boolean dom_selector(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dom_selector")) return false;
    if (!nextTokenIs(builder_, STRING)) return false;
    boolean result_ = false;
    Marker marker_ = builder_.mark();
    result_ = consumeToken(builder_, STRING);
    if (result_) {
      marker_.done(DOM_SELECTOR);
    }
    else {
      marker_.rollbackTo();
    }
    return result_;
  }

  /* ********************************************************** */
  // fragment_selection_expression
  public static boolean expression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expression")) return false;
    if (!nextTokenIs(builder_, OPERATOR) && !nextTokenIs(builder_, STRING)
        && replaceVariants(builder_, 2, "<expression>")) return false;
    boolean result_ = false;
    int start_ = builder_.getCurrentOffset();
    Marker marker_ = builder_.mark();
    enterErrorRecordingSection(builder_, level_, _SECTION_GENERAL_, "<expression>");
    result_ = fragment_selection_expression(builder_, level_ + 1);
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
  // '(' param_expr (',' param_expr)* ')'
  static boolean fragment_paramlist(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fragment_paramlist")) return false;
    if (!nextTokenIs(builder_, OPEN_PARENS)) return false;
    boolean result_ = false;
    boolean pinned_ = false;
    Marker marker_ = builder_.mark();
    enterErrorRecordingSection(builder_, level_, _SECTION_GENERAL_, null);
    result_ = consumeToken(builder_, OPEN_PARENS);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, param_expr(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, fragment_paramlist_2(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, CLOSE_PARENS) && result_;
    if (!result_ && !pinned_) {
      marker_.rollbackTo();
    }
    else {
      marker_.drop();
    }
    result_ = exitErrorRecordingSection(builder_, level_, result_, pinned_, _SECTION_GENERAL_, null);
    return result_ || pinned_;
  }

  // (',' param_expr)*
  private static boolean fragment_paramlist_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fragment_paramlist_2")) return false;
    int offset_ = builder_.getCurrentOffset();
    while (true) {
      if (!fragment_paramlist_2_0(builder_, level_ + 1)) break;
      int next_offset_ = builder_.getCurrentOffset();
      if (offset_ == next_offset_) {
        empty_element_parsed_guard_(builder_, offset_, "fragment_paramlist_2");
        break;
      }
      offset_ = next_offset_;
    }
    return true;
  }

  // ',' param_expr
  private static boolean fragment_paramlist_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fragment_paramlist_2_0")) return false;
    boolean result_ = false;
    Marker marker_ = builder_.mark();
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && param_expr(builder_, level_ + 1);
    if (!result_) {
      marker_.rollbackTo();
    }
    else {
      marker_.drop();
    }
    return result_;
  }

  /* ********************************************************** */
  // fragment_spec [fragment_paramlist]
  public static boolean fragment_selection_expression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fragment_selection_expression")) return false;
    if (!nextTokenIs(builder_, OPERATOR) && !nextTokenIs(builder_, STRING)
        && replaceVariants(builder_, 2, "<expression>")) return false;
    boolean result_ = false;
    int start_ = builder_.getCurrentOffset();
    Marker marker_ = builder_.mark();
    enterErrorRecordingSection(builder_, level_, _SECTION_GENERAL_, "<expression>");
    result_ = fragment_spec(builder_, level_ + 1);
    result_ = result_ && fragment_selection_expression_1(builder_, level_ + 1);
    LighterASTNode last_ = result_? builder_.getLatestDoneMarker() : null;
    if (last_ != null && last_.getStartOffset() == start_ && type_extends_(last_.getTokenType(), FRAGMENT_SELECTION_EXPRESSION)) {
      marker_.drop();
    }
    else if (result_) {
      marker_.done(FRAGMENT_SELECTION_EXPRESSION);
    }
    else {
      marker_.rollbackTo();
    }
    result_ = exitErrorRecordingSection(builder_, level_, result_, false, _SECTION_GENERAL_, null);
    return result_;
  }

  // [fragment_paramlist]
  private static boolean fragment_selection_expression_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fragment_selection_expression_1")) return false;
    fragment_paramlist(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // (template_name ['::' dom_selector]) | ('::' dom_selector)
  static boolean fragment_spec(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fragment_spec")) return false;
    if (!nextTokenIs(builder_, OPERATOR) && !nextTokenIs(builder_, STRING)) return false;
    boolean result_ = false;
    Marker marker_ = builder_.mark();
    result_ = fragment_spec_0(builder_, level_ + 1);
    if (!result_) result_ = fragment_spec_1(builder_, level_ + 1);
    if (!result_) {
      marker_.rollbackTo();
    }
    else {
      marker_.drop();
    }
    return result_;
  }

  // template_name ['::' dom_selector]
  private static boolean fragment_spec_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fragment_spec_0")) return false;
    boolean result_ = false;
    Marker marker_ = builder_.mark();
    result_ = template_name(builder_, level_ + 1);
    result_ = result_ && fragment_spec_0_1(builder_, level_ + 1);
    if (!result_) {
      marker_.rollbackTo();
    }
    else {
      marker_.drop();
    }
    return result_;
  }

  // ['::' dom_selector]
  private static boolean fragment_spec_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fragment_spec_0_1")) return false;
    fragment_spec_0_1_0(builder_, level_ + 1);
    return true;
  }

  // '::' dom_selector
  private static boolean fragment_spec_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fragment_spec_0_1_0")) return false;
    boolean result_ = false;
    Marker marker_ = builder_.mark();
    result_ = consumeToken(builder_, OPERATOR);
    result_ = result_ && dom_selector(builder_, level_ + 1);
    if (!result_) {
      marker_.rollbackTo();
    }
    else {
      marker_.drop();
    }
    return result_;
  }

  // '::' dom_selector
  private static boolean fragment_spec_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "fragment_spec_1")) return false;
    boolean result_ = false;
    Marker marker_ = builder_.mark();
    result_ = consumeToken(builder_, OPERATOR);
    result_ = result_ && dom_selector(builder_, level_ + 1);
    if (!result_) {
      marker_.rollbackTo();
    }
    else {
      marker_.drop();
    }
    return result_;
  }

  /* ********************************************************** */
  // string
  static boolean param_expr(PsiBuilder builder_, int level_) {
    return consumeToken(builder_, STRING);
  }

  /* ********************************************************** */
  // expression
  static boolean root(PsiBuilder builder_, int level_) {
    return expression(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // string
  public static boolean template_name(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "template_name")) return false;
    if (!nextTokenIs(builder_, STRING)) return false;
    boolean result_ = false;
    Marker marker_ = builder_.mark();
    result_ = consumeToken(builder_, STRING);
    if (result_) {
      marker_.done(TEMPLATE_NAME);
    }
    else {
      marker_.rollbackTo();
    }
    return result_;
  }

}
