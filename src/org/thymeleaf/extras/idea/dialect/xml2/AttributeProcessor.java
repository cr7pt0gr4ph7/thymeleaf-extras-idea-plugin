package org.thymeleaf.extras.idea.dialect.xml2;

import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * http://www.thymeleaf.org/extras/dialect:AttributeProcessor interface.
 * <pre>
 * <h3>Type http://www.thymeleaf.org/extras/dialect:AttributeProcessor documentation</h3>
 * An attribute processor, includes an extra set of restrictions to
 * help with deciding where the processor can go and what values it
 * can take.
 * </pre>
 */
public interface AttributeProcessor extends DomElement, Processor {
    /**
     * Gets the value of the restrictions property.
     *
     * @return possible object is
     *         {@link AttributeRestrictions }
     */
    @NotNull
    AttributeRestrictions getRestrictions();
}
