// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ContributedReferenceHost;
import com.intellij.psi.PsiLanguageInjectionHost;

public class Visitor extends PsiElementVisitor {

  public void visitAndExpr(@NotNull AndExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitConditionalExpr(@NotNull ConditionalExpr o) {
    visitExpression(o);
  }

  public void visitDefaultExpr(@NotNull DefaultExpr o) {
    visitExpression(o);
  }

  public void visitDivExpr(@NotNull DivExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitEqExpr(@NotNull EqExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitExpression(@NotNull Expression o) {
    visitPsiElement(o);
  }

  public void visitFakeBinaryExpression(@NotNull FakeBinaryExpression o) {
    visitExpression(o);
  }

  public void visitGenericSelectionExpr(@NotNull GenericSelectionExpr o) {
    visitExpression(o);
    // visitContributedReferenceHost(o);
    // visitPsiLanguageInjectionHost(o);
  }

  public void visitGtEqExpr(@NotNull GtEqExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitGtExpr(@NotNull GtExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitLinkExpr(@NotNull LinkExpr o) {
    visitGenericSelectionExpr(o);
  }

  public void visitLtEqExpr(@NotNull LtEqExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitLtExpr(@NotNull LtExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitMessageExpr(@NotNull MessageExpr o) {
    visitGenericSelectionExpr(o);
  }

  public void visitMinusExpr(@NotNull MinusExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitMulExpr(@NotNull MulExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitNegationExpr(@NotNull NegationExpr o) {
    visitExpression(o);
  }

  public void visitNeqExpr(@NotNull NeqExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitOrExpr(@NotNull OrExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitPlusExpr(@NotNull PlusExpr o) {
    visitFakeBinaryExpression(o);
  }

  public void visitRemainderExpr(@NotNull RemainderExpr o) {
    visitExpression(o);
  }

  public void visitSelectionExpr(@NotNull SelectionExpr o) {
    visitGenericSelectionExpr(o);
  }

  public void visitTextLiteralExpr(@NotNull TextLiteralExpr o) {
    visitExpression(o);
  }

  public void visitTokenExpr(@NotNull TokenExpr o) {
    visitExpression(o);
  }

  public void visitUnaryMinusExpr(@NotNull UnaryMinusExpr o) {
    visitExpression(o);
  }

  public void visitVariableExpr(@NotNull VariableExpr o) {
    visitGenericSelectionExpr(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
