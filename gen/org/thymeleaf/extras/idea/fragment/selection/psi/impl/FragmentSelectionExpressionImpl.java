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

public class FragmentSelectionExpressionImpl extends FragmentSelectorPsiCompositeElementImpl implements FragmentSelectionExpression {

  public FragmentSelectionExpressionImpl(ASTNode node) {
    super(node);
  }

  @Override
  @Nullable
  public DomSelector getDomSelector() {
    return findChildByClass(DomSelector.class);
  }

  @Override
  @Nullable
  public TemplateName getTemplateName() {
    return findChildByClass(TemplateName.class);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) ((Visitor)visitor).visitFragmentSelectionExpression(this);
    else super.accept(visitor);
  }

}
