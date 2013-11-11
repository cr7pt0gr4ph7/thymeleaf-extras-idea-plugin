package org.thymeleaf.extras.idea.fragment.selection.completion;

import com.intellij.codeInsight.TailType;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.TailTypeDecorator;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.fragment.selection.parser.FragmentSelectorElementTypes;
import org.thymeleaf.extras.idea.fragment.selection.psi.FragmentSelectionExpression;

import java.util.List;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class FragmentSelectorCompletionContributor extends CompletionContributor implements DumbAware {
    //
    private static final PsiElementPattern.Capture<PsiElement> FRAGMENT_SELECTION_EXPRESSION =
            psiElement().inside(FragmentSelectionExpression.class);
    //
    private static final PsiElementPattern.Capture<PsiElement> AFTER_OPERATOR =
            psiElement().afterLeaf(psiElement().withElementType(FragmentSelectorElementTypes.OPERATOR));
    //
    private static final PsiElementPattern.Capture<PsiElement> AT_EXPR_START =
            psiElement().atStartOf(FRAGMENT_SELECTION_EXPRESSION);
    //
    private static final PsiElementPattern.Capture<PsiElement> TEMPLATE_NAME =
            AT_EXPR_START.andNot(AFTER_OPERATOR);
    //
    private static final String[] completions = new String[]{
            "abc",
            "def",
            "directory/document"
    };

    public FragmentSelectorCompletionContributor() {
        extend(CompletionType.BASIC, TEMPLATE_NAME, new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull final CompletionParameters parameters,
                                          final ProcessingContext context,
                                          @NotNull final CompletionResultSet result) {
                for (final String fileName : completions) {
                    final LookupElementBuilder builder = LookupElementBuilder.create(fileName).bold();
                    result.addElement(TailTypeDecorator.withTail(builder, TailType.SPACE));
                }
            }
        });
    }
}