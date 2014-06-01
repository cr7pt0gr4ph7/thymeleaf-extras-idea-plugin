// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.thymeleaf.extras.idea.lang.expression.psi.impl.*;

public interface ThymeleafExpressionElementTypes {

  IElementType AND_EXPR = new ThymeleafExpressionCompositeElementType("AND_EXPR");
  IElementType CONDITIONAL_EXPR = new ThymeleafExpressionCompositeElementType("CONDITIONAL_EXPR");
  IElementType DEFAULT_EXPR = new ThymeleafExpressionCompositeElementType("DEFAULT_EXPR");
  IElementType DIV_EXPR = new ThymeleafExpressionCompositeElementType("DIV_EXPR");
  IElementType EQ_EXPR = new ThymeleafExpressionCompositeElementType("EQ_EXPR");
  IElementType EXPRESSION = new ThymeleafExpressionCompositeElementType("EXPRESSION");
  IElementType GENERIC_SELECTION_EXPR = new ThymeleafExpressionCompositeElementType("GENERIC_SELECTION_EXPR");
  IElementType GT_EQ_EXPR = new ThymeleafExpressionCompositeElementType("GT_EQ_EXPR");
  IElementType GT_EXPR = new ThymeleafExpressionCompositeElementType("GT_EXPR");
  IElementType LINK_EXPR = new ThymeleafExpressionCompositeElementType("LINK_EXPR");
  IElementType LT_EQ_EXPR = new ThymeleafExpressionCompositeElementType("LT_EQ_EXPR");
  IElementType LT_EXPR = new ThymeleafExpressionCompositeElementType("LT_EXPR");
  IElementType MESSAGE_EXPR = new ThymeleafExpressionCompositeElementType("MESSAGE_EXPR");
  IElementType MINUS_EXPR = new ThymeleafExpressionCompositeElementType("MINUS_EXPR");
  IElementType MUL_EXPR = new ThymeleafExpressionCompositeElementType("MUL_EXPR");
  IElementType NEGATION_EXPR = new ThymeleafExpressionCompositeElementType("NEGATION_EXPR");
  IElementType NEQ_EXPR = new ThymeleafExpressionCompositeElementType("NEQ_EXPR");
  IElementType OR_EXPR = new ThymeleafExpressionCompositeElementType("OR_EXPR");
  IElementType PLUS_EXPR = new ThymeleafExpressionCompositeElementType("PLUS_EXPR");
  IElementType REMAINDER_EXPR = new ThymeleafExpressionCompositeElementType("REMAINDER_EXPR");
  IElementType SELECTION_EXPR = new ThymeleafExpressionCompositeElementType("SELECTION_EXPR");
  IElementType TEXT_LITERAL_EXPR = new ThymeleafExpressionCompositeElementType("TEXT_LITERAL_EXPR");
  IElementType TOKEN_EXPR = new ThymeleafExpressionCompositeElementType("TOKEN_EXPR");
  IElementType UNARY_MINUS_EXPR = new ThymeleafExpressionCompositeElementType("UNARY_MINUS_EXPR");
  IElementType VARIABLE_EXPR = new ThymeleafExpressionCompositeElementType("VARIABLE_EXPR");

  IElementType CONVERTED_EXPRESSION_END = new ThymeleafExpressionElementType("}}");
  IElementType CONVERTED_SELECTION_EXPR_START = new ThymeleafExpressionElementType("*{{");
  IElementType CONVERTED_VARIABLE_EXPR_START = new ThymeleafExpressionElementType("${{");
  IElementType EXPRESSION_END = new ThymeleafExpressionElementType("}");
  IElementType EXPRESSION_STRING = new ThymeleafExpressionStringElementType("expression_string");
  IElementType LINK_EXPR_START = new ThymeleafExpressionElementType("@{");
  IElementType MESSAGE_EXPR_START = new ThymeleafExpressionElementType("#{");
  IElementType OP_AND = new ThymeleafExpressionElementType("and");
  IElementType OP_COLON = new ThymeleafExpressionElementType(":");
  IElementType OP_CONDITIONAL = new ThymeleafExpressionElementType("?");
  IElementType OP_DEFAULT = new ThymeleafExpressionElementType("?:");
  IElementType OP_DIV = new ThymeleafExpressionElementType("/");
  IElementType OP_EQ = new ThymeleafExpressionElementType("==");
  IElementType OP_GT = new ThymeleafExpressionElementType(">");
  IElementType OP_GT_EQ = new ThymeleafExpressionElementType(">=");
  IElementType OP_LT = new ThymeleafExpressionElementType("<");
  IElementType OP_LT_EQ = new ThymeleafExpressionElementType("<=");
  IElementType OP_MINUS = new ThymeleafExpressionElementType("-");
  IElementType OP_MUL = new ThymeleafExpressionElementType("*");
  IElementType OP_NOT = new ThymeleafExpressionElementType("not");
  IElementType OP_NOT_EQ = new ThymeleafExpressionElementType("!=");
  IElementType OP_NOT_SYM = new ThymeleafExpressionElementType("!");
  IElementType OP_OR = new ThymeleafExpressionElementType("or");
  IElementType OP_PLUS = new ThymeleafExpressionElementType("+");
  IElementType OP_REMAINDER = new ThymeleafExpressionElementType("%");
  IElementType SELECTION_EXPR_START = new ThymeleafExpressionElementType("*{");
  IElementType STRING = new ThymeleafExpressionElementType("string");
  IElementType TOKEN = new ThymeleafExpressionElementType("token");
  IElementType VARIABLE_EXPR_START = new ThymeleafExpressionElementType("${");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == AND_EXPR) {
        return new AndExprImpl(node);
      }
      else if (type == CONDITIONAL_EXPR) {
        return new ConditionalExprImpl(node);
      }
      else if (type == DEFAULT_EXPR) {
        return new DefaultExprImpl(node);
      }
      else if (type == DIV_EXPR) {
        return new DivExprImpl(node);
      }
      else if (type == EQ_EXPR) {
        return new EqExprImpl(node);
      }
      else if (type == EXPRESSION) {
        return new ExpressionImpl(node);
      }
      else if (type == GENERIC_SELECTION_EXPR) {
        return new GenericSelectionExprImpl(node);
      }
      else if (type == GT_EQ_EXPR) {
        return new GtEqExprImpl(node);
      }
      else if (type == GT_EXPR) {
        return new GtExprImpl(node);
      }
      else if (type == LINK_EXPR) {
        return new LinkExprImpl(node);
      }
      else if (type == LT_EQ_EXPR) {
        return new LtEqExprImpl(node);
      }
      else if (type == LT_EXPR) {
        return new LtExprImpl(node);
      }
      else if (type == MESSAGE_EXPR) {
        return new MessageExprImpl(node);
      }
      else if (type == MINUS_EXPR) {
        return new MinusExprImpl(node);
      }
      else if (type == MUL_EXPR) {
        return new MulExprImpl(node);
      }
      else if (type == NEGATION_EXPR) {
        return new NegationExprImpl(node);
      }
      else if (type == NEQ_EXPR) {
        return new NeqExprImpl(node);
      }
      else if (type == OR_EXPR) {
        return new OrExprImpl(node);
      }
      else if (type == PLUS_EXPR) {
        return new PlusExprImpl(node);
      }
      else if (type == REMAINDER_EXPR) {
        return new RemainderExprImpl(node);
      }
      else if (type == SELECTION_EXPR) {
        return new SelectionExprImpl(node);
      }
      else if (type == TEXT_LITERAL_EXPR) {
        return new TextLiteralExprImpl(node);
      }
      else if (type == TOKEN_EXPR) {
        return new TokenExprImpl(node);
      }
      else if (type == UNARY_MINUS_EXPR) {
        return new UnaryMinusExprImpl(node);
      }
      else if (type == VARIABLE_EXPR) {
        return new VariableExprImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
