package org.thymeleaf.extras.idea;

import com.intellij.javaee.ExternalResourceManager;
import com.intellij.javaee.ExternalResourceManagerEx;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

            List<XmlAttributeDescriptor> result = new ArrayList<XmlAttributeDescriptor>();

            result.add(new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "errorclass"));
            result.add(new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "if"));
            result.add(new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "unless"));
            result.add(new XmlAttributeDescriptorWithEmptyDefaultValue(prefix + "each"));
            result.add(new ThymeleafInlineXmlAttributeDescriptor(prefix + "inline", findAttributeProcessor(getDialectForSchemaUrl("http://www.thymeleaf.org", context.getProject()), "inline").getXmlElement()));

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
                return new ThymeleafInlineXmlAttributeDescriptor(prefix + "inline", findAttributeProcessor(getDialectForSchemaUrl("http://www.thymeleaf.org", context.getProject()), "inline").getXmlElement());

            return null;
        }
    }

    private AttributeProcessor findAttributeProcessor(@NotNull Dialect dialect, @NotNull String name) {
        List<AttributeProcessor> processors = dialect.getAttributeProcessors();

        for (AttributeProcessor processor : processors) {
            if (processor.getName().getValue().equals(name))
                return processor;
        }

        return null;
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
        private final XmlElement myDeclaration;

        public ThymeleafInlineXmlAttributeDescriptor(String name, XmlElement declaration) {
            super(name);

            myDeclaration = declaration;
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

        @Override
        public XmlElement getDeclaration() {
            return myDeclaration;
        }
    }
}
