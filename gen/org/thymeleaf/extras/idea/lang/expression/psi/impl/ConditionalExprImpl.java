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

public class ConditionalExprImpl extends ExpressionImpl implements ConditionalExpr {

  public ConditionalExprImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) ((Visitor)visitor).visitConditionalExpr(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<Expression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, Expression.class);
  }

  @Override
  @NotNull
  public Expression getCondition() {
    List<Expression> p1 = getExpressionList();
    return p1.get(0);
  }

  @Override
  @Nullable
  public Expression getThen() {
    List<Expression> p1 = getExpressionList();
    return p1.size() < 2 ? null : p1.get(1);
  }

  @Override
  @Nullable
  public Expression getElse() {
    List<Expression> p1 = getExpressionList();
    return p1.size() < 3 ? null : p1.get(2);
  }

}
