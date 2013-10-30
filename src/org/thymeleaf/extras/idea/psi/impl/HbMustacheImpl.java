package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.psi.HbMustache;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

class HbMustacheImpl extends HbPsiElementImpl implements HbMustache {
    HbMustacheImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }
}
