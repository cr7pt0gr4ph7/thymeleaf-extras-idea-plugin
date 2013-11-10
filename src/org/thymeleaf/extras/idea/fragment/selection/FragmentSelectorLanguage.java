package org.thymeleaf.extras.idea.fragment.selection;

import com.intellij.lang.InjectableLanguage;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FragmentSelectorLanguage extends Language implements InjectableLanguage {
    public static final String ID = "ThFS";
    public static final String EXPRESSION_SUFFIX = "";
    public static final String EXPRESSION_PREFIX = "";
    public static final FragmentSelectorLanguage INSTANCE = new FragmentSelectorLanguage();

    private FragmentSelectorLanguage() {
        super(ID);

        SyntaxHighlighterFactory
                .LANGUAGE_FACTORY
                .addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
                    @NotNull
                    protected SyntaxHighlighter createHighlighter() {
                        return new FragmentSelectorHighlighter();
                    }
                });
    }

    @Nullable
    @Override
    public LanguageFileType getAssociatedFileType() {
        return FragmentSelectorFileType.INSTANCE;
    }
}
