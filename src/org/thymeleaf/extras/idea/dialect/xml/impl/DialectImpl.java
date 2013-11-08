package org.thymeleaf.extras.idea.dialect.xml.impl;

import com.intellij.util.xml.DomUtil;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.dialect.xml.AttributeProcessor;
import org.thymeleaf.extras.idea.dialect.xml.Dialect;

public abstract class DialectImpl implements Dialect {
    @Override
    public AttributeProcessor findAttributeProcessor(@NotNull String name) {
        return DomUtil.findByName(getAttributeProcessors(), name);
    }
}
