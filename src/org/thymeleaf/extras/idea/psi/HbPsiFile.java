package org.thymeleaf.extras.idea.psi;

import org.thymeleaf.extras.idea.file.HbFileType;
import org.thymeleaf.extras.idea.HbLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.impl.PsiFileEx;
import org.jetbrains.annotations.NotNull;

public class HbPsiFile extends PsiFileBase implements PsiFileEx {

    public HbPsiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, HbLanguage.INSTANCE);
    }

    @NotNull
    public FileType getFileType() {
        return HbFileType.INSTANCE;
    }
}
