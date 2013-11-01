package org.thymeleaf.extras.idea;

import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.ElementDescriptionLocation;
import com.intellij.psi.ElementDescriptionProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.FakePsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.usageView.UsageViewTypeLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class ThymeleafXmlDocumentationProvider implements DocumentationProvider, ElementDescriptionProvider {
    @Nullable
    @Override
    public String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        return getDoc(element, false);
    }

    @Nullable
    @Override
    public List<String> getUrlFor(PsiElement element, PsiElement originalElement) {
        if (element instanceof XmlAttribute) {
            XmlAttribute attr = (XmlAttribute) element;

            if ("http://www.thymeleaf.org".equals(attr.getNamespace()) && "inline".equals(attr.getLocalName())) {
                // TODO Provide a correct documentation url
                return Collections.singletonList("http://www.thymeleaf.org/doc/html/Using-Thymeleaf.html#inlining");
            }
        }

        return null;
    }

    @Nullable
    @Override
    public String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        return getDoc(element, true);
    }

    @Nullable
    @Override
    public PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object o, PsiElement element) {
        return null;
    }

    @Nullable
    @Override
    public PsiElement getDocumentationElementForLink(PsiManager psiManager, String s, PsiElement psiElement) {
        return null;
    }

    @Nullable
    @Override
    public String getElementDescription(@NotNull PsiElement element, @NotNull ElementDescriptionLocation location) {
        System.out.println("#" + element.toString());
        return getThymeleafElementDescription(element, location instanceof UsageViewTypeLocation ? DescKind.TYPE : DescKind.NAME, false);
    }

    @Nullable
    private static String getDoc(PsiElement element, boolean formatAsHtml) {
        if (element instanceof XmlAttribute) {
            XmlAttribute attr = (XmlAttribute) element;

            if ("http://www.thymeleaf.org".equals(attr.getNamespace()) && "inline".equals(attr.getLocalName())) {
                // TODO Provide a correct documentation string
                return "Documentation string for th:inline. TBD.";
            }
        }

        return null;
    }

    @Nullable
    private static String getThymeleafElementDescription(PsiElement e, DescKind kind, boolean html) {
        if (e == null) return null;

        if (e instanceof FakePsiElement) {
            // TODO Remove this debugging output
            System.out.println("!FakePsiElement discovered");

            return ((FakePsiElement) e).getPresentableText();
        }

        if (e instanceof XmlAttribute) {
            XmlAttribute attr = (XmlAttribute) e;

            if ("http://www.thymeleaf.org".equals(attr.getNamespace()) && "inline".equals(attr.getLocalName())) {
                String type = html ? "Property type" : "Property <b>type (html)</b>";
                String name = html ? "th:inline" : "th:<b>inline</b>";

                if (kind == DescKind.TYPE) return type;
                if (kind == DescKind.NAME) return name;

                if (kind == DescKind.TYPE_NAME_VALUE) {
                    String br = html ? "<br>" : "\n ";
                    String[] bold = html ? new String[]{"<b>", "</b>"} : new String[]{"", ""};
                    String valueSuffix = ": " + bold[0] + attr.getValue() + bold[1];
                    return type + br + name + valueSuffix;
                }

                // TODO Implement logging support properly
                System.err.println("unexpected desc kind: " + kind);
                return null;
            }
        }

        return null;
    }


    private enum DescKind {
        TYPE, NAME, TYPE_NAME_VALUE
    }
}
