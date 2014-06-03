package org.thymeleaf.extras.idea.dialect;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.MultiMap;
import com.intellij.util.containers.OrderedSet;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.DomUtil;
import com.intellij.xml.index.IndexedRelevantResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.discovery.DialectDescriptorIndex;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.merged.DialectModel;
import org.thymeleaf.extras.idea.dialect.merged.DialectModelImpl;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider.SPRING_STANDARD_DIALECT_URL;
import static org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL;

/**
 * Provide the mapping (dialect namespace URI -> dialect descriptor file) for use with code completion,
 * language injection etc.
 */
public class DialectDescriptorsHolder {
    private static final Logger LOG = Logger.getInstance(DialectDescriptorsHolder.class);
    private static final MergedDialectMap mergedDialects = new MergedDialectMap();
    private final Project myProject;

    static {
        mergedDialects.putValues(STANDARD_DIALECT_URL, Arrays.asList(
                SPRING_STANDARD_DIALECT_URL,
                STANDARD_DIALECT_URL
        ));
    }

    public DialectDescriptorsHolder(@NotNull Project project) {
        myProject = project;
    }

    public static DialectDescriptorsHolder getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, DialectDescriptorsHolder.class);
    }

    @Nullable
    public DialectModel getDialectForSchemaUrl(@NotNull String schemaUrl, @NotNull PsiElement context) {
        final Module module = ModuleUtilCore.findModuleForPsiElement(context);
        final PsiFile containingFile = context.getContainingFile();

        return getDialectForSchemaUrl(schemaUrl, module, containingFile);
    }

    @Nullable
    public DialectModel getDialectForSchemaUrl(@NotNull final String schemaUrl, @Nullable final Module module, @Nullable final PsiFile context) {
        // TODO Caching of DialectModel!
        if (mergedDialects.containsKey(schemaUrl)) {
            return new DialectModelImpl(ContainerUtil.mapNotNull(mergedDialects.get(schemaUrl), new Function<String, Dialect>() {
                @Override
                public Dialect fun(String childSchemaUrl) {
                    return getDialectForSchemaUrlCore(childSchemaUrl, module, context);
                }
            }));
        } else {
            final Dialect dialect = getDialectForSchemaUrlCore(schemaUrl, module, context);
            return (dialect != null) ? new DialectModelImpl(Collections.singletonList(dialect)) : null;
        }
    }

    @Nullable
    private Dialect getDialectForSchemaUrlCore(@NotNull String schemaUrl, @Nullable Module module, @Nullable PsiFile context) {
        if (module != null && context != null) {
            final List<IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo>> candidates =
                    DialectDescriptorIndex.getResourcesByNamespace(schemaUrl, module, context);

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
    private Dialect getStdDialectForSchemaUrl(@NotNull String schemaUrl) {
        final VirtualFile schemaFile = ThymeleafDefaultDialectsProvider.getStandardSchemaFile(schemaUrl);
        if (schemaFile == null) {
            // No mapping found. This is the point where we filter out namespaces that do not refer to dialects.
            return null;
        }
        return findDialectByVirtualFile(schemaFile);
    }

    /**
     * Get the dialect DOM model from the virtual file of the dialect schema.
     */
    @Nullable
    private Dialect findDialectByVirtualFile(@NotNull VirtualFile schemaFile) {
        final PsiFile psiFile = PsiManager.getInstance(myProject).findFile(schemaFile);

        if (!(psiFile instanceof XmlFile)) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0}: File is not an xml file.", schemaFile.getPath()));
            return null;
        }

        final DomManager manager = DomManager.getDomManager(myProject);
        final DomFileElement<Dialect> domFileElement = manager.getFileElement((XmlFile) psiFile, Dialect.class);

        if (domFileElement == null) {
            LOG.warn(MessageFormat.format("Could not read dialect help file at {0}: Could not read xml file into DOM.", schemaFile.getPath()));
            return null;
        }

        final Dialect dialect = domFileElement.getRootElement();
        return dialect;
    }

    @Nullable
    public XmlFile getDialectSchemaFile(String schemaUrl, Module module, PsiFile context) {
        // TODO Directly get the XML file, without creating an intermediate dialect DOM tree
        // TODO This is a hack (get(0))
        final Dialect dialect = getDialectForSchemaUrl(schemaUrl, module, context).getDialects().get(0);
        if (dialect == null || !dialect.isValid()) return null;
        return DomUtil.getFile(dialect);
    }

    private static class MergedDialectMap extends MultiMap<String, String> {
        // TODO Override MergedDialectMap.createEmptyCollection()?
        @Override
        protected Collection<String> createCollection() {
            return new OrderedSet<String>();
        }
    }
}
