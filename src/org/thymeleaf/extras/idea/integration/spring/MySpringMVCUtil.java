package org.thymeleaf.extras.idea.integration.spring;

import com.intellij.javaee.web.WebUtil;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.spring.facet.SpringFacet;
import com.intellij.spring.web.mvc.SpringMVCModel;
import org.jetbrains.annotations.Nullable;

/**
 * Some helper methods & workarounds for working with the Spring plugin.
 */
public class MySpringMVCUtil {
    @Nullable
    public static SpringMVCModel getSpringMVCModelForPsiElement(final PsiElement element) {
        // TODO Check if element is valid (PsiElement.isValid())?
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

    @Nullable
    private static WebFacet findWebFacetForPsiElement(final PsiElement element) {
        // NOTE: WebUtil.findWebFacetForPsiElement doesn't seem to work well with language injections,
        //       so we have to use our own version that is aware of language injections.
        // TODO Is the implementation of MySpringMVCUtil.findWebFacetForPsiElement correct?

        PsiFile psiFile = getTopLevelFile(element);
        if (psiFile == null) {
            return null;
        }

        VirtualFile virtualFile = psiFile.getVirtualFile();
        if (virtualFile == null) {
            // NOTE: psiFile.getVirtualFile() returns null when called in a completion handler.
            //       The workaround is to use psiFile.getOriginalFile().getVirtualFile() in those cases.
            //       Which might return null, too! -> Completion is not available in those cases.

            virtualFile = psiFile.getOriginalFile().getVirtualFile();

            if (virtualFile == null) {
                return null;
            }
        }

        return WebUtil.getWebFacet(virtualFile, element.getProject());
    }

    private static PsiFile getTopLevelFile(final PsiElement element) {
        // NOTE: InjectedLanguageUtil.getTopLevelFile seems to return the file
        //       that is shown in the *top level window*, not the conceptual containing file.
        PsiFile current, next = element.getContainingFile();
        do {
            current = next;
            next = getNextLevelFile(current);
        } while (next != null);
        return current;
    }

    @Nullable
    private static PsiFile getNextLevelFile(final PsiFile element) {
        PsiElement context = element.getContext();
        if (context != null) {
            return context.getContainingFile();
        }
        return null;
    }
}
