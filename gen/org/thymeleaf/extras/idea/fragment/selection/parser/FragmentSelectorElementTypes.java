// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.fragment.selection.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.thymeleaf.extras.idea.fragment.selection.psi.impl.*;

public interface FragmentSelectorElementTypes {

  IElementType EXPRESSION = new FragmentSelectorCompositeElementType("EXPRESSION");
  IElementType FRAGMENT_SELECTION_EXPRESSION = new FragmentSelectorCompositeElementType("FRAGMENT_SELECTION_EXPRESSION");

  IElementType CLOSE_ARRAY = new FragmentSelectorElementType("]");
  IElementType CLOSE_PARENS = new FragmentSelectorElementType(")");
  IElementType COMMA = new FragmentSelectorElementType(",");
  IElementType OPEN_ARRAY = new FragmentSelectorElementType("[");
  IElementType OPEN_PARENS = new FragmentSelectorElementType("(");
  IElementType OPERATOR = new FragmentSelectorElementType("::");
  IElementType STRING = new FragmentSelectorElementType("string");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == EXPRESSION) {
        return new ExpressionImpl(node);
      }
      else if (type == FRAGMENT_SELECTION_EXPRESSION) {
        return new FragmentSelectionExpressionImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
