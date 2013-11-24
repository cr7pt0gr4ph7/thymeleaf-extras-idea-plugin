package org.thymeleaf.extras.idea.dialect.discovery;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.packaging.artifacts.Artifact;
import com.intellij.packaging.impl.artifacts.ArtifactBySourceFileFinder;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.NullableFunction;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.ID;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.text.CharArrayUtil;
import com.intellij.util.xml.NanoXmlUtil;
import com.intellij.xml.index.IndexedRelevantResource;
import com.intellij.xml.index.XmlIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// NOTE Please update DialectDescriptorIndex.INDEX_VERSION before publishing a new version of this class.
//      Otherwise, we might try to load data that was saved in another version of this plugin.

public class DialectDescriptorIndex extends XmlIndex<DialectDescriptorIndex.DialectInfo> {
    public static final ID<String, DialectInfo> NAME = ID.create("thymeleafDialectUris");
    private static final int INDEX_VERSION = 1;

    public static List<IndexedRelevantResource<String, DialectInfo>> getDialectDescriptorFiles(String namespace, @NotNull Module module, @Nullable PsiFile context) {
        // TODO Should we use DumbService.isDumb() here?
        if(DumbService.isDumb(module.getProject())) return Collections.emptyList();

        final GlobalSearchScope scope = computeAdditionalScope(module, context);
        final List<IndexedRelevantResource<String, DialectInfo>> resources = IndexedRelevantResource.getResources(NAME, namespace, module, module.getProject(), scope);

        Collections.sort(resources);
        // TldProcessorFactory.getFactory(context).processResources(resources);
        return resources;
    }

    @Nullable
    private static GlobalSearchScope computeAdditionalScope(Module module, @Nullable PsiFile context) {
        if (context == null) {
            return null;
        }
        final VirtualFile file = context.getVirtualFile();
        if (file == null) {
            return null;
        }
        final ArtifactBySourceFileFinder finder = ArtifactBySourceFileFinder.getInstance(module.getProject());
        final Collection<? extends Artifact> artifacts = finder.findArtifacts(file);
        if (artifacts.isEmpty()) {
            return null;
        }
        return new GlobalSearchScope() {
            public boolean contains(VirtualFile file) {
                Collection<? extends Artifact> a = finder.findArtifacts(file);
                return ContainerUtil.intersects(artifacts, a);
            }

            public int compare(VirtualFile file1, VirtualFile file2) {
                return 0;
            }

            public boolean isSearchInModuleContent(@NotNull Module aModule) {
                return true;
            }

            public boolean isSearchInLibraries() {
                return false;
            }
        };
    }

    public static List<IndexedRelevantResource<String, DialectInfo>> getDialectDescriptorFiles(final Module module, final PsiFile context) {
        // TODO Should we use DumbService.isDumb() here?
        if(DumbService.isDumb(module.getProject())) return Collections.emptyList();

        return IndexedRelevantResource.getAllResources(NAME, module, module.getProject(), new NullableFunction<List<IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo>>, IndexedRelevantResource<String, DialectInfo>>() {
            public IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo> fun
                    (final List<IndexedRelevantResource<String, DialectDescriptorIndex.DialectInfo>> resources) {
                // TODO TldProcessorFactory.getFactory(context).processResources(resources);
                return resources.isEmpty() ? null : Collections.max(resources);
            }
        });
    }

    @NotNull
    public ID<String, DialectInfo> getName() {
        return NAME;
    }

    public FileBasedIndex.InputFilter getInputFilter() {
        return new FileBasedIndex.InputFilter() {
            public boolean acceptInput(VirtualFile file) {
                if (XmlFileType.INSTANCE != file.getFileType()) return false;
                String name = file.getName();
                return (name.endsWith(".xml")) && (file.getFileSystem() == LocalFileSystem.getInstance());
            }
        };
    }

    @NotNull
    public DataIndexer<String, DialectInfo, FileContent> getIndexer() {
        return new DataIndexer<String, DialectInfo, FileContent>() {
            @NotNull
            public Map<String, DialectInfo> map(FileContent inputData) {
                final DialectUriXmlBuilder builder = new DialectUriXmlBuilder();

                NanoXmlUtil.parse(CharArrayUtil.readerFromCharSequence(inputData.getContentAsText()), builder);

                if (!builder.isDialectDescriptor() || !builder.isUriFound()) {
                    return Collections.emptyMap();
                }

                return Collections.singletonMap(builder.getUri(), new DialectInfo(builder.getPrefix()));
            }
        };
    }

    public DataExternalizer<DialectInfo> getValueExternalizer() {
        return new DataExternalizer<DialectInfo>() {
            public void save(DataOutput out, DialectDescriptorIndex.DialectInfo value) throws IOException {
                out.writeUTF(value.prefix);
            }

            public DialectDescriptorIndex.DialectInfo read(DataInput in) throws IOException {
                return new DialectDescriptorIndex.DialectInfo(in.readUTF());
            }
        };
    }

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
