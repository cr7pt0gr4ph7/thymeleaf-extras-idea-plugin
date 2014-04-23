package org.thymeleaf.extras.idea.integration.spring.views;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiClass;
import com.intellij.psi.util.InheritanceUtil;
import com.intellij.spring.contexts.model.SpringModel;
import com.intellij.spring.model.CommonSpringBean;
import com.intellij.spring.web.mvc.views.UrlBasedViewResolver;
import com.intellij.spring.web.mvc.views.ViewResolver;
import com.intellij.spring.web.mvc.views.ViewResolverFactory;
import org.jetbrains.annotations.Nullable;

import static org.thymeleaf.extras.idea.integration.spring.MySpringPropertyUtils.getBeanForProperty;

/**
 * Base classes for the view resolvers for {@code thymeleaf-spring3} and {@code thymeleaf-spring4}.
 *
 * @see org.thymeleaf.extras.idea.integration.spring.views.ThymeleafSpring3ViewResolverFactory
 * @see org.thymeleaf.extras.idea.integration.spring.views.ThymeleafSpring4ViewResolverFactory
 */
public class ThymeleafViewResolverFactory extends ViewResolverFactory {
    private static final Logger LOGGER = Logger.getInstance(ThymeleafViewResolverFactory.class);

    private static final String THYMELEAF_SPRING_3_VIEW_RESOLVER = "org.thymeleaf.spring3.view.ThymeleafViewResolver";
    private static final String THYMELEAF_SPRING_4_VIEW_RESOLVER = "org.thymeleaf.spring4.view.ThymeleafViewResolver";

    @Override
    protected boolean isMine(CommonSpringBean bean, PsiClass beanClass) {
        return isThymeleafViewResolver(beanClass);
    }

    private static boolean isThymeleafViewResolver(PsiClass beanClass) {
        return InheritanceUtil.isInheritor(beanClass, THYMELEAF_SPRING_3_VIEW_RESOLVER)
                || InheritanceUtil.isInheritor(beanClass, THYMELEAF_SPRING_4_VIEW_RESOLVER);
    }

    @Override
    protected String getBeanClass() {
        LOGGER.warn("getBeanClass() should not be called because it always returns null.");
        return null;
    }

    @Nullable
    @Override
    protected ViewResolver doCreate(CommonSpringBean bean, SpringModel model) {
        CommonSpringBean templateEngine = getBeanForProperty(bean, "templateEngine");
        if (templateEngine == null) return null;

        CommonSpringBean templateResolver = getBeanForProperty(templateEngine, "templateResolver");
        if (templateResolver == null) return null;

        return new ThymeleafViewResolver(templateResolver);
    }

    private static class ThymeleafViewResolver extends UrlBasedViewResolver {
        private ThymeleafViewResolver(CommonSpringBean bean) {
            super(bean);
        }

        protected ThymeleafViewResolver(String prefix, String suffix) {
            super(prefix, suffix);
        }
    }
}