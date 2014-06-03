package org.thymeleaf.extras.idea.dialect.merged;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.dom.model.AttributeProcessor;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.dom.model.ElementProcessor;
import org.thymeleaf.extras.idea.dialect.dom.model.ExpressionObject;

import java.util.List;

/**
 * A model for a dialect consisting of one or more merged {@link org.thymeleaf.extras.idea.dialect.dom.model.Dialect Dialects}.
 */
public interface DialectModel {
    @NotNull
    List<Dialect> getDialects();

    @NotNull
    List<AttributeProcessor> getAttributeProcessors();

    @NotNull
    List<ElementProcessor> getElementProcessors();

    @NotNull
    List<ExpressionObject> getExpressionObjects();

    @Nullable
    AttributeProcessor findAttributeProcessor(@NotNull final String unqualifiedName);

    @Nullable
    ElementProcessor findElementProcessor(@NotNull final String unqualifiedName);

    @Nullable
    ExpressionObject findExpressionObject(@NotNull final String localName);
}
