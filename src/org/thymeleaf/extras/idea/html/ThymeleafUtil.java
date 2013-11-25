package org.thymeleaf.extras.idea.html;

import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.ide.highlighter.XHtmlFileType;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.annotations.NotNull;

public class ThymeleafUtil {
    public static boolean canBeThymeleafFile(@NotNull FileType fileType) {
        // TODO Don't hardcode this decision
        return (fileType == XmlFileType.INSTANCE || fileType == HtmlFileType.INSTANCE || fileType == XHtmlFileType.INSTANCE);
    }
}
