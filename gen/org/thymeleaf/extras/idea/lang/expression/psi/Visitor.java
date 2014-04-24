// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ContributedReferenceHost;

public class Visitor extends PsiElementVisitor {

  public void visitExpression(@NotNull Expression o) {
    visitPsiElement(o);
  }

  public void visitGenericSelectionExpr(@NotNull GenericSelectionExpr o) {
    visitExpression(o);
    // visitContributedReferenceHost(o);
  }

  public void visitLinkExpr(@NotNull LinkExpr o) {
    visitGenericSelectionExpr(o);
  }

  public void visitMessageExpr(@NotNull MessageExpr o) {
    visitGenericSelectionExpr(o);
  }

  public void visitSelectionExpr(@NotNull SelectionExpr o) {
    visitGenericSelectionExpr(o);
  }

  public void visitVariableExpr(@NotNull VariableExpr o) {
    visitGenericSelectionExpr(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
