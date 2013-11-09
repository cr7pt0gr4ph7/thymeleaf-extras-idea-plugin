// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.fragment.selection.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.thymeleaf.extras.idea.fragment.selection.parser.FragmentSelectorElementTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.thymeleaf.extras.idea.fragment.selection.psi.*;

public class TemplateNameImpl extends ASTWrapperPsiElement implements TemplateName {

  public TemplateNameImpl(ASTNode node) {
    super(node);
  }

  @Override
  @NotNull
  public PsiElement getString() {
    return findNotNullChildByType(STRING);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) ((Visitor)visitor).visitTemplateName(this);
    else super.accept(visitor);
  }

}
