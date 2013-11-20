package org.thymeleaf.extras.idea.lang.expression.parser;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.ThymeleafExpressionLanguage;

public class ThymeleafExpressionElementType extends IElementType {
    public ThymeleafExpressionElementType(@NotNull @NonNls String debugName) {
        super(debugName, ThymeleafExpressionLanguage.INSTANCE);
    }
}
