// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.fragment.signature.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.thymeleaf.extras.idea.fragment.signature.parser.FragmentSignatureElementTypes.*;
import org.thymeleaf.extras.idea.fragment.signature.psi.*;

public class FragmentSignatureExpressionImpl extends FragmentSignaturePsiCompositeElementImpl implements FragmentSignatureExpression {

  public FragmentSignatureExpressionImpl(ASTNode node) {
    super(node);
  }

  @Override
  @NotNull
  public FragmentName getFragmentName() {
    return findNotNullChildByClass(FragmentName.class);
  }

  @Override
  @NotNull
  public List<FragmentParameter> getFragmentParameterList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, FragmentParameter.class);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) ((Visitor)visitor).visitFragmentSignatureExpression(this);
    else super.accept(visitor);
  }

}
