package org.thymeleaf.extras.idea.fragment.selection.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiReference;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.fragment.selection.psi.TemplateName;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class FragmentSelectorCompletionContributor extends CompletionContributor implements DumbAware {
    public FragmentSelectorCompletionContributor() {
        extend(CompletionType.BASIC, psiElement().withParent(TemplateName.class), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull final CompletionParameters parameters,
                                          final ProcessingContext context,
                                          @NotNull final CompletionResultSet result) {
                TemplateName templateName = (TemplateName) parameters.getPosition().getParent();

                for (PsiReference reference : templateName.getReferences()) {
                    Object[] variants = reference.getVariants();

                    for (Object o : variants) {
                        if (o instanceof LookupElement) {
                            result.addElement((LookupElement) o);
                        }
                    }
                }
            }
        });
    }
}