package org.thymeleaf.extras.idea.lang.expression.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.LiteralTextEscaper;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.psi.GenericSelectionExpr;

// TODO Check GenericSelectionEscaper for correctness w.r.t. Thymeleaf specification
public class GenericSelectionEscaper extends LiteralTextEscaper<GenericSelectionExpr> {
    protected GenericSelectionEscaper(@NotNull GenericSelectionExpr host) {
        super(host);
    }

    @Override
    public boolean decode(@NotNull TextRange rangeInsideHost, @NotNull StringBuilder outChars) {
        TextRange.assertProperRange(rangeInsideHost);
        // We do not perform any encoding,
        // so it holds that inputString == outputString
        outChars.append(rangeInsideHost.substring(myHost.getText()));
        return true;
    }

    @Override
    public int getOffsetInHost(int offsetInDecoded, @NotNull TextRange rangeInsideHost) {
        // We do not perform any encoding,
        // so offsets in injected text directly map to host offsets
        return (offsetInDecoded >= 0 && offsetInDecoded < rangeInsideHost.getLength()) ? offsetInDecoded : -1;
    }

    @Override
    public boolean isOneLine() {
        // XML Attributes can contain newlines!
        return myHost.getText().contains("\n");
    }
}
