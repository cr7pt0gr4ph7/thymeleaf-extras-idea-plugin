// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.fragment.selection.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface FragmentSelectionExpression extends FragmentSelectorPsiCompositeElement {

  @Nullable
  DomSelector getDomSelector();

  @NotNull
  List<ParamExpr> getParamExprList();

  @Nullable
  TemplateName getTemplateName();

}
