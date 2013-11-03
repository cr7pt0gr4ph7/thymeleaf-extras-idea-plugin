package org.thymeleaf.extras.idea.dialect;

import com.intellij.javaee.ExternalResourceManager;
import com.intellij.javaee.ExternalResourceManagerEx;
import com.intellij.javaee.ResourceRegistrar;
import com.intellij.javaee.StandardResourceProvider;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

public class ThymeleafDialectSchemaProvider implements StandardResourceProvider {
    public static final String DIALECT_HELP_SCHEMA_URL = "http://www.thymeleaf.org/extras/dialect";

    @Override
    public void registerResources(ResourceRegistrar registrar) {
        registrar.addStdResource(ThymeleafDialectSchemaProvider.DIALECT_HELP_SCHEMA_URL, "/schemas/thymeleaf-dialect-help.xsd", getClass());
    }

    @NotNull
    public static VirtualFile getSchemaFile(@NotNull String url) {
        String location = ((ExternalResourceManagerEx) ExternalResourceManager.getInstance()).getStdResource(url, null);
        assert location != null : "cannot find a standard resource for " + url;

        VirtualFile result = VfsUtil.findRelativeFile(location, null);
        assert result != null : "cannot find a schema file for URL: " + url + " location: " + location;

        return result;
    }
}