package org.thymeleaf.extras.idea.facet;

import com.intellij.facet.Facet;
import com.intellij.facet.FacetManager;
import com.intellij.facet.FacetType;
import com.intellij.facet.FacetTypeId;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.Disposer;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Thymeleaf facet.
 */
public class ThymeleafFacet extends Facet<ThymeleafFacetConfiguration> {

    public static final FacetTypeId<ThymeleafFacet> FACET_TYPE_ID = new FacetTypeId<ThymeleafFacet>("thymeleaf");

    @SuppressWarnings({"rawtypes", "ThisEscapedInObjectConstruction"})
    public ThymeleafFacet(@NotNull final FacetType facetType,
                          @NotNull final Module module,
                          @NotNull final String name,
                          @NotNull final ThymeleafFacetConfiguration configuration,
                          final Facet underlyingFacet) {
        super(facetType, module, name, configuration, underlyingFacet);
        Disposer.register(this, configuration);
    }

    /**
     * Gets the ThymeleafFacet for the given module.
     *
     * @param module Module to check.
     * @return Instance or {@code null} if none configured.
     */
    @Nullable
    public static ThymeleafFacet getInstance(@NotNull final Module module) {
        return FacetManager.getInstance(module).getFacetByType(FACET_TYPE_ID);
    }

    /**
     * Gets the ThymeleafFacet for the module containing the given PsiElement.
     *
     * @param element Element to check.
     * @return Instance or {@code null} if none configured.
     */
    @Nullable
    public static ThymeleafFacet getInstance(@NotNull final PsiElement element) {
        final Module module = ModuleUtilCore.findModuleForPsiElement(element);
        return module != null ? getInstance(module) : null;
    }

    /**
     * Returns the underlying WebFacet.
     *
     * @return WebFacet.
     */
    @NotNull
    public WebFacet getWebFacet() {
        return (WebFacet) getUnderlyingFacet();
    }
}
