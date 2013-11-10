// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.fragment.selection.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class Visitor extends PsiElementVisitor {

  public void visitDomSelector(@NotNull DomSelector o) {
    visitFragmentSelectorPsiCompositeElement(o);
  }

  public void visitFragmentSelectionExpression(@NotNull FragmentSelectionExpression o) {
    visitFragmentSelectorPsiCompositeElement(o);
  }

  public void visitTemplateName(@NotNull TemplateName o) {
    visitFragmentSelectorPsiCompositeElement(o);
  }

  public void visitFragmentSelectorPsiCompositeElement(@NotNull FragmentSelectorPsiCompositeElement o) {
    visitElement(o);
  }

}
