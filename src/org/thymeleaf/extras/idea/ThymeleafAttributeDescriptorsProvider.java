package org.thymeleaf.extras.idea;

import com.intellij.javaee.ExternalResourceManager;
import com.intellij.javaee.ExternalResourceManagerEx;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlAttributeDescriptorsProvider;
import com.intellij.xml.impl.schema.AnyXmlAttributeDescriptor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider;
import org.thymeleaf.extras.idea.dialect.xml2.AttributeProcessor;
import org.thymeleaf.extras.idea.dialect.xml2.Dialect;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ThymeleafAttributeDescriptorsProvider implements XmlAttributeDescriptorsProvider {
    private static final Logger LOG = Logger.getInstance(ThymeleafAttributeDescriptorsProvider.class);

    @Override
    public XmlAttributeDescriptor[] getAttributeDescriptors(XmlTag context) {
        final String nsPrefix = context.getPrefixByNamespace("http://www.thymeleaf.org");

        if (nsPrefix == null) {
            // Thymeleaf XML namespace does not exist
            return XmlAttributeDescriptor.EMPTY;
        } else {
            final String prefix = nsPrefix + ":";
            final List<XmlAttributeDescriptor> result = new ArrayList<XmlAttributeDescriptor>();

            // Load the standard dialect
            final Dialect dialect = getDialectForSchemaUrl(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL, context.getProject());

            for (AttributeProcessor attr : dialect.getAttributeProcessors())
                result.add(new ThymeleafXmlAttributeDescriptor(prefix + attr.getName(), attr));

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

            // Load the standard dialect
            final Dialect dialect = getDialectForSchemaUrl(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL, context.getProject());

            for (AttributeProcessor attr : dialect.getAttributeProcessors())
                if ((prefix + attr.getName()).equals(attributeName))
                    return new ThymeleafXmlAttributeDescriptor(nsPrefix + attr.getName(), attr);

            return null;
        }
    }

    private Dialect getDialectForSchemaUrl(@NotNull String schemaUrl, @NotNull Project project) {
        // String location = ExternalResourceManager.getInstance().getResourceLocation(schemaUrl, project);
        String location = ((ExternalResourceManagerEx) ExternalResourceManager.getInstance()).getStdResource(schemaUrl, null);

        // No mapping found
        if (location == null) {
            LOG.warn(MessageFormat.format("Could not read dialect help file for dialect {0}: No mapping found for dialect schema url (in project {1}).", schemaUrl, project.getName()));
            return null;
        }

        VirtualFile schema;
        try {
            schema = VfsUtil.findFileByURL(new URL(location));
        } catch (MalformedURLException ignore) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0} for dialect {1}.", location), ignore);
            return null;
        }
        if (schema == null) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0} for dialect {1}: Failed to load file.", location));
            return null;
        }

        PsiFile psiFile = PsiManager.getInstance(project).findFile(schema);

        if (!(psiFile instanceof XmlFile)) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0} for dialect {1}: File is not an xml file.", location));
            return null;
        }

        DomManager manager = DomManager.getDomManager(project);
        DomFileElement<Dialect> domFileElement = manager.getFileElement((XmlFile) psiFile, Dialect.class);

        if (domFileElement == null) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0} for dialect {1}: Could not read xml file into DOM.", location));
            return null;
        }

        Dialect dialect = domFileElement.getRootElement();
        return dialect;
    }

    private static class ThymeleafXmlAttributeDescriptor extends AnyXmlAttributeDescriptor {
        private final AttributeProcessor myDeclaration;

        public ThymeleafXmlAttributeDescriptor(String name, AttributeProcessor declaration) {
            super(name);

            myDeclaration = declaration;
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
