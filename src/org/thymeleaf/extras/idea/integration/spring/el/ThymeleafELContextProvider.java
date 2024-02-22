package org.thymeleaf.extras.idea.integration.spring.el;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.impl.source.jsp.JspImplicitVariableImpl;
import com.intellij.psi.impl.source.jsp.el.ElContextProviderEx;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;

public class ThymeleafELContextProvider extends ElContextProviderEx {
    private PsiElement myHost;

    public ThymeleafELContextProvider(PsiElement myHost) {
        this.myHost = myHost;
    }

    @Nullable
    @Override
    public Iterator<? extends PsiVariable> getTopLevelElVariables(String nameHint) {
        if (nameHint.equals("dummy") || nameHint == null) {
            return Collections.singleton(new DummyVar(myHost, "dummy")).iterator();
        } else {
            return Collections.<PsiVariable>emptySet().iterator();
        }
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

    private static class DummyVar extends JspImplicitVariableImpl {
        public DummyVar(PsiElement scope, String name) {
            super(scope, name, PsiType.FLOAT, null);
        }
    }
}
