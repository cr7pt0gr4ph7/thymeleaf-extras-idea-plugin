package org.thymeleaf.extras.idea.structure;

import org.thymeleaf.extras.idea.psi.HbBlockWrapper;
import org.thymeleaf.extras.idea.psi.HbMustache;
import org.thymeleaf.extras.idea.psi.HbPsiFile;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.TextEditorBasedStructureViewModel;
import org.jetbrains.annotations.NotNull;

class HbStructureViewModel extends TextEditorBasedStructureViewModel {

    private final HbPsiFile myFile;
    // classes which we construct structure view nodes for
    static final Class [] ourSuitableClasses = new Class[] { HbBlockWrapper.class, HbMustache.class };

    public HbStructureViewModel(@NotNull HbPsiFile psiFile) {
        super(psiFile);
        this.myFile = psiFile;
    }

    @NotNull
    @Override
    protected Class[] getSuitableClasses() {
        return ourSuitableClasses;
    }

    @NotNull
    @Override
    public StructureViewTreeElement getRoot() {
        return new HbTreeElementFile(myFile);
    }
}
