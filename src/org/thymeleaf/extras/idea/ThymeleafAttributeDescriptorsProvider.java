package org.thymeleaf.extras.idea;

import com.intellij.psi.xml.XmlElement;
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
            result.add(new ThymeleafInlineXmlAttributeDescriptor(prefix + "inline"));

            return result.toArray(new XmlAttributeDescriptor[result.size()]);
        }
    }

    @Nullable
    @Override
    public XmlAttributeDescriptor getAttributeDescriptor(String attributeName, XmlTag context) {
        // TODO Remove this debugging aid
        System.out.println(attributeName);

        final String nsPrefix = context.getPrefixByNamespace("http://www.thymeleaf.org");

        if (nsPrefix == null) {
            // Thymeleaf XML namespace does not exist
            return null;
        } else {
            final String prefix = nsPrefix + ":";

            if ((prefix + "errorclass").equals(attributeName))
                return new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "errorclass");
            else if ((prefix + "if").equals(attributeName))
                return new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "if");
            else if ((prefix + "unless").equals(attributeName))
                return new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "unless");
            else if ((prefix + "each").equals(attributeName))
                return new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "each");
            else if ((prefix + "inline").equals(attributeName))
                return new ThymeleafInlineXmlAttributeDescriptor(prefix + "inline");

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

    private static class ThymeleafInlineXmlAttributeDescriptor extends AnyXmlAttributeDescriptor {
        public ThymeleafInlineXmlAttributeDescriptor(String name) {
            super(name);
        }

        private static String nullSafeToString(String stringOrNull) {
            if (stringOrNull == null)
                return "(null)";
            else
                return "\'" + stringOrNull + "\'";
        }

        @Override
        public String getDefaultValue() {
            return "";
        }

        @Override
        public boolean isEnumerated() {
            return true;
        }

        @Override
        public String[] getEnumeratedValues() {
            return new String[]{"text", "javascript", "dart"};
        }

        @Override
        public String validateValue(XmlElement context, String attributeValue) {
            if (attributeValue != null && ("text".equals(attributeValue) || "javascript".equals(attributeValue) || "dart".equals(attributeValue)))
                return null;
            else
                return "Invalid value: " + nullSafeToString(attributeValue) + ". Value must be one of: \'text\', \'javascript\', \'dart\'.";
        }
    }
}
