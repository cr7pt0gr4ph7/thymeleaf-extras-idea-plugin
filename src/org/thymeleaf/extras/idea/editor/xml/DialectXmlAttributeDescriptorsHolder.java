package org.thymeleaf.extras.idea.editor.xml;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomManager;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.impl.schema.AnyXmlAttributeDescriptor;
import com.intellij.xml.util.XmlUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.DialectDescriptorsHolder;
import org.thymeleaf.extras.idea.dialect.dom.model.AttributeProcessor;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.dom.model.DialectItem;
import org.thymeleaf.extras.idea.util.MyXmlUtil;

import java.util.ArrayList;
import java.util.List;

public class DialectXmlAttributeDescriptorsHolder {
    private static final Logger LOG = Logger.getInstance(DialectXmlAttributeDescriptorsHolder.class);
    private final Project myProject;

    public DialectXmlAttributeDescriptorsHolder(@NotNull Project project) {
        myProject = project;
    }

    public static DialectXmlAttributeDescriptorsHolder getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, DialectXmlAttributeDescriptorsHolder.class);
    }

    public XmlAttributeDescriptor[] getAttributeDescriptors(XmlTag context) {
        // The set of all namespaces that have at least one xmlns declaration that is visible at the current element.
        // Note that multiple prefix declarations for one and the same namespace are not handled properly at the moment
        final String[] visibleNamespaces = context.knownNamespaces();

        final List<XmlAttributeDescriptor> result = new ArrayList<XmlAttributeDescriptor>();
        final DialectDescriptorsHolder dialectHolder = DialectDescriptorsHolder.getInstance(myProject);

        for (final String dialectUrl : visibleNamespaces) {
            final Dialect dialect = dialectHolder.getDialectForSchemaUrl(dialectUrl, context);

            if (dialect == null) {
                // Not a dialect namespace
                continue;
            }

            @NonNls final String prefix = context.getPrefixByNamespace(dialectUrl);
            if (prefix == null) {
                // No prefix available for this namespace. This should not happen in practice.
                continue;
            }

            final String prefixWithColon = prefix + ':';

            for (AttributeProcessor attr : dialect.getAttributeProcessors())
                result.add(new ThymeleafXmlAttributeDescriptor(prefixWithColon + attr.getName(), attr));
        }

        return result.toArray(new XmlAttributeDescriptor[result.size()]);
    }

    @Nullable
    public XmlAttributeDescriptor getAttributeDescriptor(final String attributeName, final XmlTag context) {
        final String localName = MyXmlUtil.getLocalName(attributeName);
        if (localName.isEmpty()) {
            // Incomplete attribute name, i.e. "th:"
            return null;
        }

        final String schemaUrl = context.getNamespaceByPrefix(XmlUtil.findPrefixByQualifiedName(attributeName));
        if (schemaUrl.isEmpty()) {
            // Prefix is not associated with a namespace
            return null;
        }

        final Dialect dialect = DialectDescriptorsHolder.getInstance(myProject).getDialectForSchemaUrl(schemaUrl, context);
        if (dialect == null) {
            // No mapping found for dialect, or namespace url is not a dialect namespace
            return null;
        }

        final AttributeProcessor attr = dialect.findAttributeProcessor(localName);
        if (attr == null) {
            // Unknown attribute processor
            return null;
        }

        return new ThymeleafXmlAttributeDescriptor(attributeName, attr);
    }

    @Nullable
    public DialectItem findDialectItemFromDocumentationXmlTag(@NotNull XmlTag itemDeclaration) {
        final DomManager manager = DomManager.getDomManager(myProject);
        final DomElement domElement = manager.getDomElement(itemDeclaration);
        if (!(domElement instanceof DialectItem)) return null;
        return (DialectItem) domElement;
    }

    private static class ThymeleafXmlAttributeDescriptor extends AnyXmlAttributeDescriptor {
        private final AttributeProcessor myDeclaration;

        public ThymeleafXmlAttributeDescriptor(@NonNls String name, AttributeProcessor declaration) {
            super(name);

            myDeclaration = declaration;
        }

        public AttributeProcessor getDomDeclaration() {
            return myDeclaration;
        }

        @Override
        public PsiElement getDeclaration() {
            return myDeclaration.getXmlElement();
        }

        @Override
        public String getDefaultValue() {
            return "";
        }

        @Override
        public boolean isEnumerated() {
            return myDeclaration.getRestrictions().getValues().exists();
        }

        @Override
        public String[] getEnumeratedValues() {
            if (isEnumerated()) {
                List<String> allowedValues = myDeclaration.getRestrictions().getValues().getValue();
                assert allowedValues != null;
                return allowedValues.toArray(new String[allowedValues.size()]);
            } else {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            }
        }

        @Override
        public String validateValue(XmlElement context, String attributeValue) {
            if (!isEnumerated()) return null;

            List<String> allowedValues = myDeclaration.getRestrictions().getValues().getValue();
            assert allowedValues != null;
            if (!allowedValues.contains(attributeValue))
                return "Invalid value: " + nullSafeToString(attributeValue) + ". Value must be one of: {" + StringUtils.join(allowedValues, ", ") + "}";

            return null;
        }

        private static String nullSafeToString(String stringOrNull) {
            if (stringOrNull == null)
                return "(null)";
            else
                return "\'" + stringOrNull + "\'";
        }
    }
}
