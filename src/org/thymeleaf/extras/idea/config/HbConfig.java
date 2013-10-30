package org.thymeleaf.extras.idea.config;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.lang.Language;

public class HbConfig {

    public static boolean isAutoGenerateCloseTagEnabled() {
        return getBooleanPropertyValue(Property.AUTO_GENERATE_CLOSE_TAG);
    }

    public static void setAutoGenerateCloseTagEnabled(boolean enabled) {
        setBooleanPropertyValue(Property.AUTO_GENERATE_CLOSE_TAG, enabled);
    }

    public static boolean isFormattingEnabled() {
        return getBooleanPropertyValue(Property.FORMATTER);
    }

    public static void setFormattingEnabled(boolean enabled) {
        setBooleanPropertyValue(Property.FORMATTER, enabled);
    }

    public static boolean isAutoCollapseBlocksEnabled() {
        return getBooleanPropertyValue(Property.AUTO_COLLAPSE_BLOCKS);
    }

    public static void setAutoCollapseBlocks(boolean enabled) {
        setBooleanPropertyValue(Property.AUTO_COLLAPSE_BLOCKS, enabled);
    }

    public static Language getCommenterLanguage() {
        return Language.findLanguageByID(getStringPropertyValue(Property.COMMENTER_LANGUAGE_ID));
    }

    public static void setCommenterLanguage(Language language) {
        if (language == null) {
            setStringPropertyValue(Property.COMMENTER_LANGUAGE_ID, null);
        } else {
            setStringPropertyValue(Property.COMMENTER_LANGUAGE_ID, language.getID());
        }
    }

    private static String getStringPropertyValue(Property property) {
        return new PropertyAccessor(PropertiesComponent.getInstance())
                .getPropertyValue(property);
    }

    private static void setStringPropertyValue(Property property, String value) {
        new PropertyAccessor(PropertiesComponent.getInstance())
                .setPropertyValue(property, value);
    }

    private static boolean getBooleanPropertyValue(Property property) {
        return Property.ENABLED.equals(getStringPropertyValue(property));
    }

    private static void setBooleanPropertyValue(Property property, boolean enabled) {
        setStringPropertyValue(property, enabled ? Property.ENABLED : Property.DISABLED);
    }
}
