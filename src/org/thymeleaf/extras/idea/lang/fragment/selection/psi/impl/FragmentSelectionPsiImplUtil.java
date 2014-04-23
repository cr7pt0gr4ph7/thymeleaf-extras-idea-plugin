package org.thymeleaf.extras.idea.lang.fragment.selection.psi.impl;

import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.fragment.selection.psi.DomSelector;
import org.thymeleaf.extras.idea.lang.fragment.selection.psi.FragmentSelectionExpression;
import org.thymeleaf.extras.idea.lang.fragment.selection.psi.TemplateName;

public class FragmentSelectionPsiImplUtil {
    /**
     * Infrastructure method. Use {@see FragmentSelectionExpression#getCompositeReferences} instead.
     */
    public static PsiReference[] getCompositeReferences(@NotNull final FragmentSelectionExpression selector) {
        final DomSelector domSelector = selector.getDomSelector();
        if (domSelector != null) {
            return domSelector.getReferences();
        }

        final TemplateName templateName = selector.getTemplateName();
        if (templateName != null) {
            return templateName.getReferences();
        }

        return PsiReference.EMPTY_ARRAY;
    }
}
