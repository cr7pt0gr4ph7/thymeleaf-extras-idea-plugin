package org.thymeleaf.extras.idea.html;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.util.NotNullLazyValue;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.ThymeleafBundle;
import org.thymeleaf.extras.idea.fragment.selection.psi.FragmentSelectionExpression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FragmentSelectorLineMarkerProvider implements LineMarkerProvider {
    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull final PsiElement element) {
        if (!(element instanceof FragmentSelectionExpression)) {
            return null;
        }

        final NavigationGutterIconBuilder<PsiElement> gutterIconBuilder =
                NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementingMethod).
                        setAlignment(GutterIconRenderer.Alignment.LEFT).
                        setTooltipText(ThymeleafBundle.message("annotators.html.goto.fragment.declaration")).
                        setEmptyPopupText(ThymeleafBundle.message("annotators.html.goto.fragment.declaration.not.found")).
                        setTargets(new NotNullLazyValue<Collection<? extends PsiElement>>() {
                            @NotNull
                            protected Collection<PsiElement> compute() {
                                final FragmentSelectionExpression selector = (FragmentSelectionExpression) element;
                                final List<PsiElement> results = new ArrayList<PsiElement>();

                                resolveAndAddIfNotNull(selector.getDomSelector(), results);
                                resolveAndAddIfNotNull(selector.getTemplateName(), results);

                                return results;
                            }
                        });

        return gutterIconBuilder.createLineMarkerInfo(element);
    }

    private static void resolveAndAddIfNotNull(PsiElement element, Collection<PsiElement> resolvedElements) {
        if (element != null) {
            addAllElements(element.getReferences(), resolvedElements);
        }
    }

    private static void addAllElements(PsiReference[] references, Collection<PsiElement> elements) {
        for (PsiReference reference : references) {
            elements.add(reference.resolve());
        }
    }

    @Override
    public void collectSlowLineMarkers(@NotNull final List<PsiElement> psiElements,
                                       @NotNull final Collection<LineMarkerInfo> lineMarkerInfos) {
        if (psiElements.isEmpty()) {
            return;
        }

        for (PsiElement psiElement : psiElements) {
            annotate(psiElement, lineMarkerInfos);
        }
    }

    private static void annotate(@NotNull final PsiElement psiElement,
                                 @NotNull final Collection<LineMarkerInfo> lineMarkerInfo) {
    }
}


