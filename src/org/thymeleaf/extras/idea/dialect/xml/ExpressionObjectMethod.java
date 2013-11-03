// Generated on Fri Nov 01 23:47:37 CET 2013
// DTD/Schema  :    http://www.thymeleaf.org/extras/dialect

package org.thymeleaf.extras.idea.dialect.xml;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * http://www.thymeleaf.org/extras/dialect:ExpressionObjectMethod interface.
 * <pre>
 * <h3>Type http://www.thymeleaf.org/extras/dialect:ExpressionObjectMethod documentation</h3>
 * A method in an expression object.
 * </pre>
 */
public interface ExpressionObjectMethod extends DomElement, DialectItem {
    /**
     * Returns the value of the java-bean-property child.
     *
     * @return the value of the java-bean-property child.
     */
    @NotNull
    GenericAttributeValue<Boolean> isJavaBeanProperty();
}
