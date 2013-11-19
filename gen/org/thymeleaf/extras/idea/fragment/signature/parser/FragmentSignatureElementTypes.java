// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.fragment.signature.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.thymeleaf.extras.idea.fragment.signature.psi.impl.*;

public interface FragmentSignatureElementTypes {

  IElementType FRAGMENT_NAME = new FragmentSignatureCompositeElementType("FRAGMENT_NAME");
  IElementType FRAGMENT_PARAMETER = new FragmentSignatureCompositeElementType("FRAGMENT_PARAMETER");
  IElementType FRAGMENT_SIGNATURE_EXPRESSION = new FragmentSignatureCompositeElementType("FRAGMENT_SIGNATURE_EXPRESSION");

  IElementType CLOSE_PARENS = new FragmentSignatureElementType(")");
  IElementType COMMA = new FragmentSignatureElementType(",");
  IElementType OPEN_PARENS = new FragmentSignatureElementType("(");
  IElementType STRING = new FragmentSignatureElementType("string");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == FRAGMENT_NAME) {
        return new FragmentNameImpl(node);
      }
      else if (type == FRAGMENT_PARAMETER) {
        return new FragmentParameterImpl(node);
      }
      else if (type == FRAGMENT_SIGNATURE_EXPRESSION) {
        return new FragmentSignatureExpressionImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
