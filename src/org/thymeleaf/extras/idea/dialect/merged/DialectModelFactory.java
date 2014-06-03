package org.thymeleaf.extras.idea.dialect.merged;

import com.intellij.openapi.module.Module;
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
import com.intellij.util.xml.model.impl.DomModelFactory;
import com.intellij.xml.index.IndexedRelevantResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.DialectDescriptorsHolder;
import org.thymeleaf.extras.idea.dialect.discovery.DialectDescriptorIndex;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;

import java.util.*;

import static org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider.SPRING_STANDARD_DIALECT_URL;
import static org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL;

public class DialectModelFactory extends DomModelFactory<Dialect, DialectModel, PsiElement> {
    private static final MergedDialectMap mergedDialects = new MergedDialectMap();

    static {
        mergedDialects.putValues(STANDARD_DIALECT_URL, Arrays.asList(
                SPRING_STANDARD_DIALECT_URL,
                STANDARD_DIALECT_URL
        ));
    }

    public DialectModelFactory(final Project project) {
        super(Dialect.class, project, "Thymeleaf Dialect");
    }

    @Nullable
    @Override
    protected List<DialectModel> computeAllModels(@NotNull final Module scope) {
        // TODO Derive dialect models from user settings and/or common namespace prefixes
        // TODO How do we know which dialects have been activated?
        // Possibility A =>
        // Steps:
        //   (1) Find out which Template Engine is responsible for the current file (via Spring Model)
        //   (2) Check which dialects have been activated (via Spring Model)
        //   (3) Use a class-to-dialect index to get the correct dialect models
        // Possibility B =>
        //   Fixed user configuration. Problems: (1) Scope of the settings (and/or multiple scopes?)
        //                                       (2) Must be configured manually (or may be derived automatically?)
        //                                           Note: Automatical derivation basically equals to A.3, plus
        //                                           additional caching.
        // Possibility C =>
        //   Hardcoded configuration in code. This is the way it is currently implemented.

        // TODO Take the dialects that are really available into account (Currently, the available dialects are determined by the libraries)
        final List<DialectModel> result = new ArrayList<DialectModel>();

        // Process merged dialects
        for (final Map.Entry<String, Collection<String>> entry : mergedDialects.entrySet()) {
            final Set<XmlFile> mergedFiles = ContainerUtil.collectSet(ContainerUtil.mapNotNull(entry.getValue(), new Function<String, XmlFile>() {
                @Override
                public XmlFile fun(String schemaUrl) {
                    return findDialectXmlFile(scope, schemaUrl);
                }
            }).iterator());

            if (!mergedFiles.isEmpty()) {
                final DomFileElement<Dialect> element = createMergedModelRoot(mergedFiles);
                if (element != null) {
                    result.add(new DialectModelImpl(element, mergedFiles));
                }
            }
        }

        // Process dialects that have not already been overwritten by a merged dialect
        for (final IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo> resource : DialectDescriptorIndex.getAllResources(scope, null)) {
            if (!mergedDialects.containsKey(resource.getKey())) {
                final XmlFile xmlFile = findDialectXmlFile(scope, resource.getKey());

                if (xmlFile != null) {
                    final Set<XmlFile> files = Collections.singleton(xmlFile);
                    final DomFileElement<Dialect> element = createMergedModelRoot(files);

                    if (element != null) {
                        result.add(new DialectModelImpl(element, files));
                    }
                }
            }
        }

        return result;
    }

    @Nullable
    private XmlFile findDialectXmlFile(@NotNull final Module scope, @NotNull final String schemaUrl) {
        return findDialectXmlFileFromCandidates(DialectDescriptorIndex.getAllResources(scope, null));
    }

    @Nullable
    private XmlFile findDialectXmlFileFromCandidates(@NotNull final List<IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo>> candidates) {
        final DialectDescriptorsHolder holder = DialectDescriptorsHolder.getInstance(getProject());

        // TODO Remove the code duplication!
        for (final IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo> candidate : candidates) {
            final VirtualFile file = candidate.getFile();
            if (file == null || !file.isValid()) continue;

            final Dialect dialect = holder.findDialectByVirtualFile(file);
            if (dialect == null || !dialect.isValid()) continue;

            final PsiFile psiFile = PsiManager.getInstance(getProject()).findFile(file);
            if (!(psiFile instanceof XmlFile)) continue;

            return (XmlFile) psiFile;
        }
        return null;
    }

    @Nullable
    private Dialect findFirstApplicableDialectFromCandidates(@NotNull final List<IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo>> candidates) {
        final DialectDescriptorsHolder holder = DialectDescriptorsHolder.getInstance(getProject());
        for (final IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo> candidate : candidates) {
            final VirtualFile file = candidate.getFile();
            if (file == null || !file.isValid()) continue;
            final Dialect dialect = holder.findDialectByVirtualFile(file);
            if (dialect == null || !dialect.isValid()) continue;
            return dialect;
        }
        return null;
    }

    @Override
    protected DialectModel createCombinedModel(@NotNull final Set<XmlFile> configFiles,
                                               @NotNull final DomFileElement<Dialect> mergedModel,
                                               final DialectModel firstModel, final Module scope) {
        return new DialectModelImpl(mergedModel, configFiles);
    }

    private static class MergedDialectMap extends MultiMap<String, String> {
        // TODO Override MergedDialectMap.createEmptyCollection()?
        @Override
        protected Collection<String> createCollection() {
            return new OrderedSet<String>();
        }
    }
}
