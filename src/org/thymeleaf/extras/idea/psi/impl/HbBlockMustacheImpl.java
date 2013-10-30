package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.psi.HbBlockMustache;
import org.thymeleaf.extras.idea.psi.HbPath;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class HbBlockMustacheImpl extends HbPsiElementImpl implements HbBlockMustache {
    protected HbBlockMustacheImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    @Nullable
    public HbPath getBlockMainPath() {
        return PsiTreeUtil.findChildOfType(this, HbPath.class);
    }

    @Override
    @Nullable
    public String getName() {
        HbPath mainPath = getBlockMainPath();
        return mainPath == null ? null : mainPath.getName();
    }
}
