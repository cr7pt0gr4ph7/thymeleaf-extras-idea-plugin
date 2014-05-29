package org.thymeleaf.extras.idea.lang.expression;

public class SimpleTestCase extends AbstractExpressionParsingTestCase {
    public SimpleTestCase() {
        super("lang/expression/core/simple");
    }

    public void testSimple01() {
        doTest(true);
    }
}
