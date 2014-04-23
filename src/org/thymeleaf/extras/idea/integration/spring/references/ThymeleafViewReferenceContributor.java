package org.thymeleaf.extras.idea.integration.spring.references;

import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;

public class ThymeleafViewReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        ThymeleafViewReferenceProvider.register(registrar);
    }
}
