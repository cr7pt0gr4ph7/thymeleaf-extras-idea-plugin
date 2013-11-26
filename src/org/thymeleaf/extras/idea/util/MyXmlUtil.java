package org.thymeleaf.extras.idea.util;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.containers.BidirectionalMap;
import com.intellij.xml.util.XmlUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyXmlUtil {
    @NotNull
    public static String getLocalName(@NotNull String name) {
        return name.substring(name.indexOf(':') + 1);
    }

    // NOTE: One prefix can only be bound to one namespace (altough this binding can be overridden in nested elements),
    //       but one and the same namespace url can be bound to multiple prefixes!
    public static BidirectionalMap<String, String> knownPrefixNamespacePairs(XmlElement context) {
        final XmlTag tag = PsiTreeUtil.getParentOfType(context, XmlTag.class);

        if (tag != null) {
            final String[] knownNamespaces = tag.knownNamespaces();
            final BidirectionalMap<String, String> prefixNamespaceMap = new BidirectionalMap<String, String>();

            for (String namespace : knownNamespaces) {
                prefixNamespaceMap.put(tag.getPrefixByNamespace(namespace), namespace);
            }

            // // The following alternate implementation walks up the the context node's ancestor chain
            // // (via XmlTag.getParentTag()), and collects all xmlns:prefix="namespace" declarations on the way.
            //
            // // Walk up the ancestor chain
            // for (XmlTag p = tag; p != null; p = p.getParentTag()) {
            //     final Set<Map.Entry<String, String>> localPrefixes = p.getLocalNamespaceDeclarations().entrySet();
            //     // Collect all local namespace declarations
            //     for (Map.Entry<String, String> entry : localPrefixes) {
            //         if (!resultMap.containsKey(entry.getKey())) {
            //             resultMap.put(entry.getKey(), entry.getValue());
            //         }
            //     }
            // }

            return prefixNamespaceMap;
        } else {
            return new BidirectionalMap<String, String>();
        }
    }

    @NonNls
    @NotNull
    public static String buildQName(@Nullable String namespacePrefix, @NotNull String localName) {
        if (StringUtil.isEmpty(namespacePrefix)) return localName;
        else return namespacePrefix + ":" + localName;
    }
}
