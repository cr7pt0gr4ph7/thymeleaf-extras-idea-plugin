package org.thymeleaf.extras.idea.fragment.selection.parser;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.fragment.selection.FragmentSelectorLanguage;

public class FragmentSelectorElementType extends IElementType {
    public FragmentSelectorElementType(@NotNull @NonNls String debugName) {
        super(debugName, FragmentSelectorLanguage.INSTANCE);
    }
}
