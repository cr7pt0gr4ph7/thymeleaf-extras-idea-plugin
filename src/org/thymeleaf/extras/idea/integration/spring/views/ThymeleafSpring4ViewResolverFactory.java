package org.thymeleaf.extras.idea.integration.spring.views;

/**
 * Subclass of {@link org.thymeleaf.extras.idea.integration.spring.views.AbstractThymeleafViewResolverFactory}
 * that handles the view resolver of {@code thymeleaf-spring4}. All other relevant functionality is contained in the base class.
 */
public class ThymeleafSpring4ViewResolverFactory extends AbstractThymeleafViewResolverFactory {
    @Override
    protected String getBeanClass() {
        return "org.thymeleaf.spring4.view.ThymeleafViewResolver";
    }
}
