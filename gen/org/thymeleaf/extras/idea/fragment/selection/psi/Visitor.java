// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.fragment.selection.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class Visitor extends PsiElementVisitor {

  public void visitDomSelector(@NotNull DomSelector o) {
    visitPsiElement(o);
  }

  public void visitExpression(@NotNull Expression o) {
    visitPsiElement(o);
  }

  public void visitFragmentSelectionExpression(@NotNull FragmentSelectionExpression o) {
    visitExpression(o);
  }

  public void visitTemplateName(@NotNull TemplateName o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
