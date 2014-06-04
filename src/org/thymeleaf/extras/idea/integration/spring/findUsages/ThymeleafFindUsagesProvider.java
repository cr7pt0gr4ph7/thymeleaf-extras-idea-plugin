package org.thymeleaf.extras.idea.integration.spring.findUsages;

import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThymeleafFindUsagesProvider implements FindUsagesProvider {
    public ThymeleafFindUsagesProvider() {
        return;
    }

    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        // return new DefaultWordsScanner(...);
        return null;
    }

    @Override
    public boolean canFindUsagesFor(PsiElement psiElement) {
        return psiElement instanceof PsiNamedElement;
    }

    @Nullable
    @Override
    public String getHelpId(PsiElement psiElement) {
        return null;
    }

    @NotNull
    @Override
    public String getType(PsiElement element) {
        return "reference";
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof PsiNamedElement) {
            return StringUtil.notNullize(((PsiNamedElement) element).getName());
        }
        return "";
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        final String name = element instanceof PsiNamedElement ? ((PsiNamedElement) element).getName() : null;
        return name != null ? name : element.getText();
    }
}
