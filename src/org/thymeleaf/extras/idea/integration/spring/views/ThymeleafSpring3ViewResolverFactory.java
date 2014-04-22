package org.thymeleaf.extras.idea.integration.spring.views;

/**
 * Subclass of {@link org.thymeleaf.extras.idea.integration.spring.views.AbstractThymeleafViewResolverFactory}
 * that handles the view resolver of {@code thymeleaf-spring3}. All other relevant functionality is contained in the base class.
 */
public class ThymeleafSpring3ViewResolverFactory extends AbstractThymeleafViewResolverFactory {
    @Override
    protected String getBeanClass() {
        return "org.thymeleaf.spring3.view.ThymeleafViewResolver";
    }
}
