package org.thymeleaf.extras.idea.fragment.selection;

import com.intellij.lang.Language;

public class FragmentSelectorLanguage extends Language {
    public static final FragmentSelectorLanguage INSTANCE = new FragmentSelectorLanguage();

    public FragmentSelectorLanguage() {
        super("ThFragmentSelector", "application/x-thymeleaf-fragment-selector");
    }
}
