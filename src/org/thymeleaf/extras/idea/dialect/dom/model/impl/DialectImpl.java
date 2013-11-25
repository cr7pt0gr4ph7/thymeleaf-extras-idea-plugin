package org.thymeleaf.extras.idea.dialect.dom.model.impl;

import com.intellij.util.xml.DomUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.dom.model.AttributeProcessor;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;

public abstract class DialectImpl implements Dialect {
    @Override
    @Nullable
    public AttributeProcessor findAttributeProcessor(@NotNull String name) {
        return DomUtil.findByName(getAttributeProcessors(), name);
    }
}
