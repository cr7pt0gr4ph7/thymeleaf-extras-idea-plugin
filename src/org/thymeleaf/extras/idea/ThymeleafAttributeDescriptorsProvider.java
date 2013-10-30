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
        List<XmlAttributeDescriptor> result = new ArrayList<XmlAttributeDescriptor>();

        result.add(new XmlAttributeDescriptorWithEmptyDefaultValue("some-attribute"));

        return result.toArray(new XmlAttributeDescriptor[result.size()]);
    }

    @Nullable
    @Override
    public XmlAttributeDescriptor getAttributeDescriptor(String attributeName, XmlTag context) {
        System.out.println(attributeName);

        if(attributeName == "some-attribute")
            return new XmlAttributeDescriptorWithEmptyDefaultValue("some-attribute");

        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
