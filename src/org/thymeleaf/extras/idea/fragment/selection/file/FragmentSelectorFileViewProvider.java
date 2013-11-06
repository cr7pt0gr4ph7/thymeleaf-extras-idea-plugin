package org.thymeleaf.extras.idea.fragment.selection.file;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.SingleRootFileViewProvider;
import org.thymeleaf.extras.idea.fragment.selection.FragmentSelectorLanguage;

public class FragmentSelectorFileViewProvider extends SingleRootFileViewProvider {

    private final PsiManager myManager;
    private final VirtualFile myFile;

    public FragmentSelectorFileViewProvider(PsiManager manager, VirtualFile file, boolean physical) {
        super(manager, file, physical, FragmentSelectorLanguage.INSTANCE);

        myManager = manager;
        myFile = file;
    }
}

