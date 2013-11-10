package org.thymeleaf.extras.idea.fragment.selection;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class FragmentSelectorFile extends PsiFileBase {
    protected FragmentSelectorFile(@NotNull final FileViewProvider fileViewProvider) {
        super(fileViewProvider, FragmentSelectorLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return FragmentSelectorFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "FragmentSelectorFile: " + getName();
    }

    @NotNull
    @Override
    public ParserDefinition getParserDefinition() {
        return new FragmentSelectorParserDefinition();
    }
}
