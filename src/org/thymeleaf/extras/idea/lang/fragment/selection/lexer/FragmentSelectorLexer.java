package org.thymeleaf.extras.idea.lang.fragment.selection.lexer;

import com.intellij.lexer.FlexAdapter;

public class FragmentSelectorLexer extends FlexAdapter {
    public FragmentSelectorLexer() {
        super(new _FragmentSelectorLexer());
    }
}
