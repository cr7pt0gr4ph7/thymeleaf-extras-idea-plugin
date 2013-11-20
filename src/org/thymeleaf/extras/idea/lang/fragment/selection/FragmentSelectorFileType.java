package org.thymeleaf.extras.idea.lang.fragment.selection;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class FragmentSelectorFileType extends LanguageFileType {
    public static final LanguageFileType INSTANCE = new FragmentSelectorFileType();

    private FragmentSelectorFileType() {
        super(FragmentSelectorLanguage.INSTANCE);
    }

    @NotNull
    public String getName() {
        return FragmentSelectorLanguage.ID;
    }

    @NotNull
    public String getDescription() {
        return "Thymeleaf fragment selectors - some/demo :: fragment.";
    }

    @NotNull
    @NonNls
    public String getDefaultExtension() {
        return "thfs";
    }

    public Icon getIcon() {
        return AllIcons.FileTypes.Custom;
    }
}
