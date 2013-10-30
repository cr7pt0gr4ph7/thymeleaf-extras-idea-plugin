package org.thymeleaf.extras.idea;

import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlAttributeDescriptorsProvider;
import com.intellij.xml.impl.schema.AnyXmlAttributeDescriptor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ThymeleafAttributeDescriptorsProvider implements XmlAttributeDescriptorsProvider {

    @Override
    public XmlAttributeDescriptor[] getAttributeDescriptors(XmlTag context) {
        final String nsPrefix = context.getPrefixByNamespace("http://www.thymeleaf.org");

        if (nsPrefix == null) {
            // Thymeleaf XML namespace does not exist
            return XmlAttributeDescriptor.EMPTY;
        } else {
            final String prefix = nsPrefix + ":";

            List<XmlAttributeDescriptor> result = new ArrayList<XmlAttributeDescriptor>();

            result.add(new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "errorclass"));
            result.add(new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "if"));
            result.add(new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "unless"));
            result.add(new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "each"));

            return result.toArray(new XmlAttributeDescriptor[result.size()]);
        }
    }

    @Nullable
    @Override
    public XmlAttributeDescriptor getAttributeDescriptor(String attributeName, XmlTag context) {
        System.out.println(attributeName);

        final String nsPrefix = context.getPrefixByNamespace("http://www.thymeleaf.org");

        if (nsPrefix == null) {
            // Thymeleaf XML namespace does not exist
            return null;
        } else {
            final String prefix = nsPrefix + ":";

            if ((nsPrefix + "errorclass").equals(attributeName))
                return new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "errorclass");
            else if ((nsPrefix + "if").equals(attributeName))
                return new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "if");
            else if ((nsPrefix + "unless").equals(attributeName))
                return new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "unless");
            else if ((nsPrefix + "each").equals(attributeName))
                return new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "each");

            return null;
        }
    }

    private static class XmlAttributeDescriptorWithEmptyDefaultValue extends AnyXmlAttributeDescriptor {
        public XmlAttributeDescriptorWithEmptyDefaultValue(String name) {
            super(name);
        }

        @Override
        public String getDefaultValue() {
            return "";
        }
    }
}
