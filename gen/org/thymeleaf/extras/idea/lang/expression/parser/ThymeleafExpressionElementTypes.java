// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.thymeleaf.extras.idea.lang.expression.psi.impl.*;

public interface ThymeleafExpressionElementTypes {

  IElementType EXPRESSION = new ThymeleafExpressionCompositeElementType("EXPRESSION");
  IElementType GENERIC_SELECTION_EXPR = new ThymeleafExpressionCompositeElementType("GENERIC_SELECTION_EXPR");
  IElementType LINK_EXPR = new ThymeleafExpressionCompositeElementType("LINK_EXPR");
  IElementType MESSAGE_EXPR = new ThymeleafExpressionCompositeElementType("MESSAGE_EXPR");
  IElementType SELECTION_EXPR = new ThymeleafExpressionCompositeElementType("SELECTION_EXPR");
  IElementType VARIABLE_EXPR = new ThymeleafExpressionCompositeElementType("VARIABLE_EXPR");

  IElementType EXPRESSION_END = new ThymeleafExpressionElementType("}");
  IElementType LINK_EXPR_START = new ThymeleafExpressionElementType("@{");
  IElementType MESSAGE_EXPR_START = new ThymeleafExpressionElementType("#{");
  IElementType SELECTION_EXPR_START = new ThymeleafExpressionElementType("*{");
  IElementType STRING = new ThymeleafExpressionElementType("string");
  IElementType VARIABLE_EXPR_START = new ThymeleafExpressionElementType("${");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == EXPRESSION) {
        return new ExpressionImpl(node);
      }
      else if (type == GENERIC_SELECTION_EXPR) {
        return new GenericSelectionExprImpl(node);
      }
      else if (type == LINK_EXPR) {
        return new LinkExprImpl(node);
      }
      else if (type == MESSAGE_EXPR) {
        return new MessageExprImpl(node);
      }
      else if (type == SELECTION_EXPR) {
        return new SelectionExprImpl(node);
      }
      else if (type == VARIABLE_EXPR) {
        return new VariableExprImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
