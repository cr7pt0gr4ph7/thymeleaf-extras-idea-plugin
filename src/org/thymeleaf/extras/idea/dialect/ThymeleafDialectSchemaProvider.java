package org.thymeleaf.extras.idea.dialect;

import com.intellij.javaee.ExternalResourceManager;
import com.intellij.javaee.ExternalResourceManagerEx;
import com.intellij.javaee.ResourceRegistrar;
import com.intellij.javaee.StandardResourceProvider;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class ThymeleafDialectSchemaProvider implements StandardResourceProvider {
    public static final String DIALECT_HELP_SCHEMA_URL = "http://www.thymeleaf.org/extras/dialect";

    @Override
    public void registerResources(ResourceRegistrar registrar) {
        registrar.addStdResource(DIALECT_HELP_SCHEMA_URL, "/resources/schemas/thymeleaf-dialect-help.xsd", getClass());
    }
}