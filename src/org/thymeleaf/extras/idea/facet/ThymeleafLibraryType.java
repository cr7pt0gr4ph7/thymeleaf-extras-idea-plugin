package org.thymeleaf.extras.idea.facet;

import com.intellij.framework.library.DownloadableLibraryType;
import com.intellij.icons.AllIcons;

/**
 * Support for Thymeleaf library setup in project settings.
 */
public class ThymeleafLibraryType extends DownloadableLibraryType {

    public static final String LIB_CATEGORY_NAME = "Thymeleaf";
    public static final String LIB_TYPE_ID = "thymeleaf";
    public static final String GROUP_ID = "thymeleaf";

    public ThymeleafLibraryType() {
        // TODO Use a custom icon for ThymleafLibraryType (instead of AllIcons.FileTypes.Html
        super(LIB_CATEGORY_NAME, LIB_TYPE_ID, GROUP_ID, AllIcons.FileTypes.Html, ThymeleafLibraryType.class.getResource("thymeleaf.xml"));
    }

    @Override
    protected String[] getDetectionClassNames() {
        return new String[]{"org.thymeleaf.standard.StandardDialect"};
    }
}
