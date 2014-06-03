package org.thymeleaf.extras.idea.dialect.merged;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.dom.model.AttributeProcessor;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.dom.model.ElementProcessor;
import org.thymeleaf.extras.idea.dialect.dom.model.ExpressionObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DialectModelImpl implements DialectModel {
    private final List<Dialect> myDialects;

    public DialectModelImpl(@NotNull final List<Dialect> dialects) {
        myDialects = dialects;
    }

    @NotNull
    @Override
    public List<Dialect> getDialects() {
        return myDialects;
    }

    @NotNull
    @Override
    public List<AttributeProcessor> getAttributeProcessors() {
        final Set<String> names = new HashSet<String>();
        final List<AttributeProcessor> result = new ArrayList<AttributeProcessor>();

        for (Dialect dialect : getDialects()) {
            for (AttributeProcessor processor : dialect.getAttributeProcessors()) {
                // TODO There might be problems if getName().getValue() is null
                if (names.add(processor.getName().getValue())) {
                    result.add(processor);
                }
            }
        }
        return result;
    }

    @NotNull
    @Override
    public List<ElementProcessor> getElementProcessors() {
        final Set<String> names = new HashSet<String>();
        final List<ElementProcessor> result = new ArrayList<ElementProcessor>();

        for (Dialect dialect : getDialects()) {
            for (ElementProcessor processor : dialect.getElementProcessors()) {
                // TODO There might be problems if getName().getValue() is null
                if (names.add(processor.getName().getValue())) {
                    result.add(processor);
                }
            }
        }
        return result;
    }

    @NotNull
    @Override
    public List<ExpressionObject> getExpressionObjects() {
        final Set<String> names = new HashSet<String>();
        final List<ExpressionObject> result = new ArrayList<ExpressionObject>();

        for (Dialect dialect : getDialects()) {
            for (ExpressionObject processor : dialect.getExpressionObjects()) {
                // TODO There might be problems if getName().getValue() is null
                if (names.add(processor.getName().getValue())) {
                    result.add(processor);
                }
            }
        }
        return result;
    }

    @Nullable
    @Override
    public AttributeProcessor findAttributeProcessor(@NotNull String unqualifiedName) {
        for (Dialect dialect : getDialects()) {
            final AttributeProcessor result = dialect.findAttributeProcessor(unqualifiedName);
            if (result != null) return result;
        }
        return null;
    }

    @Nullable
    @Override
    public ElementProcessor findElementProcessor(@NotNull String unqualifiedName) {
        for (Dialect dialect : getDialects()) {
            final ElementProcessor result = dialect.findElementProcessor(unqualifiedName);
            if (result != null) return result;
        }
        return null;
    }

    @Nullable
    @Override
    public ExpressionObject findExpressionObject(@NotNull String localName) {
        for (Dialect dialect : getDialects()) {
            final ExpressionObject result = dialect.findExpressionObject(localName);
            if (result != null) return result;
        }
        return null;
    }
}
