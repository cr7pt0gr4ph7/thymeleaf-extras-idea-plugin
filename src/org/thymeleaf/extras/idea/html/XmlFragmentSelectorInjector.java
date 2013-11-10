package org.thymeleaf.extras.idea.html;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.XmlAttributeValuePattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider;
import org.thymeleaf.extras.idea.fragment.selection.FragmentSelectorLanguageInjector;

import java.util.Collections;
import java.util.List;

import static com.intellij.patterns.XmlPatterns.xmlAttribute;
import static com.intellij.patterns.XmlPatterns.xmlAttributeValue;
import static com.intellij.patterns.XmlPatterns.xmlTag;

public class XmlFragmentSelectorInjector implements MultiHostInjector, DumbAware {
    private static final ElementPattern<XmlAttributeValue> INCLUDE_ATTRIBUTE_PATTERN = xmlAttributeValue()
            .withLocalName("include", "substituteby")
            .withParent(xmlAttribute().withNamespace(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL));

    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement psiElement) {
        // TODO Are the restrictions to the filetypes XML, XHTML and HTML overly restrictive?
        final FileType fileType = psiElement.getContainingFile().getFileType();

        if (fileType != StdFileTypes.HTML && fileType != StdFileTypes.XML && fileType != StdFileTypes.XHTML) {
            return;
        }

        if (!psiElement.isValid() || !psiElement.getContainingFile().isValid()) {
            // Do not try to inject something into invalid elements, e.g. elements that have
            // already been removed from the internal tree, or that are members of a file that
            // has become invalid, i.e. has been removed.
            return;
        }

        if (INCLUDE_ATTRIBUTE_PATTERN.accepts(psiElement)) {
            FragmentSelectorLanguageInjector.injectElementWithPrefixSuffix(registrar, (PsiLanguageInjectionHost) psiElement);
        }
    }

    @NotNull
    @Override
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Collections.singletonList(XmlAttributeValue.class);
    }
}