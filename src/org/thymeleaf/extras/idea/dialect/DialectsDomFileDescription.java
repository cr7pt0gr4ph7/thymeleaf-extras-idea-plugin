package org.thymeleaf.extras.idea.dialect;

import com.intellij.util.xml.DomFileDescription;
import org.thymeleaf.extras.idea.dialect.xml.Dialect;

public class DialectsDomFileDescription extends DomFileDescription<Dialect> {
    public DialectsDomFileDescription() {
        super(Dialect.class, "dialect", "http://www.thymeleaf.org/extras/dialect");
    }
}
