package org.thymeleaf.extras.idea.dialect.xml2.impl;

import com.intellij.util.xml.DomElement;
import org.thymeleaf.extras.idea.dialect.xml2.Dialect;
import org.thymeleaf.extras.idea.dialect.xml2.DialectItem;

public abstract class DialectItemImpl implements DialectItem {
    @Override
    public Dialect getDialect() {
        return getParentOfType(Dialect.class, true);
    }
}
