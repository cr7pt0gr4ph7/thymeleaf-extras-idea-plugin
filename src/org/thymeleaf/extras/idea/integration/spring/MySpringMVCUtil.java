package org.thymeleaf.extras.idea.integration.spring;

import com.intellij.javaee.web.WebUtil;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.intellij.spring.facet.SpringFacet;
import com.intellij.spring.web.mvc.SpringMVCModel;

/**
 * Some helper methods & workarounds for working with the Spring plugin.
 */
class MySpringMVCUtil {
    public static SpringMVCModel getSpringMVCModelForPsiElement(final PsiElement element) {
        Module module = ModuleUtilCore.findModuleForPsiElement(element);
        if (module == null) {
            return null;
        }

        SpringFacet springFacet = SpringFacet.getInstance(module);
        if (springFacet == null) {
            return null;
        }

        WebFacet webFacet = findWebFacetForPsiElement(element);
        if (webFacet == null) {
            return null;
        }

        return SpringMVCModel.getModel(webFacet, springFacet);
    }

    private static WebFacet findWebFacetForPsiElement(final PsiElement element) {
        // NOTE: WebUtil.findWebFacetForPsiElement doesn't seem to work well with language injections,
        //       so we have to use our own version that is aware of language injections.
        // TODO Is the implementation of MySpringMVCUtil.findWebFacetForPsiElement correct?

        PsiFile psiFile = InjectedLanguageUtil.getTopLevelFile(element);
        if (psiFile == null) {
            return null;
        }

        VirtualFile virtualFile = psiFile.getVirtualFile();
        if (virtualFile == null) {
            return null;
        }

        return WebUtil.getWebFacet(virtualFile, element.getProject());
    }

}
