// Generated on Fri Nov 01 23:47:37 CET 2013
// DTD/Schema  :    http://www.thymeleaf.org/extras/dialect

package org.thymeleaf.extras.idea.dialect.xml2;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.dialect.xml2.converters.SpaceSeparatedListConverter;

import java.util.List;

/**
 * http://www.thymeleaf.org/extras/dialect:Documentation interface.
 * <pre>
 * <h3>Type http://www.thymeleaf.org/extras/dialect:Documentation documentation</h3>
 * Notes to help generate some documentation about a processor.
 * </pre>
 */
public interface Documentation extends DomElement {

	/**
	 * Returns the value of the simple content.
	 * @return the value of the simple content.
	 */
	@NotNull
	@Required
    @TagValue
	String getValue();
	/**
	 * Sets the value of the simple content.
	 * @param value the new value to set
	 */
	void setValue(@NotNull String value);


	/**
	 * Returns the value of the see-also child.
	 * <pre>
	 * <h3>Attribute null:see-also documentation</h3>
	 * List of tags related to this one, suggesting where else
	 * 							the user can go to get more information or understanding.
	 * </pre>
	 * @return the value of the see-also child.
	 */
	@NotNull
    @Convert(SpaceSeparatedListConverter.class)
	GenericAttributeValue<List<String>> getSeeAlso();


	/**
	 * Returns the value of the reference child.
	 * <pre>
	 * <h3>Attribute null:reference documentation</h3>
	 * An 'official' document and the section/page within it
	 * 							to get more information.
	 * </pre>
	 * @return the value of the reference child.
	 */
	@NotNull
	GenericAttributeValue<String> getReference();


}
