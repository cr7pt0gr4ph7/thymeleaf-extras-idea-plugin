package org.thymeleaf.extras.idea.dialect;

import com.intellij.javaee.ExternalResourceManager;
import com.intellij.javaee.ExternalResourceManagerEx;
import com.intellij.javaee.ResourceRegistrar;
import com.intellij.javaee.StandardResourceProvider;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

public class ThymeleafDefaultDialectsProvider implements StandardResourceProvider {
    public static final String STANDARD_DIALECT_URL = "http://www.thymeleaf.org";
    public static final String SPRING_STANDARD_DIALECT_URL = "http://www.thymeleaf.org/spring";
    public static final String SPRING_SECURITY_DIALECT_URL = "http://www.thymeleaf.org/extras/spring-security";
    public static final String TILES_DIALECT_URL = "http://www.thymeleaf.org/extras/tiles";

    public static final String FIX_SPRING_STANDARD_DIALECT_URL = "http://www.thymeleaf.org/spring/custom-fix";

    @Override
    public void registerResources(ResourceRegistrar registrar) {
        registrar.addStdResource(STANDARD_DIALECT_URL, "/resources/dialects/Standard-Dialect.xml", getClass());
        registrar.addStdResource(SPRING_STANDARD_DIALECT_URL, "/resources/dialects/Spring-Standard-Dialect.xml", getClass());
        registrar.addStdResource(SPRING_SECURITY_DIALECT_URL, "/resources/dialects/Spring-Security-Dialect.xml", getClass());
        registrar.addStdResource(TILES_DIALECT_URL, "/resources/dialects/Tiles-Dialect.xml", getClass());

        registrar.addStdResource(FIX_SPRING_STANDARD_DIALECT_URL, "/resources/dialects/Fix-Spring-Standard-Dialect.xml", getClass());
    }

    @Nullable
    public static VirtualFile getStandardSchemaFile(@NotNull String schemaUrl) {
        final String location = ((ExternalResourceManagerEx) ExternalResourceManager.getInstance()).getStdResource(schemaUrl, null);
        if (location == null) {
            // No mapping found. This is the point where we filter out namespaces that do not refer to dialects.
            return null;
        }

        final VirtualFile result = VfsUtil.findFileByURL(createURL(location));
        assert result != null : "cannot find a schema file for URL: " + schemaUrl + " location: " + location;

        return result;
    }

    private static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException wrapped) {
            throw new IllegalArgumentException("Malformed URL parameter: " + url, wrapped);
        }
    }
}
