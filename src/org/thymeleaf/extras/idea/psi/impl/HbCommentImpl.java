package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.psi.HbComment;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class HbCommentImpl extends HbPsiElementImpl implements HbComment {
    public HbCommentImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }
}
