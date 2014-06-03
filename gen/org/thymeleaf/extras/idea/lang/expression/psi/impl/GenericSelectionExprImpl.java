// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes.*;
import org.thymeleaf.extras.idea.lang.expression.psi.*;
import com.intellij.psi.LiteralTextEscaper;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.PsiReference;

public class GenericSelectionExprImpl extends ExpressionImpl implements GenericSelectionExpr {

  public GenericSelectionExprImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) ((Visitor)visitor).visitGenericSelectionExpr(this);
    else super.accept(visitor);
  }

  @NotNull
  public PsiReference[] getReferences() {
    return ThymeleafExpressionPsiImplUtil.getReferences(this);
  }

  public boolean isValidHost() {
    return ThymeleafExpressionPsiImplUtil.isValidHost(this);
  }

  public PsiLanguageInjectionHost updateText(String text) {
    return ThymeleafExpressionPsiImplUtil.updateText(this, text);
  }

  @NotNull
  public LiteralTextEscaper<GenericSelectionExpr> createLiteralTextEscaper() {
    return ThymeleafExpressionPsiImplUtil.createLiteralTextEscaper(this);
  }

  @Nullable
  public PsiElement getExpressionString() {
    return ThymeleafExpressionPsiImplUtil.getExpressionString(this);
  }

}
