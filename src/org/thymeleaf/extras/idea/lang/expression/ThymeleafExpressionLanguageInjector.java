/**
 * Based on https://github.com/JetBrains/intellij-plugins/blob/4ad7738567e61a7ca6126be590644ba3383f3400/struts2/ognl/src/com/intellij/lang/ognl/OgnlLanguageInjector.java
 */

package org.thymeleaf.extras.idea.lang.expression;

import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiLanguageInjectionHost;

public class ThymeleafExpressionLanguageInjector {

    private final MultiHostRegistrar registrar;
    private final PsiLanguageInjectionHost element;
    private boolean addPrefixSuffix;

    private ThymeleafExpressionLanguageInjector(final MultiHostRegistrar registrar,
                                                final PsiLanguageInjectionHost element) {
        this.registrar = registrar;
        this.element = element;
    }

    public static void injectElementWithPrefixSuffix(final MultiHostRegistrar registrar,
                                                     final PsiLanguageInjectionHost element) {
        new ThymeleafExpressionLanguageInjector(registrar, element)
                .addPrefixSuffix()
                .injectWholeXmlAttributeValue();
    }

    public static void injectOccurrences(final MultiHostRegistrar registrar,
                                         final PsiLanguageInjectionHost element) {
        new ThymeleafExpressionLanguageInjector(registrar, element).injectOccurrences();
    }

    public ThymeleafExpressionLanguageInjector addPrefixSuffix() {
        this.addPrefixSuffix = true;
        return this;
    }

    private void injectWholeXmlAttributeValue() {
        final int textLength = element.getTextLength();
        if (textLength < 2) {
            return;
        }

        // We leave off one character at the start and at the end of the attribute value
        // token that we are injecting into because the quotes around the attribute value
        // are also included in the token, but we want to ignore them. Example:
        //
        //   "fragment_selector"
        //   +-----------------+ => Attribute value (including quotes)
        //    +---------------+  => Fragment selector expression (excluding quotes)
        //
        final TextRange range = new TextRange(1, textLength - 1);
        registrar.startInjecting(ThymeleafExpressionLanguage.INSTANCE)
                .addPlace(addPrefixSuffix ? ThymeleafExpressionLanguage.EXPRESSION_PREFIX : null,
                        addPrefixSuffix ? ThymeleafExpressionLanguage.EXPRESSION_SUFFIX : null,
                        element,
                        range)
                .doneInjecting();
    }

    private void injectOccurrences() {
        registrar.startInjecting(ThymeleafExpressionLanguage.INSTANCE);

        final String text = element.getText();
        final int textLength = text.length() - 1;
        final int lastStartPosition = Math.max(textLength, text.lastIndexOf(ThymeleafExpressionLanguage.EXPRESSION_SUFFIX));

        int startOffset = 0;
        while (startOffset < lastStartPosition) {
            startOffset = text.indexOf(ThymeleafExpressionLanguage.EXPRESSION_PREFIX, startOffset);
            if (startOffset == -1) {
                break;
            }

            // search closing '}' from text end/next expr start backwards to support sequence expressions
            final int nextStartOffset = text.indexOf(ThymeleafExpressionLanguage.EXPRESSION_PREFIX,
                    startOffset + ThymeleafExpressionLanguage.EXPRESSION_PREFIX.length());
            final int searchClosingBraceIdx = nextStartOffset != -1 ? nextStartOffset : textLength;
            final int closingBraceIdx = text.lastIndexOf(ThymeleafExpressionLanguage.EXPRESSION_SUFFIX, searchClosingBraceIdx);
            final int length = (closingBraceIdx != -1 && closingBraceIdx > nextStartOffset ? closingBraceIdx + 1 : textLength) - startOffset;
            final TextRange range = TextRange.from(startOffset, length);
            registrar.addPlace(null, null, element, range);
            startOffset += length;
        }

        registrar.doneInjecting();
    }
}
