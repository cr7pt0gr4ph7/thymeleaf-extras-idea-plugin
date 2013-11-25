package org.thymeleaf.extras.idea.dialect.dom;

import com.intellij.util.xml.DomFileDescription;
import org.thymeleaf.extras.idea.dialect.dom.model.Dialect;

public class DialectsDomFileDescription extends DomFileDescription<Dialect> {
    public DialectsDomFileDescription() {
        super(Dialect.class, "dialect", "http://www.thymeleaf.org/extras/dialect");
    }
}
