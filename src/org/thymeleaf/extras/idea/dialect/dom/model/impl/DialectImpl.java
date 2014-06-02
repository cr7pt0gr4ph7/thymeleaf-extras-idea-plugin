package org.thymeleaf.extras.idea.dialect.dom.model.impl;

import com.intellij.util.xml.DomUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.dom.model.AttributeProcessor;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.dom.model.ElementProcessor;
import org.thymeleaf.extras.idea.dialect.dom.model.ExpressionObject;

@SuppressWarnings("AbstractClassNeverImplemented" /* This is a DOM mixin class */)
public abstract class DialectImpl implements Dialect {
    @Nullable
    @Override
    public AttributeProcessor findAttributeProcessor(@NotNull final String name) {
        return DomUtil.findByName(getAttributeProcessors(), name);
    }

    @Nullable
    @Override
    public ElementProcessor findElementProcessor(@NotNull final String name) {
        return DomUtil.findByName(getElementProcessors(), name);
    }

    @Nullable
    @Override
    public ExpressionObject findExpressionObject(@NotNull final String name) {
        return DomUtil.findByName(getExpressionObjects(), name);
    }
}
