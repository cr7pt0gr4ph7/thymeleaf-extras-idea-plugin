package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.psi.HbStatements;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class HbStatementsImpl extends HbPsiElementImpl implements HbStatements {
    public HbStatementsImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }
}
