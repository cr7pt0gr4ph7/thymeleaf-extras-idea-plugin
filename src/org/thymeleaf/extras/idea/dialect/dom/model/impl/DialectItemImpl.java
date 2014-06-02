package org.thymeleaf.extras.idea.dialect.dom.model.impl;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiTypesUtil;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.dom.model.DialectItem;

public abstract class DialectItemImpl implements DialectItem {
    @Override
    public Dialect getDialect() {
        return getParentOfType(Dialect.class, true);
    }

    @Override
    public PsiType getImplementationType() {
        final PsiClass psiClass = getImplementationClass().getValue();
        if (psiClass == null) return null;
        return PsiTypesUtil.getClassType(psiClass);
    }
}
