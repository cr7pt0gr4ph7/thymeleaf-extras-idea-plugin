// Generated on Fri Nov 01 23:47:37 CET 2013
// DTD/Schema  :    http://www.thymeleaf.org/extras/dialect

package org.thymeleaf.extras.idea.dialect.xml2;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.dialect.xml2.converters.SpaceSeparatedListConverter;

import java.util.List;

/**
 * http://www.thymeleaf.org/extras/dialect:AttributeRestrictions interface.
 * <pre>
 * <h3>Type http://www.thymeleaf.org/extras/dialect:AttributeRestrictions documentation</h3>
 * A set of restrictions on attribute processor use, used to help the
 * content assist decide where attribute suggestions should be made.
 * </pre>
 */
public interface AttributeRestrictions extends DomElement {

    /**
     * Returns the value of the tags child.
     * <pre>
     * <h3>Attribute null:tags documentation</h3>
     * A list of tags that this processor can or cannot appear in.  To
     * list a tag that it can't appear in, prefix that tag name with a
     * minus symbol, eg: -head
     * </pre>
     *
     * @return the value of the tags child.
     */
    @NotNull
    @Convert(SpaceSeparatedListConverter.class)
    GenericAttributeValue<List<String>> getTags();

    /**
     * Returns the value of the attributes child.
     * <pre>
     * <h3>Attribute null:attributes documentation</h3>
     * A list of attributes that must or must not be present in the same
     * tag as this processor.  To list an attribute that must not be
     * present, prefix that attribute name with a minus symbol, eg: -style
     * </pre>
     *
     * @return the value of the attributes child.
     */
    @NotNull
    @Convert(SpaceSeparatedListConverter.class)
    GenericAttributeValue<List<String>> getAttributes();

    /**
     * Returns the value of the values child.
     * <pre>
     * <h3>Attribute null:values documentation</h3>
     * A list of values that this processor can take.
     * </pre>
     *
     * @return the value of the values child.
     */
    @NotNull
    @Convert(SpaceSeparatedListConverter.class)
    GenericAttributeValue<List<String>> getValues();
}
