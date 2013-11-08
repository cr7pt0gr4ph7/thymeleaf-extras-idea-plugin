// Generated on Fri Nov 01 23:47:37 CET 2013
// DTD/Schema  :    http://www.thymeleaf.org/extras/dialect

package org.thymeleaf.extras.idea.dialect.xml;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * http://www.thymeleaf.org/extras/dialect:DialectItem interface.
 * <pre>
 * <h3>Type http://www.thymeleaf.org/extras/dialect:DialectItem documentation</h3>
 * Common code for Thymeleaf processor and expression objects.
 * </pre>
 */
public interface DialectItem extends DomElement {
    /**
     * Returns the value of the class child.
     *
     * @return the value of the class child.
     */
    @NotNull
    @Attribute("class")
    GenericAttributeValue<PsiClass> getClazz();

    /**
     * Returns the value of the documentation child.
     *
     * @return the value of the documentation child.
     */
    @NotNull
    Documentation getDocumentation();

    @NotNull
    @Required
    @NameValue
    GenericAttributeValue<String> getName();

    /**
     * Gets the dialect this object belongs to.
     *
     * @return {@link Dialect} this object is for.
     */
    Dialect getDialect();
}
