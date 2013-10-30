package org.thymeleaf.extras.idea.parsing;

import com.intellij.lexer.FlexAdapter;
import java.io.Reader;

public class HbLexer extends FlexAdapter {
    public HbLexer() {
        super(new _HbLexer((Reader) null));
    }

}