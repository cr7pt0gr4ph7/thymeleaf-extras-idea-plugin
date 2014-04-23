package org.thymeleaf.extras.idea.integration.spring;

import com.intellij.spring.model.CommonSpringBean;
import com.intellij.spring.model.SpringBeanPointer;
import com.intellij.spring.model.utils.SpringPropertyUtils;
import com.intellij.spring.model.xml.beans.SpringPropertyDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MySpringPropertyUtils {
    @Nullable
    public static CommonSpringBean getBeanForProperty(@NotNull CommonSpringBean bean, @NotNull String propertyName) {
        final SpringPropertyDefinition property = SpringPropertyUtils.findPropertyByName(bean, propertyName);
        if (property == null) return null;

        final SpringBeanPointer springBeanPointer = SpringPropertyUtils.findReferencedBean(property);
        if (springBeanPointer == null) return null;

        return springBeanPointer.getSpringBean();
    }
}
