package org.thymeleaf.extras.idea.lang.expression;

public class ConditionalTestCase extends AbstractExpressionParsingTestCase {
    public ConditionalTestCase() {
        super("lang/expression/core/conditional");
    }

    public void testConditional01() {
        doTest(true);
    }
}
