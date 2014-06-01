package org.thymeleaf.extras.idea.integration.spring.injections;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.impl.source.jsp.el.ELContextProvider;
import com.intellij.spring.el.SpringELLanguage;
import com.intellij.spring.el.SpringElContextProvider;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.psi.GenericSelectionExpr;
import org.thymeleaf.extras.idea.lang.expression.psi.SelectionExpr;
import org.thymeleaf.extras.idea.lang.expression.psi.VariableExpr;

import java.util.Arrays;
import java.util.List;

import static org.thymeleaf.extras.idea.util.MyTextRangeUtil.makeRelativeTo;

public class SelectionOrVariableSyntaxInjector implements MultiHostInjector, DumbAware {

    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        if (!context.isValid() || !context.getContainingFile().isValid()) {
            // Do not try to inject something into invalid elements, e.g. elements that have
            // already been removed from the internal tree, or that are members of a file that
            // has become invalid, i.e. has been removed.
            return;
        }

        if (context instanceof SelectionExpr || context instanceof VariableExpr) {
            final GenericSelectionExpr expression = (GenericSelectionExpr) context;
            final PsiElement expressionString = expression.getString();

            if (expressionString != null) {
                doRegister(registrar, expression, expressionString);
            }
        }
    }

    private void doRegister(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement host, @NotNull PsiElement injectionTargetInsideHost) {
        doRegister(registrar, host, makeRelativeTo(host.getTextRange(), injectionTargetInsideHost.getTextRange()));
    }

    private static void doRegister(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement host, @NotNull TextRange rangeInHost) {
        registrar.startInjecting(SpringELLanguage.INSTANCE).addPlace(null, null, (PsiLanguageInjectionHost) host, rangeInHost).doneInjecting();
        host.putUserData(ELContextProvider.ourContextProviderKey, new SpringELForThymeleafContextProvider(host));
    }

    @NotNull
    @Override
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Arrays.asList(SelectionExpr.class, VariableExpr.class);
    }
}
