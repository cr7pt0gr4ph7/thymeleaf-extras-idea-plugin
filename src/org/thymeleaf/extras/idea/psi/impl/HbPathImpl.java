package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.psi.HbPath;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class HbPathImpl extends HbPsiElementImpl implements HbPath {
    public HbPathImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    public String getName() {
        return getText();
    }
}
