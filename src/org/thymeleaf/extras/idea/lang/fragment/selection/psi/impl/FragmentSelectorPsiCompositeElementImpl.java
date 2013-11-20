package org.thymeleaf.extras.idea.lang.fragment.selection.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.fragment.selection.psi.FragmentSelectorPsiCompositeElement;

public abstract class FragmentSelectorPsiCompositeElementImpl extends ASTWrapperPsiElement implements FragmentSelectorPsiCompositeElement {
    public FragmentSelectorPsiCompositeElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return getNode().getElementType().toString();
    }
}
