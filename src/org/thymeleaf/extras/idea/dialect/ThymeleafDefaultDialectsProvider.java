package org.thymeleaf.extras.idea.dialect;

import com.intellij.javaee.ExternalResourceManager;
import com.intellij.javaee.ExternalResourceManagerEx;
import com.intellij.javaee.ResourceRegistrar;
import com.intellij.javaee.StandardResourceProvider;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class ThymeleafDefaultDialectsProvider implements StandardResourceProvider {
    public static final String STANDARD_DIALECT_URL = "http://www.thymeleaf.org";
    public static final String SPRING_STANDARD_DIALECT_URL = "http://www.thymeleaf.org/spring";
    public static final String SPRING_SECURITY_DIALECT_URL = "http://www.thymeleaf.org/extras/spring-security";
    public static final String TILES_DIALECT_URL = "http://www.thymeleaf.org/extras/tiles";

    @Override
    public void registerResources(ResourceRegistrar registrar) {
        registrar.addStdResource(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL, "/dialects/Standard-Dialect.xml", getClass());
        registrar.addStdResource(ThymeleafDefaultDialectsProvider.SPRING_STANDARD_DIALECT_URL, "/dialects/Spring-Standard-Dialect.xml", getClass());
        registrar.addStdResource(ThymeleafDefaultDialectsProvider.SPRING_SECURITY_DIALECT_URL, "/dialects/Spring-Security-Dialect.xml", getClass());
        registrar.addStdResource(ThymeleafDefaultDialectsProvider.TILES_DIALECT_URL, "/dialects/Tiles-Dialect.xml", getClass());
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
