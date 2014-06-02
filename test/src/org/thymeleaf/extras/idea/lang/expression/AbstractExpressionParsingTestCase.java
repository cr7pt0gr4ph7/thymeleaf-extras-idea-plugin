package org.thymeleaf.extras.idea.lang.expression;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.jsp.el.ELParserDefinition;
import com.intellij.psi.impl.source.jsp.el.impl.ELExpressionHolderImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.spring.el.parser.SpringELParserDefinition;
import com.intellij.testFramework.ParsingTestCase;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.lang.expression.parser.ThymeleafExpressionElementTypes;
import org.thymeleaf.extras.idea.util.ThymeleafTestUtils;

public abstract class AbstractExpressionParsingTestCase extends ParsingTestCase {
    protected AbstractExpressionParsingTestCase(@NonNls @NotNull String dataPath) {
        super(dataPath, "thel", true, new ThymeleafExpressionParserDefinition(), new SpringELParserDefinition(), new ELParserDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return ThymeleafTestUtils.BASE_TEST_DATA_PATH;
    }
}
