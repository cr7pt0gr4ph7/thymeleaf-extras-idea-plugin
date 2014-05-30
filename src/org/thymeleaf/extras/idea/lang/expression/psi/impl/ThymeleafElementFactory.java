package org.thymeleaf.extras.idea.lang.expression.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.ThymeleafExpressionFile;
import org.thymeleaf.extras.idea.lang.expression.ThymeleafExpressionFileType;
import org.thymeleaf.extras.idea.lang.expression.psi.Expression;

public class ThymeleafElementFactory {
    @NotNull
    public static Expression createExpressionFromText(@NotNull Project project, @NonNls @NotNull CharSequence text) {
        return (Expression) createFileFromText(project, text).getChildren()[0];
    }

    @NotNull
    private static ThymeleafExpressionFile createFileFromText(@NotNull Project project, @NonNls @NotNull CharSequence text) {
        final String fileName = "dummy." + ThymeleafExpressionFileType.INSTANCE.getDefaultExtension();
        return (ThymeleafExpressionFile) PsiFileFactory.getInstance(project).createFileFromText(fileName, ThymeleafExpressionFileType.INSTANCE, text);
    }
}
