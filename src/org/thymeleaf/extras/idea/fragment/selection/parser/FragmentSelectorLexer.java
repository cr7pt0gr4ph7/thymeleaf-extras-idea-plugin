package org.thymeleaf.extras.idea.fragment.selection.parser;

import com.intellij.lexer.FlexAdapter;

public class FragmentSelectorLexer extends FlexAdapter {
    public FragmentSelectorLexer() {
        super(new _FragmentSelectorLexer());
    }
}
