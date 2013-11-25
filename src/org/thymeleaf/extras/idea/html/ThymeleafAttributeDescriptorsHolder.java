package org.thymeleaf.extras.idea.html;

import com.intellij.javaee.ExternalResourceManager;
import com.intellij.javaee.ExternalResourceManagerEx;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
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
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlNSDescriptor;
import com.intellij.xml.impl.schema.AnyXmlAttributeDescriptor;
import com.intellij.xml.index.IndexedRelevantResource;
import com.intellij.xml.util.XmlUtil;
import gnu.trove.THashMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.discovery.DialectDescriptorIndex;
import org.thymeleaf.extras.idea.dialect.xml.AttributeProcessor;
import org.thymeleaf.extras.idea.dialect.xml.Dialect;
import org.thymeleaf.extras.idea.dialect.xml.DialectItem;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        final List<XmlAttributeDescriptor> result = new ArrayList<XmlAttributeDescriptor>();

        for (final String dialectUrl : visibleNamespaces) {
            final Dialect dialect = getDialectForSchemaUrl(dialectUrl, context);

            if (dialect == null) {
                // Not a dialect namespace
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

        final Dialect dialect = getDialectForSchemaUrl(schemaUrl, context);
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
    public Dialect getDialectForSchemaUrl(@NotNull String schemaUrl, @NotNull PsiElement context) {
        final Module module = ModuleUtilCore.findModuleForPsiElement(context);
        final PsiFile containingFile = context.getContainingFile();

        return getDialectForSchemaUrl(schemaUrl, module, containingFile);
    }

    @Nullable
    public Dialect getDialectForSchemaUrl(@NotNull String schemaUrl, @Nullable Module module, @Nullable PsiFile context) {
        if (module != null && context != null) {
            final List<IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo>> candidates =
                    DialectDescriptorIndex.getDialectDescriptorFiles(schemaUrl, module, context);

            for (final IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo> candidate : candidates) {
                final VirtualFile file = candidate.getFile();
                if (file == null || !file.isValid()) continue;
                final Dialect dialect = findDialectByVirtualFile(file);
                if (dialect == null || !dialect.isValid()) continue;
                return dialect;
            }
        }
        return getStdDialectForSchemaUrl(schemaUrl);
    }

    @Nullable
    public Dialect getStdDialectForSchemaUrl(@NotNull String schemaUrl) {
        // String location = ExternalResourceManager.getInstance().getResourceLocation(schemaUrl, project);
        final String location = ((ExternalResourceManagerEx) ExternalResourceManager.getInstance()).getStdResource(schemaUrl, null);

        if (location == null) {
            // No mapping found. This is the point where we filter out namespaces that do not refer to dialects.
            return null;
        }

        final VirtualFile schema;
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

        return findDialectByVirtualFile(schema);
    }

    /**
     * Get the dialect DOM model from the virtual file of the dialect schema.
     */
    @Nullable
    public Dialect findDialectByVirtualFile(@NotNull VirtualFile schemaFile) {
        final PsiFile psiFile = PsiManager.getInstance(myProject).findFile(schemaFile);

        if (!(psiFile instanceof XmlFile)) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0} for dialect {1}: File is not an xml file.", schemaFile.getPath()));
            return null;
        }

        final DomManager manager = DomManager.getDomManager(myProject);
        final DomFileElement<Dialect> domFileElement = manager.getFileElement((XmlFile) psiFile, Dialect.class);

        if (domFileElement == null) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0} for dialect {1}: Could not read xml file into DOM.", schemaFile.getPath()));
            return null;
        }

        final Dialect dialect = domFileElement.getRootElement();
        return dialect;
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

        public ThymeleafXmlAttributeDescriptor(String name, AttributeProcessor declaration) {
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
