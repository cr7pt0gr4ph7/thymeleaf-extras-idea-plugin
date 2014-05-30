package org.thymeleaf.extras.idea.lang.expression;

import com.intellij.testFramework.ParsingTestCase;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.util.ThymeleafTestUtils;

public abstract class AbstractExpressionParsingTestCase extends ParsingTestCase {
    protected AbstractExpressionParsingTestCase(@NonNls @NotNull String dataPath) {
        super(dataPath, "thel", true, new ThymeleafExpressionParserDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return ThymeleafTestUtils.BASE_TEST_DATA_PATH;
    }
}
