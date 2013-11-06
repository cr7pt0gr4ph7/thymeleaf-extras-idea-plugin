package org.thymeleaf.extras.idea.fragment.selection.file;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter;
import org.thymeleaf.extras.idea.HbBundle;
import org.thymeleaf.extras.idea.HbIcons;
import org.thymeleaf.extras.idea.HbLanguage;
import org.thymeleaf.extras.idea.HbTemplateHighlighter;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileTypes.EditorHighlighterProvider;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeEditorHighlighterProviders;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.TemplateLanguageFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thymeleaf.extras.idea.fragment.selection.FragmentSelectorHighlighter;

import javax.swing.Icon;
import java.nio.charset.Charset;

public class FragmentSelectorFileType extends LanguageFileType implements TemplateLanguageFileType {
    public static final LanguageFileType INSTANCE = new FragmentSelectorFileType();
    @NonNls
    public static final String DEFAULT_EXTENSION = "thfs";

    private FragmentSelectorFileType() {
        super(HbLanguage.INSTANCE);

        FileTypeEditorHighlighterProviders.INSTANCE.addExplicitExtension(this, new EditorHighlighterProvider() {
            public EditorHighlighter getEditorHighlighter(@Nullable Project project,
                                                          @NotNull FileType fileType,
                                                          @Nullable VirtualFile virtualFile,
                                                          @NotNull EditorColorsScheme editorColorsScheme) {
                return new LayeredLexerEditorHighlighter(new FragmentSelectorHighlighter(), editorColorsScheme);
            }
        });
    }

    @NotNull
    public String getName() {
        return "Thymeleaf fragment selector";
    }

    @NotNull
    public String getDescription() {
        return "Thymeleaf fragment selectors - some/demo :: fragment.";
    }

    @NotNull
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    public Icon getIcon() {
        return HbIcons.FILE_ICON;
    }
}
