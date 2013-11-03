package org.thymeleaf.extras.idea.dialect.xml.impl;

import org.thymeleaf.extras.idea.dialect.xml.Dialect;
import org.thymeleaf.extras.idea.dialect.xml.DialectItem;

public abstract class DialectItemImpl implements DialectItem {
    @Override
    public Dialect getDialect() {
        return getParentOfType(Dialect.class, true);
    }
}
