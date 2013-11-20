package org.thymeleaf.extras.idea.lang.expression;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ThymeleafExpressionFileType extends LanguageFileType {
    public static final LanguageFileType INSTANCE = new ThymeleafExpressionFileType();

    private ThymeleafExpressionFileType() {
        super(ThymeleafExpressionLanguage.INSTANCE);
    }

    @NotNull
    public String getName() {
        return ThymeleafExpressionLanguage.ID;
    }

    @NotNull
    public String getDescription() {
        return "Thymeleaf fragment selectors - some/demo :: fragment.";
    }

    @NotNull
    @NonNls
    public String getDefaultExtension() {
        return "thel";
    }

    public Icon getIcon() {
        return AllIcons.FileTypes.Custom;
    }
}
