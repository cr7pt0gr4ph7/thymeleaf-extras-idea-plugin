package org.thymeleaf.extras.idea.html;

import com.intellij.javaee.ExternalResourceManager;
import com.intellij.javaee.ExternalResourceManagerEx;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlNSDescriptor;
import com.intellij.xml.impl.schema.AnyXmlAttributeDescriptor;
import com.intellij.xml.util.XmlUtil;
import gnu.trove.THashMap;
import gnu.trove.THashSet;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider;
import org.thymeleaf.extras.idea.dialect.xml.AttributeProcessor;
import org.thymeleaf.extras.idea.dialect.xml.Dialect;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

public class ThymeleafAttributeDescriptorsHolder {
    private static final Logger LOG = Logger.getInstance(ThymeleafAttributeDescriptorsHolder.class);
    private final Project myProject;
    private final Map<String, CachedValue<XmlNSDescriptor>> myDescriptorsMap = new THashMap<String, CachedValue<XmlNSDescriptor>>();

    public ThymeleafAttributeDescriptorsHolder(@NotNull Project project) {
        myProject = project;
    }

    public static ThymeleafAttributeDescriptorsHolder getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, ThymeleafAttributeDescriptorsHolder.class);
    }

    public XmlAttributeDescriptor[] getAttributeDescriptors(XmlTag context) {
        // The set of all namespaces that have at least one xmlns declaration that is visible at the current element.
        // Note that multiple prefix declarations for one and the same namespace are not handled properly at the moment
        final String[] visibleNamespaces = context.knownNamespaces();

        // The set of known dialect namespaces
        final Set<String> knownDialects = new THashSet<String>();

        knownDialects.add(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL);
        knownDialects.add(ThymeleafDefaultDialectsProvider.SPRING_STANDARD_DIALECT_URL);
        knownDialects.add(ThymeleafDefaultDialectsProvider.SPRING_SECURITY_DIALECT_URL);
        knownDialects.add(ThymeleafDefaultDialectsProvider.TILES_DIALECT_URL);

        final List<XmlAttributeDescriptor> result = new ArrayList<XmlAttributeDescriptor>();

        for (final String dialectUrl : visibleNamespaces) {
            if (knownDialects.contains(dialectUrl)) {
                // A dialect has been registered for the current schema url
                final Dialect dialect = getDialectForSchemaUrl(dialectUrl);

                if (dialect == null) {
                    // TODO Maybe limit the appearance rate of this error message to avoid spamming the output window?
                    LOG.warn(MessageFormat.format("Failed to load dialect with schema url \"{0}\".", dialectUrl));
                    continue;
                }

                final String prefix = context.getPrefixByNamespace(dialectUrl);
                if (prefix == null) {
                    // No prefix available for this namespace. This should not happen in practice.
                    continue;
                }
                final String prefixWithColon = prefix + ':';

                for (AttributeProcessor attr : dialect.getAttributeProcessors())
                    result.add(new ThymeleafXmlAttributeDescriptor(prefixWithColon + attr.getName(), attr));
            }
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

        final Dialect dialect = getDialectForSchemaUrl(schemaUrl);
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
    public Dialect getDialectForSchemaUrl(@NotNull String schemaUrl) {
        // String location = ExternalResourceManager.getInstance().getResourceLocation(schemaUrl, project);
        final String location = ((ExternalResourceManagerEx) ExternalResourceManager.getInstance()).getStdResource(schemaUrl, null);
        // No mapping found
        if (location == null) {
            LOG.warn(MessageFormat.format("Could not read dialect help file for dialect {0}: No mapping found for dialect schema url (in project {1}).", schemaUrl, myProject.getName()));
            return null;
        }

        VirtualFile schema;
        try {
            schema = VfsUtil.findFileByURL(new URL(location));
        } catch (MalformedURLException ignore) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0} for dialect {1}: Malformed dialect help file location url.", location), ignore);
            return null;
        }
        if (schema == null) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0} for dialect {1}: Failed to load file.", location));
            return null;
        }

        PsiFile psiFile = PsiManager.getInstance(myProject).findFile(schema);

        if (!(psiFile instanceof XmlFile)) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0} for dialect {1}: File is not an xml file.", location));
            return null;
        }

        DomManager manager = DomManager.getDomManager(myProject);
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
