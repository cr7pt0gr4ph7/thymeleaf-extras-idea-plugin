// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.fragment.selection.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface FragmentSelectionExpression extends FragmentSelectorPsiCompositeElement {

  @Nullable
  DomSelector getDomSelector();

  @NotNull
  List<ParamExpr> getParamExprList();

  @Nullable
  TemplateName getTemplateName();

}
