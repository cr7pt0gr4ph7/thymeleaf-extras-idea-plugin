package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.HbIcons;
import org.thymeleaf.extras.idea.psi.HbPartial;
import org.thymeleaf.extras.idea.psi.HbPartialName;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class HbPartialImpl extends HbMustacheImpl implements HbPartial {
    public HbPartialImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    public String getName() {
        HbPartialName partialName = PsiTreeUtil.findChildOfType(this, HbPartialName.class);
        return partialName == null ? null : partialName.getName();
    }

    @Nullable
    @Override
    public Icon getIcon(@IconFlags int flags) {
        return HbIcons.OPEN_PARTIAL;
    }
}
