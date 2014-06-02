package org.thymeleaf.extras.idea.facet;

import com.intellij.facet.Facet;
import com.intellij.facet.FacetType;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Description type for {@link org.thymeleaf.extras.idea.facet.ThymeleafFacet}.
 * Adds autodetection feature for HTML files with thymeleaf namespaces found in the project.
 */
// TODO Implement detection based on Spring beans?
// TODO Implement detection based on thymeleaf libraries included via Gradle?
public class ThymeleafFacetType extends FacetType<ThymeleafFacet, ThymeleafFacetConfiguration> {

    ThymeleafFacetType() {
        // TODO Is it correct to use the WebFacet as the underlying facet here?
        super(ThymeleafFacet.FACET_TYPE_ID, "Thymeleaf", "Thymeleaf", WebFacet.ID);
    }

    public static FacetType<ThymeleafFacet, ThymeleafFacetConfiguration> getInstance() {
        return findInstance(ThymeleafFacetType.class);
    }

    @Override
    public ThymeleafFacetConfiguration createDefaultConfiguration() {
        return new ThymeleafFacetConfiguration();
    }

    @Override
    public ThymeleafFacet createFacet(@NotNull final Module module,
                                      final String name,
                                      @NotNull final ThymeleafFacetConfiguration configuration,
                                      @Nullable final Facet underlyingFacet) {
        // TODO name is required to be not null by the ThymeleafFacet constructor, but not the signature of this method
        return new ThymeleafFacet(this, module, name, configuration, underlyingFacet);
    }

    @Override
    public boolean isSuitableModuleType(ModuleType moduleType) {
        return moduleType instanceof JavaModuleType;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        // TODO Set ThymeleafFacetType.getIcon()
        return super.getIcon();
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        // TODO Set ThymeleafFacetType.getHelpTopic()
        return super.getHelpTopic();
    }
}
