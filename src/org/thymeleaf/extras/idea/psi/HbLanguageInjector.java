package org.thymeleaf.extras.idea.psi;


import org.thymeleaf.extras.idea.HbLanguage;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.InjectedLanguagePlaces;
import com.intellij.psi.LanguageInjector;
import com.intellij.psi.PsiLanguageInjectionHost;
import org.jetbrains.annotations.NotNull;

public class HbLanguageInjector implements LanguageInjector {
    @Override
    public void getLanguagesToInject(@NotNull PsiLanguageInjectionHost host, @NotNull InjectedLanguagePlaces injectionPlacesRegistrar) {
        if (host instanceof HbEmbeddedContent) {
            injectionPlacesRegistrar.addPlace(
                    HbLanguage.INSTANCE,
                    TextRange.create(0, host.getTextLength()),
                    null,
                    null
            );
        }
    }
}