package org.thymeleaf.extras.idea.fragment.selection.parser;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Distinct interface to distinguish the leaf elements we get from the lexer from the synthetic
 * composite elements we create in the parser
 */
class FragmentSelectorCompositeElementType extends IElementType {
    public FragmentSelectorCompositeElementType(@NotNull @NonNls String debugName) {
        super(debugName, null);
    }
}

