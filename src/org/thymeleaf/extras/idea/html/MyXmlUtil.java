package org.thymeleaf.extras.idea.html;

import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.containers.BidirectionalMap;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class MyXmlUtil {
    @NotNull
    public static String getLocalName(@NotNull String name) {
        return name.substring(name.indexOf(':') + 1);
    }

    // NOTE: One prefix can only be bound to one namespace (altough this binding can be overridden in nested elements),
    //       but one and the same namespace url can be bound to multiple prefixes!
    public static BidirectionalMap<String, String> knownPrefixNamespacePairs(XmlElement context) {
        // This function walks up the the context node's ancestor chain (via XmlTag.getParentTag()),
        // and collects all xmlns:prefix="namespace" declarations on the way
        final XmlTag tag = PsiTreeUtil.getParentOfType(context, XmlTag.class);

        if (tag != null) {
            final BidirectionalMap<String, String> resultMap = new BidirectionalMap<String, String>();
            // Walk up the ancestor chain
            for (XmlTag p = tag; p != null; p = p.getParentTag()) {
                final Set<Map.Entry<String, String>> localPrefixes = p.getLocalNamespaceDeclarations().entrySet();
                // Collect all local namespace declarations
                for (Map.Entry<String, String> entry : localPrefixes) {
                    if (!resultMap.containsKey(entry.getKey())) {
                        resultMap.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            return resultMap;
        } else {
            return new BidirectionalMap<String, String>();
        }
    }
}
