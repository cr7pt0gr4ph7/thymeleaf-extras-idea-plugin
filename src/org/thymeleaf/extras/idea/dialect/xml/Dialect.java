// Generated on Fri Nov 01 23:47:37 CET 2013
// DTD/Schema  :    http://www.thymeleaf.org/extras/dialect

package org.thymeleaf.extras.idea.dialect.xml;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * http://www.thymeleaf.org/extras/dialect:dialectElemType interface.
 * <pre>
 * <h3>Type http://www.thymeleaf.org/extras/dialect:dialectElemType documentation</h3>
 * Information about a dialect, its processors, and its expression
 * 					objects.
 * </pre>
 */
public interface Dialect extends DomElement {
    /**
     * Returns the value of the prefix property.
     *
     * @return the value of the prefix property.
     */
    @NotNull
    @Required
    GenericAttributeValue<String> getPrefix();

    /**
     * Returns the value of the namespace-uri property.
     *
     * @return the value of the namespace-uri property.
     */
    @NotNull
    @Required
    GenericAttributeValue<String> getNamespaceUri();

    /**
     * Returns the value of the namespace-strict property.
     *
     * @return the value of the namespace-strict property.
     */
    @NotNull
    GenericAttributeValue<Boolean> isNamespaceStrict();

    /**
     * Returns the value of the class property.
     *
     * @return the value of the class property.
     */
    @NotNull
    @Attribute("class")
    @Required
    GenericAttributeValue<String> getClazz();

    // /**
    //  * Returns the merged list of dialect items.
    //  *
    //  * @return the merged list of dialect items.
    //  */
    // @SubTagsList({""})
    // List<DialectItem> getDialectItems();

    @NotNull
    List<AttributeProcessor> getAttributeProcessors();

    /**
     * Adds new children to the list of attribute-processor children.
     *
     * @return created child
     */
    AttributeProcessor addAttributeProcessor();

    /**
     * Returns the list of element-processor children.
     *
     * @return the list of element-processor children.
     */
    @NotNull
    List<ElementProcessor> getElementProcessors();

    /**
     * Adds new children to the list of element-processor children.
     *
     * @return created child
     */
    ElementProcessor addElementProcessor();

    /**
     * Returns the list of expression-object children.
     *
     * @return the list of expression-object children.
     */
    @NotNull
    List<ExpressionObject> getExpressionObjects();

    /**
     * Adds new children to the list of expression-object children.
     *
     * @return created child
     */
    ExpressionObject addExpressionObject();

    /**
     * Returns the list of expression-object-method children.
     *
     * @return the list of expression-object-method children.
     */
    @NotNull
    List<ExpressionObjectMethod> getExpressionObjectMethods();

    /**
     * Adds new children to the list of expression-object-method children.
     *
     * @return created child
     */
    ExpressionObjectMethod addExpressionObjectMethod();
}
