package org.thymeleaf.extras.idea.lang.expression;

import com.intellij.lang.InjectableLanguage;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThymeleafExpressionLanguage extends Language implements InjectableLanguage {
    public static final String ID = "ThEL";
    public static final String EXPRESSION_SUFFIX = "";
    public static final String EXPRESSION_PREFIX = "";
    public static final ThymeleafExpressionLanguage INSTANCE = new ThymeleafExpressionLanguage();

    private ThymeleafExpressionLanguage() {
        super(ID);

        SyntaxHighlighterFactory
                .LANGUAGE_FACTORY
                .addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
                    @NotNull
                    protected SyntaxHighlighter createHighlighter() {
                        return new ThymeleafExpressionHighlighter();
                    }
                });
    }

    @Nullable
    @Override
    public LanguageFileType getAssociatedFileType() {
        return ThymeleafExpressionFileType.INSTANCE;
    }
}
