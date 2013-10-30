package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.psi.HbParam;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class HbParamImpl extends HbPsiElementImpl implements HbParam {
    public HbParamImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }
}
