package org.thymeleaf.extras.idea.integration.properties.references;

import com.intellij.lang.properties.IProperty;
import com.intellij.lang.properties.references.PropertyReference;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.psi.MessageExpr;

import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 * A {@link com.intellij.psi.PsiReferenceProvider} that injects {@link com.intellij.lang.properties.references.PropertyReference}s
 * into {@code #{message.key}} expressions.
 */
public class PropertiesReferenceProvider extends PsiReferenceProvider {

    private final boolean myDefaultSoft;

    public PropertiesReferenceProvider() {
        this(false);
    }

    public PropertiesReferenceProvider(final boolean defaultSoft) {
        myDefaultSoft = defaultSoft;
    }

    @Override
    public boolean acceptsTarget(@NotNull PsiElement target) {
        return target instanceof IProperty;
    }

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(PsiElement element, ProcessingContext context) {
        if (element instanceof MessageExpr) {
            final MessageExpr messageExpr = (MessageExpr) element;

            if (messageExpr.getString() != null) {
                final String bundleName = null;
                final boolean soft = myDefaultSoft;

                final PsiReference reference = new PropertyReference(messageExpr.getString().getText(), messageExpr.getString(), bundleName, soft);
                return new PsiReference[]{reference};
            }
        }
        return PsiReference.EMPTY_ARRAY;
    }

    public static void register(PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(psiElement(MessageExpr.class), new PropertiesReferenceProvider());
    }
}
