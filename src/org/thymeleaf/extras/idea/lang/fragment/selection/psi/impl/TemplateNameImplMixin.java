package org.thymeleaf.extras.idea.lang.fragment.selection.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.ContributedReferenceHost;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceService;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.fragment.selection.psi.TemplateName;

public abstract class TemplateNameImplMixin extends FragmentSelectorPsiCompositeElementImpl implements TemplateName, ContributedReferenceHost {
    public TemplateNameImplMixin(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        return PsiReferenceService.getService().getContributedReferences(this);
    }
}
