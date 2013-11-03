package org.thymeleaf.extras.idea.dialect.xml2.converters;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.util.enumeration.ArrayEnumeration;
import com.intellij.util.io.PersistentStringEnumerator;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.converters.DelimitedListConverter;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.*;
import java.lang.String;

public class SpaceSeparatedListConverter extends DelimitedListConverter<String> {
    private static final String SEPARATOR = " ";

    public SpaceSeparatedListConverter() {
        super(SEPARATOR);
    }

    @Nullable
    @Override
    protected String convertString(@Nullable final String string, final ConvertContext context) {
        return string;
    }

    @Nullable
    @Override
    protected String toString(@Nullable final String string) {
        return string;
    }

    @Override
    protected Object[] getReferenceVariants(final ConvertContext context, final GenericDomValue<List<String>> value) {
        // TODO Implement SpaceSeparatedListConverter.getReferenceVariants() (if needed).
        return EMPTY_ARRAY;
    }

    @Nullable
    @Override
    protected PsiElement resolveReference(@Nullable final String string, final ConvertContext context) {
        return string == null ? null : context.getReferenceXmlElement();
    }

    @Override
    protected String getUnresolvedMessage(final String value) {
        // TODO Implement SpaceSeparatedListConverter.getUnresolvedMessage() correctly.
        return "Unresovled: " + value + " (inside SpaceSeparatedListConverter)";
    }
}
