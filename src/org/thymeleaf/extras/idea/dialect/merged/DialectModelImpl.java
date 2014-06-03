package org.thymeleaf.extras.idea.dialect.merged;

import com.intellij.psi.xml.XmlFile;
import com.intellij.util.NotNullFunction;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.model.impl.DomModelImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.dom.model.AttributeProcessor;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.dom.model.ElementProcessor;
import org.thymeleaf.extras.idea.dialect.dom.model.ExpressionObject;

import java.util.List;
import java.util.Set;

public class DialectModelImpl extends DomModelImpl<Dialect> implements DialectModel {
    private static final NotNullFunction<DomFileElement<Dialect>, Dialect> ROOT_ELEMENT_MAPPER =
            new NotNullFunction<DomFileElement<Dialect>, Dialect>() {
                @NotNull
                @Override
                public Dialect fun(final DomFileElement<Dialect> dom) {
                    return dom.getRootElement();
                }
            };

    public DialectModelImpl(DomFileElement<Dialect> mergedModel, Set<XmlFile> configFiles) {
        super(mergedModel, configFiles);
    }

    // TODO Determine merging order

    @NotNull
    @Override
    public List<AttributeProcessor> getAttributeProcessors() {
        return getMergedModel().getAttributeProcessors();
    }

    @NotNull
    @Override
    public List<ElementProcessor> getElementProcessors() {
        return getMergedModel().getElementProcessors();
    }

    @NotNull
    @Override
    public List<ExpressionObject> getExpressionObjects() {
        return getMergedModel().getExpressionObjects();
    }

    @Nullable
    @Override
    public AttributeProcessor findAttributeProcessor(@NotNull final String unqualifiedName) {
        return getMergedModel().findAttributeProcessor(unqualifiedName);
    }

    @Nullable
    @Override
    public ElementProcessor findElementProcessor(@NotNull final String unqualifiedName) {
        return getMergedModel().findElementProcessor(unqualifiedName);
    }

    @Nullable
    @Override
    public ExpressionObject findExpressionObject(@NotNull final String localName) {
        return getMergedModel().findExpressionObject(localName);
    }
}
