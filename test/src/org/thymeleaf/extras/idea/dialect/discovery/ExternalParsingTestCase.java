package org.thymeleaf.extras.idea.dialect.discovery;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.testFramework.TestDataFile;
import com.intellij.testFramework.UsefulTestCase;
import junit.framework.TestCase;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.util.ThymeleafTestUtils;

import java.io.File;
import java.io.IOException;

public abstract class ExternalParsingTestCase extends TestCase {
    private String myFullDataPath;
    private String myFileExt;

    public ExternalParsingTestCase(@NonNls @NotNull String dataPath, @NotNull String fileExt) {
        myFullDataPath = getTestDataPath() + "/" + dataPath;
        myFileExt = fileExt;
    }

    /**
     * Returns the name of the test input data file corresponding to the current test.
     *
     * @param lowercaseFirstLetter Whether to lowercase the first letter of the test name or not.
     *                             As an exception, ALL_UPPERCASE test names are always taken as-is.
     * @return The name of the corresponding test file.
     */
    protected String getTestFileName(boolean lowercaseFirstLetter) {
        return getTestName(lowercaseFirstLetter) + myFileExt;
    }

    // From UsefulTestCase
    protected String getTestName(boolean lowercaseFirstLetter) {
        String name = getName();
        return UsefulTestCase.getTestName(name, lowercaseFirstLetter);
    }

    // From ParsingTestCase
    protected String getTestDataPath() {
        return ThymeleafTestUtils.BASE_TEST_DATA_PATH;
    }

    protected String loadFile(@NonNls @TestDataFile String name) throws IOException {
        return doLoadFile(myFullDataPath, name);
    }

    private static String doLoadFile(String myFullDataPath, String name) throws IOException {
        String text = FileUtil.loadFile(new File(myFullDataPath, name), CharsetToolkit.UTF8).trim();
        text = StringUtil.convertLineSeparators(text);
        return text;
    }
}
