package org.thymeleaf.extras.idea.facet;

import com.intellij.facet.FacetConfiguration;
import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.facet.ui.FacetValidatorsManager;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.ModificationTracker;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class ThymeleafFacetConfiguration implements FacetConfiguration, ModificationTracker, Disposable {
    @Override
    public void dispose() {

    }

    @Override
    public FacetEditorTab[] createEditorTabs(FacetEditorContext editorContext, FacetValidatorsManager validatorsManager) {
        return new FacetEditorTab[0];
    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {

    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {

    }

    @Override
    public long getModificationCount() {
        return 0;
    }
}
