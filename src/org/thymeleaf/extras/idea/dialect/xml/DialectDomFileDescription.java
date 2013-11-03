package org.thymeleaf.extras.idea.dialect.xml;

import com.intellij.util.xml.DomFileDescription;

public class DialectDomFileDescription extends DomFileDescription<Dialect> {
    public DialectDomFileDescription() {
        super(Dialect.class, "dialect", "http://www.thymeleaf.org/extras/dialect");
    }
}
