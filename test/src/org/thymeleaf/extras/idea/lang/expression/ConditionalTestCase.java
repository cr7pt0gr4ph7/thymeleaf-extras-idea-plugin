package org.thymeleaf.extras.idea.lang.expression;

public class ConditionalTestCase extends AbstractExpressionParsingTestCase {
    public ConditionalTestCase() {
        super("lang/expression/core/conditional");
    }

    public void testConditional_01_with_tokens() {
        doTest(true);
    }

    public void testConditional_02_with_variable_expression() {
        doTest(true);
    }

    public void testSingleBranch_01_with_tokens() {
        doTest(true);
    }

    public void testDefaultExpr_01_with_tokens() {
        doTest(true);
    }
}
