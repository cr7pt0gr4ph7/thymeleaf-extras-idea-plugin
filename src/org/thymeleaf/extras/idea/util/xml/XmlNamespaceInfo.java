package org.thymeleaf.extras.idea.util.xml;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class XmlNamespaceInfo {
    private final Map<String, String> rootNamespaces;

    public XmlNamespaceInfo(@NotNull Map<String, String> rootNamespaces) {
        // TODO Do we need to copy the parameter map?
        this.rootNamespaces = rootNamespaces;
    }

    public Map<String, String> getRootNamespaces() {
        return this.rootNamespaces;
    }
}
