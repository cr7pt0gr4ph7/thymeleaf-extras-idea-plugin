package org.thymeleaf.extras.idea;

import com.intellij.lang.DependentLanguage;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import org.thymeleaf.extras.idea.parsing.HbParseDefinition;

public class HbInHtmlLanguage extends Language implements DependentLanguage {
    public static HbInHtmlLanguage INSTANCE = new HbInHtmlLanguage();

    protected HbInHtmlLanguage() {
        super(HbLanguage.INSTANCE, "Handlebars in Html");
    }

    {
        LanguageParserDefinitions.INSTANCE.addExplicitExtension(this, new HbParseDefinition());
    }
}
