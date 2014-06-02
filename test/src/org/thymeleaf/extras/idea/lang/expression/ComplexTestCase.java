package org.thymeleaf.extras.idea.lang.expression;

public class ComplexTestCase extends AbstractExpressionParsingTestCase {
    public ComplexTestCase() {
        super("lang/expression/complex");
    }

    public void testVariable_01_multi_path() {
        doTest(true);
    }

    public void testVariable_02_in_addition(){
        doTest(true);
    }

    public void testVariable_03_with_addition_inside() {
        doTest(true);
    }

    public void testSelection_01_simple() {
        doTest(true);
    }

    public void testSelection_02_in_addition(){
        doTest(true);
    }

    public void testSelection_03_with_addition_inside() {
        doTest(true);
    }

    public void testSelection_04_with_array_inside() {
        doTest(true);
    }

    public void testCombined_01_with_selection_variable_and_add() {
        doTest(true);
    }
}
