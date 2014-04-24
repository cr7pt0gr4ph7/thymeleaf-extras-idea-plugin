package org.thymeleaf.extras.idea.integration.properties;

import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;

/**
 * Boilerplate code for registering {@link org.thymeleaf.extras.idea.integration.properties.PropertiesReferenceProvider}.
 *
 * @see org.thymeleaf.extras.idea.integration.properties.PropertiesReferenceProvider
 */
public class PropertiesReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        PropertiesReferenceProvider.register(registrar);
    }
}
