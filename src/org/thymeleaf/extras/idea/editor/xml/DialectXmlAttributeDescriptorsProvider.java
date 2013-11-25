package org.thymeleaf.extras.idea.editor.xml;

import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlAttributeDescriptorsProvider;
import org.jetbrains.annotations.Nullable;

public class DialectXmlAttributeDescriptorsProvider implements XmlAttributeDescriptorsProvider {
    @Override
    public XmlAttributeDescriptor[] getAttributeDescriptors(XmlTag context) {
        return DialectXmlAttributeDescriptorsHolder.getInstance(context.getProject()).getAttributeDescriptors(context);
    }

    @Nullable
    @Override
    public XmlAttributeDescriptor getAttributeDescriptor(String attributeName, XmlTag context) {
        return DialectXmlAttributeDescriptorsHolder.getInstance(context.getProject()).getAttributeDescriptor(attributeName, context);
    }
}
