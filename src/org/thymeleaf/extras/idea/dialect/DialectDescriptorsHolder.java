package org.thymeleaf.extras.idea.dialect;

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
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.DomUtil;
import com.intellij.xml.index.IndexedRelevantResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.discovery.DialectDescriptorIndex;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

/**
 * Provide the mapping (dialect namespace URI -> dialect descriptor file) for use with code completion,
 * language injection etc.
 */
public class DialectDescriptorsHolder {
    private static final Logger LOG = Logger.getInstance(DialectDescriptorsHolder.class);
    private final Project myProject;

    public DialectDescriptorsHolder(@NotNull Project project) {
        myProject = project;
    }

    public static DialectDescriptorsHolder getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, DialectDescriptorsHolder.class);
    }

    @Nullable
    public String getDefaultPrefix(@NotNull String schemaUrl, @NotNull PsiElement context) {
        final Dialect dialect = getDialectForSchemaUrl(schemaUrl, context);
        if(dialect == null) return null;
        return dialect.getPrefix().getValue();
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
    public XmlFile getDialectSchemaFile(String schemaUrl, Module module, PsiFile context) {
        // TODO Directly get the XML file, without creating an intermediate dialect DOM tree
        final Dialect dialect = getDialectForSchemaUrl(schemaUrl, module, context);
        if (dialect == null || !dialect.isValid()) return null;
        return DomUtil.getFile(dialect);
    }
}
