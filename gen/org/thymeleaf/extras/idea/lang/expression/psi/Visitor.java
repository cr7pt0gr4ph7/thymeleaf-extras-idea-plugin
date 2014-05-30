// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ContributedReferenceHost;

public class Visitor extends PsiElementVisitor {

  public void visitConditionalExpr(@NotNull ConditionalExpr o) {
    visitExpression(o);
  }

  public void visitDefaultExpr(@NotNull DefaultExpr o) {
    visitExpression(o);
  }

  public void visitDivExpr(@NotNull DivExpr o) {
    visitExpression(o);
  }

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

  public void visitMinusExpr(@NotNull MinusExpr o) {
    visitExpression(o);
  }

  public void visitMulExpr(@NotNull MulExpr o) {
    visitExpression(o);
  }

  public void visitPlusExpr(@NotNull PlusExpr o) {
    visitExpression(o);
  }

  public void visitRemainderExpr(@NotNull RemainderExpr o) {
    visitExpression(o);
  }

  public void visitSelectionExpr(@NotNull SelectionExpr o) {
    visitGenericSelectionExpr(o);
  }

  public void visitTokenExpr(@NotNull TokenExpr o) {
    visitExpression(o);
  }

  public void visitVariableExpr(@NotNull VariableExpr o) {
    visitGenericSelectionExpr(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
