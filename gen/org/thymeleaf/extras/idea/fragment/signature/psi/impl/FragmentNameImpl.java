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

public class FragmentNameImpl extends FragmentNameImplMixin implements FragmentName {

  public FragmentNameImpl(ASTNode node) {
    super(node);
  }

  @Override
  @NotNull
  public PsiElement getString() {
    return findNotNullChildByType(STRING);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) ((Visitor)visitor).visitFragmentName(this);
    else super.accept(visitor);
  }

}
