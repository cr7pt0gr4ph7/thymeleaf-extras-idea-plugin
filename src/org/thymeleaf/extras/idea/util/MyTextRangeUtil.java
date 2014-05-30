package org.thymeleaf.extras.idea.util;

import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

public class MyTextRangeUtil {
    @NotNull
    public static TextRange makeRelativeTo(@NotNull TextRange parent, @NotNull TextRange child) {
        assert parent.contains(child) : String.format("parent=%s; child=%s", parent, child);
        return new TextRange(child.getStartOffset() - parent.getStartOffset(), child.getEndOffset() - parent.getStartOffset());
    }
}
