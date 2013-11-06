// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.fragment.selection.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.thymeleaf.extras.idea.fragment.selection.parser.FragmentSelectorElementTypes.*;
import org.thymeleaf.extras.idea.fragment.selection.psi.*;

public class FragmentSelectionExpressionImpl extends ExpressionImpl implements FragmentSelectionExpression {

  public FragmentSelectionExpressionImpl(ASTNode node) {
    super(node);
  }

  @Override
  @Nullable
  public PsiElement getString() {
    return findChildByType(STRING);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) ((Visitor)visitor).visitFragmentSelectionExpression(this);
    else super.accept(visitor);
  }

}
