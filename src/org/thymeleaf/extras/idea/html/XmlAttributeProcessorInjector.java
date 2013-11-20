package org.thymeleaf.extras.idea.html;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PatternCondition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import com.intellij.xml.XmlAttributeDescriptor;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider;
import org.thymeleaf.extras.idea.lang.expression.ThymeleafExpressionLanguageInjector;
import org.thymeleaf.extras.idea.lang.fragment.selection.FragmentSelectorLanguageInjector;

import java.util.Collections;
import java.util.List;

import static com.intellij.patterns.XmlPatterns.xmlAttribute;
import static com.intellij.patterns.XmlPatterns.xmlAttributeValue;

public class XmlAttributeProcessorInjector implements MultiHostInjector, DumbAware {
    private static final ElementPattern<XmlAttribute> STANDARD_DIALECT_ATTR_PATTERN = xmlAttribute()
            .withNamespace(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL);
    // --
    private static final ElementPattern<XmlAttributeValue> INCLUDE_ATTRIBUTE_PATTERN = xmlAttributeValue()
            .withLocalName("include", "substituteby", "replace")
            .withParent(STANDARD_DIALECT_ATTR_PATTERN);
    // --
    private static final ElementPattern<XmlAttributeValue> ANY_THYMELEAF_ATTRIBUTE_PATTERN = xmlAttributeValue()
            .withParent(STANDARD_DIALECT_ATTR_PATTERN);
    // --
    private static final ElementPattern<XmlAttributeValue> ENUMERATED_ATTRIBUTE_PATTERN = xmlAttributeValue()
            .withParent(xmlAttribute().with(new PatternCondition<XmlAttribute>("EnumeratedXmlAttributeCondition") {
                @Override
                @SuppressWarnings("SimplifiableIfStatement")
                public boolean accepts(@NotNull XmlAttribute attribute, ProcessingContext processingContext) {
                    final XmlAttributeDescriptor descriptor = attribute.getDescriptor();
                    if (descriptor == null) return false;
                    return descriptor.isEnumerated();
                }
            }));

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

        if (ANY_THYMELEAF_ATTRIBUTE_PATTERN.accepts(psiElement)) {
            if (INCLUDE_ATTRIBUTE_PATTERN.accepts(psiElement)) {
                FragmentSelectorLanguageInjector.injectElementWithPrefixSuffix(registrar, (PsiLanguageInjectionHost) psiElement);
                return;
            }

            // HACK: Do not inject into enumerated attributes (e.g. th:inline)
            if (ENUMERATED_ATTRIBUTE_PATTERN.accepts(psiElement)) {
                return;
            }

            // Default action: Inject Thymeleaf Standard Expression Syntax into all other attributes
            //                 in the http://www.thymeleaf.org namespace.
            ThymeleafExpressionLanguageInjector.injectElementWithPrefixSuffix(registrar, (PsiLanguageInjectionHost) psiElement);
        }
    }

    @NotNull
    @Override
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Collections.singletonList(XmlAttributeValue.class);
    }
}