package org.thymeleaf.extras.idea.editor.xml;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.xml.XmlSchemaProvider;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.DialectDescriptorsHolder;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.merged.DialectModel;
import org.thymeleaf.extras.idea.editor.ThymeleafUtil;

import java.util.Set;

public class DialectXmlSchemaProvider extends XmlSchemaProvider {
    @Nullable
    @Override
    public XmlFile getSchema(@NotNull @NonNls String namespace, @Nullable Module module, @NotNull PsiFile file) {
        return DialectDescriptorsHolder.getInstance(file.getProject()).getDialectSchemaFile(namespace, module, file);
    }

    @Override
    public boolean isAvailable(@NotNull XmlFile file) {
        return ThymeleafUtil.canBeThymeleafFile(file.getFileType());
    }

    @NotNull
    @Override
    public Set<String> getAvailableNamespaces(@NotNull XmlFile file, @Nullable String tagName) {
        // TODO Check if file belongs to a ThymeleafFileSet (to be defined), and if yes, add the applicable dialect namespaces
        return super.getAvailableNamespaces(file, tagName);
    }

    @Nullable
    @Override
    public String getDefaultPrefix(@NotNull @NonNls String namespace, @NotNull XmlFile context) {
        final VirtualFile virtualFile = context.getVirtualFile();
        if (virtualFile == null) {
            return null;
        }
        final Project project = context.getProject();
        final Module module = ModuleUtilCore.findModuleForFile(virtualFile, project);
        final DialectModel dialect = DialectDescriptorsHolder.getInstance(project).getDialectForSchemaUrl(namespace, module, context);
        if (dialect == null) {
            return null;
        }
        // TODO HACK getDialects().get(0)
        return dialect.getDialects().get(0).getPrefix().getValue();
    }

    @Nullable
    @Override
    public Set<String> getLocations(@NotNull @NonNls String namespace, @NotNull XmlFile context) {
        return super.getLocations(namespace, context);
    }
}
