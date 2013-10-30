package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.psi.HbPartialName;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class HbPartialNameImpl extends HbPsiElementImpl implements HbPartialName {
    public HbPartialNameImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    public String getName() {
        return getText();
    }
}
