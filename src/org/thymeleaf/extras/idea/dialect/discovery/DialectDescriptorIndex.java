package org.thymeleaf.extras.idea.dialect.discovery;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.NullableFunction;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.ID;
import com.intellij.util.io.DataExternalizer;
import com.intellij.xml.index.IndexedRelevantResource;
import com.intellij.xml.index.XmlIndex;
import com.intellij.xml.util.XmlUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// NOTE Please update DialectDescriptorIndex.INDEX_VERSION before publishing a new version of this class.
//      Otherwise, we might try to load data that was saved in another version of this plugin.

public class DialectDescriptorIndex extends XmlIndex<DialectDescriptorIndex.DialectInfo> {
    public static final ID<String, DialectInfo> NAME = ID.create("thymeleafDialectUris");
    private static final int INDEX_VERSION = 10;

    // TODO Rename to getResourcesByNamespace
    public static List<IndexedRelevantResource<String, DialectInfo>> getDialectDescriptorFiles(String namespace, @NotNull Module module, @Nullable PsiFile context) {
        // TODO Should we use DumbService.isDumb() here?
        if (DumbService.isDumb(module.getProject()) || (context != null && XmlUtil.isStubBuilding(context))) {
            return Collections.emptyList();
        }

        final GlobalSearchScope scope = computeAdditionalScope(module, context);
        final List<IndexedRelevantResource<String, DialectInfo>> resources = IndexedRelevantResource.getResources(NAME, namespace, module, module.getProject(), scope);

        Collections.sort(resources);
        // TldProcessorFactory.getFactory(context).processResources(resources);
        return resources;
    }

    @Nullable
    private static GlobalSearchScope computeAdditionalScope(Module module, @Nullable PsiFile context) {
        return module.getModuleWithDependenciesAndLibrariesScope(/*includeTests: */ false);
        // return createFilterForProject(module.getProject());
    }

    private static GlobalSearchScope createFilterForProject(final Project project) {
        final GlobalSearchScope projectScope = GlobalSearchScope.allScope(project);
        return new GlobalSearchScope(project) {
            @Override
            public boolean contains(VirtualFile file) {
                final VirtualFile parent = file.getParent();
                return parent != null && projectScope.contains(file);
            }

            @Override
            public int compare(VirtualFile file1, VirtualFile file2) {
                return projectScope.compare(file1, file2);
            }

            @Override
            public boolean isSearchInModuleContent(@NotNull Module aModule) {
                return true;
            }

            @Override
            public boolean isSearchInLibraries() {
                return true;
            }
        };
    }

    // TODO Rename to getAllResources
    public static List<IndexedRelevantResource<String, DialectInfo>> getDialectDescriptorFiles(final Module module, final PsiFile context) {
        // TODO Should we use DumbService.isDumb() here?
        if (DumbService.isDumb(module.getProject()) || (context != null && XmlUtil.isStubBuilding(context))) {
            return Collections.emptyList();
        }

        return IndexedRelevantResource.getAllResources(NAME, module, module.getProject(), new NullableFunction<List<IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo>>, IndexedRelevantResource<String, DialectInfo>>() {
            @Override
            public IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo> fun
                    (final List<IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo>> resources) {
                // TODO TldProcessorFactory.getFactory(context).processResources(resources);
                return resources.isEmpty() ? null : Collections.max(resources);
            }
        });
    }

    @Override
    @NotNull
    public ID<String, DialectInfo> getName() {
        return NAME;
    }

    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return new FileBasedIndex.InputFilter() {
            @Override
            public boolean acceptInput(VirtualFile file) {
                return XmlFileType.INSTANCE == file.getFileType() && file.getName().endsWith(".xml");
            }
        };
    }

    @Override
    @NotNull
    public DataIndexer<String, DialectInfo, FileContent> getIndexer() {
        return new DataIndexer<String, DialectInfo, FileContent>() {
            @Override
            @NotNull
            public Map<String, DialectInfo> map(FileContent inputData) {
                final DialectUriXmlBuilder builder = DialectUriXmlBuilder.computeInfo(inputData.getContentAsText());

                if (!builder.isDialectDescriptor() || !builder.isUriFound()) {
                    return Collections.emptyMap();
                }

                return Collections.singletonMap(builder.getUri(), new DialectInfo(builder.getPrefix()));
            }
        };
    }

    @Override
    public DataExternalizer<DialectInfo> getValueExternalizer() {
        return new DataExternalizer<DialectInfo>() {
            @Override
            public void save(DataOutput out, DialectDescriptorIndex.DialectInfo value) throws IOException {
                out.writeUTF(value.prefix);
            }

            @Override
            public DialectDescriptorIndex.DialectInfo read(DataInput in) throws IOException {
                return new DialectDescriptorIndex.DialectInfo(in.readUTF());
            }
        };
    }

    @Override
    public int getVersion() {
        return INDEX_VERSION;
    }

    public static class DialectInfo implements Comparable<DialectInfo> {
        private final String prefix;

        private DialectInfo(String prefix) {
            this.prefix = (prefix == null ? "" : prefix);
        }

        public String getPrefix() {
            return prefix;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DialectInfo that = (DialectInfo) o;

            if (!prefix.equals(that.prefix)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            // TODO Better hashCode() implementation for DialectInfo
            return prefix.hashCode();
        }

        @Override
        public int compareTo(DialectInfo dialectInfo) {
            // TODO Provide a correct implementation of DialectInfo.compareTo (based on the version of the dialect descriptor files)!
            return 0;
        }
    }
}