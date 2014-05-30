// This is a generated file. Not intended for manual editing.
package org.thymeleaf.extras.idea.lang.expression.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ContributedReferenceHost;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.PsiReference;
import org.thymeleaf.extras.idea.lang.expression.psi.impl.GenericSelectionEscaper;

public interface GenericSelectionExpr extends Expression, ContributedReferenceHost, PsiLanguageInjectionHost {

  @NotNull
  PsiReference[] getReferences();

  boolean isValidHost();

  PsiLanguageInjectionHost updateText(String text);

  @NotNull
  GenericSelectionEscaper createLiteralTextEscaper();

  @Nullable
  PsiElement getString();

}
