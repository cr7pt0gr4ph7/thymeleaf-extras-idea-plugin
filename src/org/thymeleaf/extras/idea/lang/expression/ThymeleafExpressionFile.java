package org.thymeleaf.extras.idea.lang.expression;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.fragment.selection.FragmentSelectorFileType;
import org.thymeleaf.extras.idea.lang.fragment.selection.FragmentSelectorLanguage;

public class ThymeleafExpressionFile extends PsiFileBase {
    protected ThymeleafExpressionFile(@NotNull final FileViewProvider fileViewProvider) {
        super(fileViewProvider, ThymeleafExpressionLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return ThymeleafExpressionFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "ThymeleafExpressionFile: " + getName();
    }

    @NotNull
    @Override
    public ParserDefinition getParserDefinition() {
        return new ThymeleafExpressionParserDefinition();
    }
}
