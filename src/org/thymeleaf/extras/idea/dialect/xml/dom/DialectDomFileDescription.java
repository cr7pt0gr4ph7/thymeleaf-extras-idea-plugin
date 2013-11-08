package org.thymeleaf.extras.idea.dialect.xml.dom;

import com.intellij.util.xml.DomFileDescription;
import org.thymeleaf.extras.idea.dialect.xml.Dialect;

public class DialectDomFileDescription extends DomFileDescription<Dialect> {
    public DialectDomFileDescription() {
        super(Dialect.class, "dialect", "http://www.thymeleaf.org/extras/dialect");
    }
}
