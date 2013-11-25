package org.thymeleaf.extras.idea.dialect.dom.model.impl;

import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.dom.model.DialectItem;

public abstract class DialectItemImpl implements DialectItem {
    @Override
    public Dialect getDialect() {
        return getParentOfType(Dialect.class, true);
    }
}
