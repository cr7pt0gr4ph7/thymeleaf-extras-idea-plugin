// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface FakeBinaryExpression extends Expression {

  @NotNull
  List<Expression> getExpressionList();

  @NotNull
  Expression getLeft();

  @Nullable
  Expression getRight();

}
