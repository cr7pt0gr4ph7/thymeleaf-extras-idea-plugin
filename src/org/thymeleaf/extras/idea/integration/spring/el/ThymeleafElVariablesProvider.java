package org.thymeleaf.extras.idea.integration.spring.el;

import com.intellij.lang.injection.InjectedLanguageManager;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.jsp.JspImplicitVariableImpl;
import com.intellij.psi.impl.source.jsp.el.impl.ELElementProcessor;
import com.intellij.psi.impl.source.jsp.el.impl.ELResolveUtil;
import com.intellij.psi.impl.source.jsp.el.impl.ElVariablesProvider;
import com.intellij.psi.jsp.el.ELExpression;
import com.intellij.psi.jsp.el.ELExpressionHolder;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.dialect.DialectDescriptorsHolder;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;
import org.thymeleaf.extras.idea.dialect.dom.model.ExpressionObject;
import org.thymeleaf.extras.idea.lang.expression.ThymeleafExpressionFile;
import org.thymeleaf.extras.idea.lang.expression.psi.Expression;
import org.thymeleaf.extras.idea.lang.expression.psi.GenericSelectionExpr;
import org.thymeleaf.extras.idea.lang.expression.psi.SelectionExpr;
import org.thymeleaf.extras.idea.lang.expression.psi.VariableExpr;

import javax.swing.*;
import java.util.List;

public class ThymeleafELVariablesProvider extends ElVariablesProvider {

    @Override
    public boolean processImplicitVariables(@NotNull PsiElement element, @NotNull ELExpressionHolder expressionHolder, @NotNull ELElementProcessor processor) {
        // TODO Extend to other dialects beside Thymeleaf standard dialect
        final Dialect dialect = DialectDescriptorsHolder.getInstance(element.getProject())
                .getDialectForSchemaUrl("http://www.thymeleaf.org", element);
        if (dialect == null) {
            return false;
        }
        if (isInsideSelectionExpression(expressionHolder)) {
            if (!processSelectionContext(expressionHolder, processor)) {
                return false;
            }
        }
        for (ExpressionObject expressionObject : dialect.getExpressionObjects()) {
            //noinspection ObjectAllocationInLoop
            if (!processor.processVariable(createVariable(element, expressionObject))) {
                return false;
            }
        }
        return true;
    }

    private boolean processSelectionContext(ELExpressionHolder expressionHolder, ELElementProcessor processor) {
        // We are inside a *{...} expression.
        // All variables are resolved relative to the selection root.
        final ELExpression selectionRoot = findSelectionRoot(expressionHolder);
        if (selectionRoot == null) return true;
        PsiType resolvedContext = ELResolveUtil.resolveContextAsType(selectionRoot);
        if (resolvedContext == null) return true;

        if (resolvedContext instanceof PsiClassType && resolvedContext.isValid()) {
            PsiClassType.ClassResolveResult classResolveResult = ((PsiClassType) resolvedContext).resolveGenerics();
            PsiClass psiClass = classResolveResult.isValidResult() ? classResolveResult.getElement() : null;
            if ((psiClass instanceof PsiTypeParameter)) {
                PsiReferenceList list = psiClass.getExtendsList();
                if (list != null) {
                    PsiClassType[] classTypes = list.getReferencedTypes();
                    if (classTypes.length > 0) {
                        resolvedContext = classTypes[0];
                        psiClass = ((PsiClassType) resolvedContext).resolve();
                    }
                }
            }

            // TODO Handle Properties, Maps and PropertyResourceBundles?
            if (psiClass != null) {
                return ELResolveUtil.iterateClassProperties(psiClass, processor, true);
            }
        }
        return true;
    }

    private static boolean isInsideSelectionExpression(@NotNull final PsiElement psiElement) {
        // TODO How does this interact with MessageExpr and LinkExpr?
        final GenericSelectionExpr selectionOrVariableExpr = PsiTreeUtil.getParentOfType(psiElement, GenericSelectionExpr.class);
        return selectionOrVariableExpr instanceof SelectionExpr;
    }

    private static ELExpression findSelectionRoot(@NotNull final PsiElement psiElement) {
        final XmlAttribute attribute = findNearestSelectionObjectAttribute(psiElement);
        if (attribute == null) return null;
        final XmlAttributeValue attributeValue = attribute.getValueElement();
        if (attributeValue == null) return null;
        final List<Pair<PsiElement, TextRange>> injectedPsiFiles = InjectedLanguageManager.getInstance(psiElement.getProject()).getInjectedPsiFiles(attributeValue);
        assert injectedPsiFiles != null && !injectedPsiFiles.isEmpty() : "No injected PSI file found for th:object attribute";
        final PsiElement injectedPsi = injectedPsiFiles.isEmpty() ? null : injectedPsiFiles.get(0).getFirst();

        if (injectedPsi instanceof ThymeleafExpressionFile) {
            final Expression outerExpression = (Expression) injectedPsi.getFirstChild();

            if (outerExpression instanceof VariableExpr || outerExpression instanceof SelectionExpr) {
                final GenericSelectionExpr selectionExpr = (GenericSelectionExpr) outerExpression;
                final ELExpressionHolder elHolder = (ELExpressionHolder) selectionExpr.getString();
                // TODO findSelectionRoot fails with an Exception in some error conditions
                final ELExpression expression = (ELExpression) elHolder.getFirstChild();
                return expression;
            }
        }
        return null;
    }

    private static XmlAttribute findNearestSelectionObjectAttribute(@NotNull final PsiElement psiElement) {
        XmlTag xmlTag = PsiTreeUtil.getContextOfType(psiElement, XmlTag.class);
        while (xmlTag != null) {
            final XmlAttribute attribute = xmlTag.getAttribute("object", "http://www.thymeleaf.org");
            if (attribute != null) return attribute;
            xmlTag = xmlTag.getParentTag();
        }
        return null;
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
