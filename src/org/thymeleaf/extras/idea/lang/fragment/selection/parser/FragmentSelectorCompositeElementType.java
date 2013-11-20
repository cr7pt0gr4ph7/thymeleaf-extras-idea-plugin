package org.thymeleaf.extras.idea.lang.fragment.selection.parser;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.fragment.selection.FragmentSelectorLanguage;

/**
 * Distinct interface to distinguish the leaf elements we get from the lexer from the synthetic
 * composite elements we create in the parser
 */
class FragmentSelectorCompositeElementType extends IElementType {
    public FragmentSelectorCompositeElementType(@NotNull @NonNls String debugName) {
        super(debugName, FragmentSelectorLanguage.INSTANCE);
    }
}

