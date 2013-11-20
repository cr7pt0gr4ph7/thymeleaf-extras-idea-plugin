package org.thymeleaf.extras.idea.lang.fragment.selection.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.impl.PsiFileEx;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.fragment.selection.FragmentSelectorFileType;
import org.thymeleaf.extras.idea.lang.fragment.selection.FragmentSelectorLanguage;

public class FragmentSelectorPsiFile extends PsiFileBase implements PsiFileEx {
    public FragmentSelectorPsiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, FragmentSelectorLanguage.INSTANCE);
    }

    @NotNull
    public FileType getFileType() {
        return FragmentSelectorFileType.INSTANCE;
    }
}
