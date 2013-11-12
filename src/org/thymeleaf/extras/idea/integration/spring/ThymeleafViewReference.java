package org.thymeleaf.extras.idea.integration.spring;

import com.intellij.codeInsight.daemon.EmptyResolveMessageProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.ElementManipulator;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.spring.web.mvc.SpringMVCModel;
import com.intellij.spring.web.mvc.views.ViewResolver;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

// TODO Make ThymeleafViewReference package-scoped again
public class ThymeleafViewReference extends PsiReferenceBase<PsiElement>
        implements EmptyResolveMessageProvider {
    private static final Logger LOG = Logger.getInstance(ThymeleafViewReference.class);
    private final List<ViewResolver> myResolvers;
    private ViewResolver myResolver;

    public ThymeleafViewReference(PsiElement element, List<ViewResolver> resolvers, TextRange range, boolean soft) {
        super(element, range, soft);
        this.myResolvers = resolvers;
    }

    public PsiElement resolve() {
        SpringMVCModel model = MySpringMVCUtil.getSpringMVCModelForPsiElement(getElement());
        if (model == null) return null;

        String viewName = getCanonicalText();
        for (ViewResolver resolver : this.myResolvers) {
            PsiElement psiElement = resolver.resolveView(viewName, model);
            if (psiElement != null) {
                this.myResolver = resolver;
                return psiElement;
            }
        }
        return null;
    }

    @NotNull
    public Object[] getVariants() { // Byte code:
        //   0: aload_0
        //   1: invokevirtual 36	com/intellij/spring/web/mvc/views/ViewReference:getElement	()Lcom/intellij/psi/PsiElement;
        //   4: invokestatic 42	com/intellij/spring/web/mvc/SpringMVCModel:getModel	(Lcom/intellij/psi/PsiElement;)Lcom/intellij/spring/web/mvc/SpringMVCModel;
        //   7: astore_1
        //   8: aload_1
        //   9: ifnonnull +21 -> 30
        //   12: getstatic 89	com/intellij/spring/web/mvc/views/ViewReference:EMPTY_ARRAY	[Lcom/intellij/psi/PsiReference;
        //   15: dup
        //   16: ifnonnull +13 -> 29
        //   19: new 91	java/lang/IllegalStateException
        //   22: dup
        //   23: ldc 93
        //   25: invokespecial 96	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
        //   28: athrow
        //   29: areturn
        //   30: new 98	java/util/ArrayList
        //   33: dup
        //   34: invokespecial 101	java/util/ArrayList:<init>	()V
        //   37: astore_2
        //   38: aload_0
        //   39: getfield 22	com/intellij/spring/web/mvc/views/ViewReference:myResolvers	Ljava/util/List;
        //   42: invokeinterface 52 1 0
        //   47: astore_3
        //   48: aload_3
        //   49: invokeinterface 58 1 0
        //   54: ifeq +30 -> 84
        //   57: aload_3
        //   58: invokeinterface 62 1 0
        //   63: checkcast 64	com/intellij/spring/web/mvc/views/ViewResolver
        //   66: astore 4
        //   68: aload_2
        //   69: aload 4
        //   71: aload_1
        //   72: invokevirtual 105	com/intellij/spring/web/mvc/views/ViewResolver:getAllViews	(Lcom/intellij/spring/web/mvc/SpringMVCModel;)Ljava/util/List;
        //   75: invokeinterface 109 2 0
        //   80: pop
        //   81: goto -33 -> 48
        //   84: aload_2
        //   85: invokestatic 115	com/intellij/util/ArrayUtil:toObjectArray	(Ljava/util/Collection;)[Ljava/lang/Object;
        //   88: dup
        //   89: ifnull -70 -> 19
        //   92: areturn
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        LOG.assertTrue(this.myResolver != null, "Trying to bind a non-resolved reference? Resolvers: " + this.myResolvers + ", element: " + element);

        String newName = this.myResolver.bindToElement(element);
        return newName == null ? getElement() : ElementManipulators.getManipulator(getElement()).handleContentChange(getElement(), newName);
    }

    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return super.handleElementRename(this.myResolver.handleElementRename(newElementName));
    }

    @NotNull
    public String getUnresolvedMessagePattern() {
        return (this.myResolvers.isEmpty() ? "No view resolvers found" : "Cannot resolve MVC View ''{0}''");
    }
}