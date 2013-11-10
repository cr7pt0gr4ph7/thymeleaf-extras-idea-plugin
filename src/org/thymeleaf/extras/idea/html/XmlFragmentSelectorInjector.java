package org.thymeleaf.extras.idea.html;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.DumbAware;
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
    private static final XmlAttributeValuePattern THYMELEAF_TAG_ATTRIBUTE = xmlAttributeValue()
            .withParent(xmlAttribute().withNamespace(
                    ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL,
                    ThymeleafDefaultDialectsProvider.SPRING_STANDARD_DIALECT_URL,
                    ThymeleafDefaultDialectsProvider.SPRING_SECURITY_DIALECT_URL,
                    ThymeleafDefaultDialectsProvider.TILES_DIALECT_URL
            ));

    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement psiElement) {
        final FileType fileType = psiElement.getContainingFile().getFileType();

        // TODO Are the restrictions to the filetypes XML, XHTML and HTML to hard?
        if(fileType != StdFileTypes.HTML && fileType != StdFileTypes.XML && fileType != StdFileTypes.XHTML)

        if (psiElement instanceof XmlAttributeValue) {
            XmlAttributeValue attrValue = (XmlAttributeValue) psiElement;
            XmlAttribute parentAttr = (XmlAttribute) attrValue.getParent();

            if (ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL.equals(parentAttr.getNamespace()) && "include".equals(parentAttr.getLocalName())) {
                FragmentSelectorLanguageInjector.injectElementWithPrefixSuffix(registrar, registrar.startIn);
            }
        }
    }

    @NotNull
    @Override
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Collections.singletonList(XmlAttributeValue.class);
    }
}