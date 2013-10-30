package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.HbIcons;
import org.thymeleaf.extras.idea.parsing.HbTokenTypes;
import org.thymeleaf.extras.idea.psi.HbSimpleInverse;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class HbSimpleInverseImpl extends HbMustacheImpl implements HbSimpleInverse {
    public HbSimpleInverseImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    public String getName() {
        ASTNode elseNode = getElseNode();
        if (elseNode != null) {
            return elseNode.getText();
        }
        return ""; // no name for "{{^}}" expressions
    }

    @Nullable
    @Override
    public Icon getIcon(@IconFlags int flags) {
        if (getElseNode() != null) {
            return HbIcons.OPEN_MUSTACHE;
        }
        return HbIcons.OPEN_INVERSE;
    }

    /**
     * If this element was created from an "{{else}}" expression, it will have an {@link org.thymeleaf.extras.idea.parsing.HbTokenTypes#ELSE} child.
     * Otherwise, it was created from "{{^}}"
     *
     * @return the {@link org.thymeleaf.extras.idea.parsing.HbTokenTypes#ELSE} element if it exists, null otherwise
     */
    private ASTNode getElseNode() {
        ASTNode[] elseChildren = getNode().getChildren(TokenSet.create(HbTokenTypes.ELSE));
        if (elseChildren != null && elseChildren.length > 0) {
            return elseChildren[0];
        }
        return null;
    }
}
