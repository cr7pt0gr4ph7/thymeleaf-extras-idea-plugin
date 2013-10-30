package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.psi.HbBlockWrapper;
import org.thymeleaf.extras.idea.psi.HbOpenBlockMustache;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class HbBlockWrapperImpl extends HbPsiElementImpl implements HbBlockWrapper {
    public HbBlockWrapperImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    public String getName() {
        HbOpenBlockMustache openBlockMustache = getHbOpenBlockMustache();
        return openBlockMustache == null ? null : openBlockMustache.getName();
    }

    @Nullable
    @Override
    public Icon getIcon(@IconFlags int flags) {
        HbOpenBlockMustache openBlockMustache = getHbOpenBlockMustache();
        return openBlockMustache == null ? null : openBlockMustache.getIcon(0);
    }

    private HbOpenBlockMustache getHbOpenBlockMustache() {
        return PsiTreeUtil.findChildOfType(this, HbOpenBlockMustache.class);
    }
}
