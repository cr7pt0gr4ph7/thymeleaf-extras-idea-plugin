package org.thymeleaf.extras.idea.integration.properties.references;

import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;

/**
 * Boilerplate code for registering {@link PropertiesReferenceProvider}.
 *
 * @see PropertiesReferenceProvider
 */
public class PropertiesReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        PropertiesReferenceProvider.register(registrar);
    }
}
