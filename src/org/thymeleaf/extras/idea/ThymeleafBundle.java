package org.thymeleaf.extras.idea;

import com.intellij.AbstractBundle;
import com.intellij.CommonBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

@SuppressWarnings({"Singleton", "StaticVariableOfConcreteClass", "VariableArgumentMethod"})
public class ThymeleafBundle extends AbstractBundle {
    @NonNls
    private static final String PATH_TO_BUNDLE = "resources.messages.ThymeleafBundle";

    private static final ThymeleafBundle ourInstance = new ThymeleafBundle();

    private ThymeleafBundle() {
        super(PATH_TO_BUNDLE);
    }

    public static String message(@PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key, Object... params) {
        return ourInstance.getMessage(key, params);
    }
}
