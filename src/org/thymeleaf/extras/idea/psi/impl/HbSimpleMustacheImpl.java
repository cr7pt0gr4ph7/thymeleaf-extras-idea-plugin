package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.HbIcons;
import org.thymeleaf.extras.idea.parsing.HbTokenTypes;
import org.thymeleaf.extras.idea.psi.HbData;
import org.thymeleaf.extras.idea.psi.HbPath;
import org.thymeleaf.extras.idea.psi.HbPsiElement;
import org.thymeleaf.extras.idea.psi.HbSimpleMustache;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class HbSimpleMustacheImpl extends HbMustacheImpl implements HbSimpleMustache {
    public HbSimpleMustacheImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    public String getName() {
        for (PsiElement childElement : getChildren()) {
            // use the name of the first path or data to represent this mustache
            if (childElement instanceof HbPath
                    || childElement instanceof HbData) {
                return ((HbPsiElement) childElement).getName();
            }
        }

        return null;
    }

    @Nullable
    @Override
    public Icon getIcon(@IconFlags int flags) {
        PsiElement openStacheElem = getFirstChild();
        if (openStacheElem == null) {
            return null;
        }

        if (openStacheElem.getNode().getElementType() == HbTokenTypes.OPEN_UNESCAPED) {
            return HbIcons.OPEN_UNESCAPED;
        }

        return HbIcons.OPEN_MUSTACHE;
    }
}
