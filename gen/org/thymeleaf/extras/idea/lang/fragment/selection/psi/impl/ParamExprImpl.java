// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.fragment.selection.psi.impl;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static org.thymeleaf.extras.idea.lang.fragment.selection.parser.FragmentSelectorElementTypes.*;
import org.thymeleaf.extras.idea.lang.fragment.selection.psi.*;

public class ParamExprImpl extends FragmentSelectorPsiCompositeElementImpl implements ParamExpr {

  public ParamExprImpl(ASTNode node) {
    super(node);
  }

  @Override
  @NotNull
  public PsiElement getString() {
    return findNotNullChildByType(STRING);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) ((Visitor)visitor).visitParamExpr(this);
    else super.accept(visitor);
  }

}
