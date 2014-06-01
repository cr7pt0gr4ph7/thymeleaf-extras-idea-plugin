package org.thymeleaf.extras.idea.integration.spring.el;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.jsp.JspImplicitVariableImpl;
import com.intellij.psi.impl.source.jsp.el.impl.ELElementProcessor;
import com.intellij.psi.impl.source.jsp.el.impl.ElVariablesProvider;
import com.intellij.psi.jsp.el.ELExpressionHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.DialectDescriptorsHolder;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.dom.model.ExpressionObject;

import javax.swing.*;

public class ThymeleafELVariablesProvider extends ElVariablesProvider {

    @Override
    public boolean processImplicitVariables(@NotNull PsiElement element, @NotNull ELExpressionHolder expressionHolder, @NotNull ELElementProcessor processor) {
        // TODO Extend to other dialects beside Thymeleaf standard dialect
        final Dialect dialect = DialectDescriptorsHolder.getInstance(element.getProject())
                .getDialectForSchemaUrl("http://www.thymeleaf.org", element);
        if (dialect == null) {
            return false;
        }
        for (ExpressionObject expressionObject : dialect.getExpressionObjects()) {
            //noinspection ObjectAllocationInLoop
            if (!processor.processVariable(createVariable(element, expressionObject))) {
                return false;
            }
        }
        return true;
    }

    private static JspImplicitVariableImpl createVariable(PsiElement scope, ExpressionObject source) {
        return new ExpressionObjectVariable(scope, source);
    }

    private static class ExpressionObjectVariable extends JspImplicitVariableImpl {
        private ExpressionObjectVariable(PsiElement scope, ExpressionObject source) {
            // TODO What does "NESTED" mean in this context?
            super(scope, source.getName().getValue(), source.getImplementationType(), null, "NESTED");
        }

        @Nullable
        @Override
        public Icon getIcon(boolean open) {
            // TODO Override ThymeleafELVariablesProvider.ExpressionObjectVariable.getIcon(boolean)
            return super.getIcon(open);
        }

        @Nullable
        @Override
        public String getLocationString() {
            return "Thymeleaf Utility Object";
        }
    }
}
