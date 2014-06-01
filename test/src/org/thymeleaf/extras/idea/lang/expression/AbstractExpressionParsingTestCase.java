package org.thymeleaf.extras.idea.lang.expression;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.jsp.el.ELParserDefinition;
import com.intellij.psi.impl.source.jsp.el.impl.ELExpressionHolderImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.ParsingTestCase;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes;
import org.thymeleaf.extras.idea.util.ThymeleafTestUtils;

public abstract class AbstractExpressionParsingTestCase extends ParsingTestCase {
    protected AbstractExpressionParsingTestCase(@NonNls @NotNull String dataPath) {
        super(dataPath, "thel", true, new ThymeleafExpressionParserDefinition(), new DummyELParserDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return ThymeleafTestUtils.BASE_TEST_DATA_PATH;
    }

    private static class DummyELParserDefinition extends ELParserDefinition {
        @NotNull
        @Override
        public PsiElement createElement(ASTNode node) {
            final IElementType elementType = node.getElementType();
            if (elementType == ThymeleafExpressionElementTypes.EXPRESSION_STRING) {
                return new ELExpressionHolderImpl(node);
            }
            return super.createElement(node);
        }
    }
}
