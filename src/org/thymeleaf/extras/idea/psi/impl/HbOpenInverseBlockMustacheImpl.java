package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.HbIcons;
import org.thymeleaf.extras.idea.psi.HbOpenInverseBlockMustache;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class HbOpenInverseBlockMustacheImpl extends HbOpenBlockMustacheImpl implements HbOpenInverseBlockMustache {
    public HbOpenInverseBlockMustacheImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Nullable
    @Override
    public Icon getIcon(int flags) {
        return HbIcons.OPEN_INVERSE;
    }
}
