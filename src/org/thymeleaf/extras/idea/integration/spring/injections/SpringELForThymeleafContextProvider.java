package org.thymeleaf.extras.idea.integration.spring.injections;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.jsp.JspImplicitVariableImpl;
import com.intellij.psi.impl.source.jsp.el.ElContextProviderEx;
import com.intellij.psi.impl.source.jsp.el.impl.ELElementProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.DialectDescriptorsHolder;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.dom.model.ExpressionObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpringELForThymeleafContextProvider extends ElContextProviderEx {
    private final PsiElement myHost;

    public SpringELForThymeleafContextProvider(@NotNull PsiElement host) {
        myHost = host;
    }

    @Nullable
    @Override
    public Iterator<? extends PsiVariable> getTopLevelElVariables(String nameHint) {
        // TODO Extend to other dialects beside Thymeleaf standard dialect
        final Dialect dialect = DialectDescriptorsHolder.getInstance(myHost.getProject())
                .getDialectForSchemaUrl("http://www.thymeleaf.org/extras/dialect", myHost);

        if (dialect == null) {
            return null;
        }

        if (nameHint == null || nameHint != null) {
            final List<PsiVariable> result = new ArrayList<PsiVariable>();
            for (ExpressionObject expressionObject : dialect.getExpressionObjects()) {
                //noinspection ObjectAllocationInLoop
                result.add(new ExpressionObjectVariable(myHost, expressionObject));
            }
            return result.iterator();
        }

        return null;
    }

    @Override
    public boolean acceptsGetMethodForLastReference(PsiMethod psiMethod) {
        return true;
    }

    @Override
    public boolean acceptsSetMethodForLastReference(PsiMethod psiMethod) {
        return true;
    }

    @Override
    public boolean acceptsNonPropertyMethodForLastReference(PsiMethod psiMethod) {
        return true;
    }

    private static class ExpressionObjectVariable extends JspImplicitVariableImpl {
        private ExpressionObjectVariable(PsiElement scope, ExpressionObject source) {
            // TODO What does "NESTED" mean in this context?
            super(scope, source.getName().getValue(), source.getImplementationType(), null, "NESTED");
        }

        @Nullable
        @Override
        public String getLocationString() {
            // TODO What is the purpose of ExpressionObjectVariable.getLocationString()?
            return "ExpressionObjectVariable Location String";
        }
    }
}
