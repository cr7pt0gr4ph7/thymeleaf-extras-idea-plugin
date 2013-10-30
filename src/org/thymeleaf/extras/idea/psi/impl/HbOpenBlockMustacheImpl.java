package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.HbIcons;
import org.thymeleaf.extras.idea.psi.HbCloseBlockMustache;
import org.thymeleaf.extras.idea.psi.HbOpenBlockMustache;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class HbOpenBlockMustacheImpl extends HbBlockMustacheImpl implements HbOpenBlockMustache {
    public HbOpenBlockMustacheImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    public HbCloseBlockMustache getPairedElement() {
        PsiElement closeBlockElement = getParent().getLastChild();
        if (closeBlockElement instanceof HbCloseBlockMustache) {
            return  (HbCloseBlockMustache) closeBlockElement;
        }

        return null;
    }

    @Nullable
    @Override
    public Icon getIcon(int flags) {
        return HbIcons.OPEN_BLOCK;
    }
}
