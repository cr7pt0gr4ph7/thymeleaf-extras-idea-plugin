package org.thymeleaf.extras.idea.lang.expression;

public class ArithmeticTestCase extends AbstractExpressionParsingTestCase {
    public ArithmeticTestCase() {
        super("lang/expression/core/arithmetic");
    }

    public void testAdd_01_with_tokens() {
        doTest(true);
    }

    public void testAdd_02_with_multiple_tokens() {
        doTest(true);
    }

    public void testAdd_03_with_tokens_and_string() {
        doTest(true);
    }

    public void testAdd_04_with_number_and_token() {
        doTest(true);
    }
}
