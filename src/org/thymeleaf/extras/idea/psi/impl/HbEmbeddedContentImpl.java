package org.thymeleaf.extras.idea.psi.impl;

import org.thymeleaf.extras.idea.psi.HbEmbeddedContent;
import org.thymeleaf.extras.idea.psi.HbPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTagChild;
import org.jetbrains.annotations.NotNull;

public class HbEmbeddedContentImpl extends HbPsiElementImpl implements HbEmbeddedContent {
    public HbEmbeddedContentImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    public XmlTag getParentTag() {
        final PsiElement parent = getParent();
        if (parent instanceof XmlTag) return (XmlTag) parent;
        return null;
    }

    public XmlTagChild getNextSiblingInTag() {
        PsiElement nextSibling = getNextSibling();
        if (nextSibling instanceof XmlTagChild) return (XmlTagChild) nextSibling;
        return null;
    }

    public XmlTagChild getPrevSiblingInTag() {
        final PsiElement prevSibling = getPrevSibling();
        if (prevSibling instanceof XmlTagChild) return (XmlTagChild) prevSibling;
        return null;
    }

    public boolean processElements(PsiElementProcessor processor, PsiElement place) {
        // TODO
        return true;
    }
}
