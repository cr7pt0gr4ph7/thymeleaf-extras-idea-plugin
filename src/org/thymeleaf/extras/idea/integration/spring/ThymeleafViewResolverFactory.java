package org.thymeleaf.extras.idea.integration.spring;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.spring.contexts.model.SpringModel;
import com.intellij.spring.model.CommonSpringBean;
import com.intellij.spring.model.SpringBeanPointer;
import com.intellij.spring.model.xml.beans.SpringBean;
import com.intellij.spring.model.xml.beans.SpringProperty;
import com.intellij.spring.model.xml.beans.SpringPropertyDefinition;
import com.intellij.spring.web.mvc.SpringMVCModel;
import com.intellij.spring.web.mvc.views.UrlBasedViewResolver;
import com.intellij.spring.web.mvc.views.ViewResolver;
import com.intellij.spring.web.mvc.views.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThymeleafViewResolverFactory extends ViewResolverFactory {
    @Override
    protected String getBeanClass() {
        return "org.thymeleaf.spring3.view.ThymeleafViewResolver";
    }

    @Override
    protected ViewResolver doCreate(CommonSpringBean bean, SpringModel model) {
        if (bean instanceof SpringBean) {
            // Get the property ThymeleafViewResolver/@templateEngine
            SpringBean templateEngine = getBeanForProperty((SpringBean) bean, "templateEngine");

            if (templateEngine != null) {
                SpringBean templateResolver = getBeanForProperty(templateEngine, "templateResolver");

                if (templateResolver != null) {
                    // TODO: Differentiate between the different TemplateResolvers / ResourceResolvers
                    return new ThymeleafViewResolver(templateResolver);
                }
            }
        }

        return null;
    }

    @Nullable
    private static SpringBean getBeanForProperty(@NotNull SpringBean bean, @NotNull String propertyName) {
        SpringPropertyDefinition property = bean.getProperty(propertyName);

        if (property != null) {
            if (property instanceof SpringProperty && ((SpringProperty) property).getBean().exists()) {
                return ((SpringProperty) property).getBean();
            } else {
                final SpringBeanPointer springBeanPointer = property.getRefElement().getValue();

                if (springBeanPointer != null) {
                    CommonSpringBean commonSpringBean = springBeanPointer.getSpringBean();

                    if (commonSpringBean instanceof SpringBean) {
                        return (SpringBean) commonSpringBean;
                    }
                }
            }
        }

        return null;
    }

    static class ThymeleafViewResolver extends UrlBasedViewResolver {
        public ThymeleafViewResolver(SpringBean bean) {
            super(bean);
        }

        protected ThymeleafViewResolver(String prefix, String suffix) {
            super(prefix, suffix);
        }

        @Override
        public PsiElement resolveView(String viewName, SpringMVCModel context) {
            return super.resolveView(viewName, context);
        }

        @Override
        public List<LookupElement> getAllViews(SpringMVCModel context) {
            return super.getAllViews(context);
        }

        @Override
        public String bindToElement(PsiElement element) {
            return super.bindToElement(element);
        }

        @NotNull
        @Override
        public String handleElementRename(String path) {
            return super.handleElementRename(path);
        }
    }
}