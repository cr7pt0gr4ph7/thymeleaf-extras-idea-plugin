// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.fragment.signature.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class Visitor extends PsiElementVisitor {

  public void visitFragmentName(@NotNull FragmentName o) {
    visitFragmentSignaturePsiCompositeElement(o);
  }

  public void visitFragmentParameter(@NotNull FragmentParameter o) {
    visitFragmentSignaturePsiCompositeElement(o);
  }

  public void visitFragmentSignatureExpression(@NotNull FragmentSignatureExpression o) {
    visitFragmentSignaturePsiCompositeElement(o);
  }

  public void visitFragmentSignaturePsiCompositeElement(@NotNull FragmentSignaturePsiCompositeElement o) {
    visitElement(o);
  }

}
