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

public class MessageExprImpl extends GenericSelectionExprImpl implements MessageExpr {

  public MessageExprImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) ((Visitor)visitor).visitMessageExpr(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getString() {
    return findChildByType(EXPRESSION_STRING);
  }

}
