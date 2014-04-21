package org.thymeleaf.extras.idea.dialect.discovery;

import com.intellij.testFramework.TestDataFile;
import com.intellij.testFramework.TestDataPath;
import com.intellij.util.text.CharArrayUtil;
import com.intellij.util.xml.NanoXmlUtil;
import org.junit.Assert;

import java.io.IOException;
import java.io.Reader;

@TestDataPath("$CONTENT_ROOT/test/data")
public class DialectUriXmlBuilderTests extends ExternalParsingTestCase {
    private static String TEST_PREFIX = "TP";
    private static String TEST_NAMESPACE = "http://example.com/namespace-uri";

    // Actual tests

    public DialectUriXmlBuilderTests() {
        super("dialects/discovery", ".xml");
    }

    public void testCorrectDocument() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(TEST_PREFIX);
        result.shouldHaveUri(TEST_NAMESPACE);
    }

    public void testCorrectDocumentWithoutXmlDeclaration() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(TEST_PREFIX);
        result.shouldHaveUri(TEST_NAMESPACE);
    }

    public void testDuplicateAttributeLocalName() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(TEST_PREFIX);
        result.shouldHaveUri(TEST_NAMESPACE);
    }

    public void testMalformedDocument() throws Exception {
        parseInputFile().shouldBeRejected();
    }

    public void testNonDefaultNamespace() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(TEST_PREFIX);
        result.shouldHaveUri(TEST_NAMESPACE);
    }

    public void testNonDefaultNamespaceOnAttributes() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(null);
        result.shouldHaveUri(null);
    }

    public void testWithoutNamespace() throws Exception {
        parseInputFile().shouldBeRejected();
    }

    public void testWrongElementNamespace() throws Exception {
        parseInputFile().shouldBeRejected();
    }

    public void testWrongAttributeNamespace() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(TEST_PREFIX);
        result.shouldHaveUri(null);
    }

    public void testWrongNamespace() throws Exception {
        parseInputFile().shouldBeRejected();
    }

    /**
     * Parse the file corresponding to the current test method.
     */
    protected ParseResult parseInputFile() throws IOException {
        return parseFile(getTestFileName(false));
    }

    protected ParseResult parseFile(@TestDataFile String fileName) throws IOException {
        final DialectUriXmlBuilder builder = new DialectUriXmlBuilder();
        final String inputString = loadFile(fileName);
        final Reader reader = CharArrayUtil.readerFromCharSequence(inputString);
        NanoXmlUtil.parse(reader, builder);
        return new ParseResult(builder);
    }

    static class ParseResult {
        private DialectUriXmlBuilder builder;

        public ParseResult(DialectUriXmlBuilder builder) {
            this.builder = builder;
        }

        public void shouldBeAccepted() {
            Assert.assertEquals(true, builder.isDialectDescriptor());
        }

        public void shouldBeRejected() {
            Assert.assertEquals(false, builder.isDialectDescriptor());
            shouldHavePrefix(null);
            shouldHaveUri(null);
        }

        public void shouldHavePrefix(String prefix) {
            if (prefix == null) {
                Assert.assertFalse(builder.isPrefixFound());
                Assert.assertNotNull(builder.getPrefix());
                Assert.assertEquals("", builder.getPrefix());
            } else {
                Assert.assertTrue(builder.isPrefixFound());
                Assert.assertNotNull(builder.getPrefix());
                Assert.assertEquals(prefix, builder.getPrefix());
            }
        }

        public void shouldHaveUri(String namespaceUri) {
            if (namespaceUri == null) {
                Assert.assertFalse(builder.isUriFound());
                Assert.assertEquals("", builder.getUri());
            } else {
                Assert.assertTrue(builder.isUriFound());
                Assert.assertEquals(namespaceUri, builder.getUri());
            }
        }
    }
}
