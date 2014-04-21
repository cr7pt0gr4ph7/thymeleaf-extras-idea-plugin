package org.thymeleaf.extras.idea.dialect.discovery;

import com.intellij.testFramework.TestDataFile;
import com.intellij.testFramework.TestDataPath;
import com.intellij.util.text.CharArrayUtil;
import com.intellij.util.xml.NanoXmlUtil;
import org.junit.Assert;

import java.io.IOException;
import java.io.Reader;

@SuppressWarnings("InstanceMethodNamingConvention")
@TestDataPath("$CONTENT_ROOT/test/data")
public class DialectUriXmlBuilderTests extends ExternalParsingTestCase {
    private static String TEST_PREFIX = "TP";
    private static String TEST_NAMESPACE = "http://example.com/namespace-uri";

    // Actual tests

    public DialectUriXmlBuilderTests() {
        super("dialects/discovery", ".xml");
    }

    public void testCorrectDocument_IsOK() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(TEST_PREFIX);
        result.shouldHaveUri(TEST_NAMESPACE);
    }

    public void testCorrectDocumentWithoutXmlDeclaration_IsOK() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(TEST_PREFIX);
        result.shouldHaveUri(TEST_NAMESPACE);
    }

    public void testDuplicateAttributeLocalNameWithOtherNS_IsOK() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(TEST_PREFIX);
        result.shouldHaveUri(TEST_NAMESPACE);
    }

    public void testMalformedDocument_IsRejected() throws Exception {
        parseInputFile().shouldBeRejected();
    }

    public void testNonDefaultXmlnsPrefix_IsOK() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(TEST_PREFIX);
        result.shouldHaveUri(TEST_NAMESPACE);
    }

    public void testWithoutNamespace_IsRejected() throws Exception {
        parseInputFile().shouldBeRejected();
    }

    public void testWrongElementNamespace_IsRejected() throws Exception {
        parseInputFile().shouldBeRejected();
    }

    public void testWrongAttributeNamespace_IsRejected() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(null);
        result.shouldHaveUri(null);
    }

    public void testWrongNamespace_IsRejected() throws Exception {
        parseInputFile().shouldBeRejected();
    }

    public void testXmlnsPrefixOnAttributes_IsPartiallyOK() throws Exception {
        ParseResult result = parseInputFile();
        result.shouldBeAccepted();
        result.shouldHavePrefix(null);
        result.shouldHaveUri(null);
    }

    /**
     * Parse the file corresponding to the current test method.
     */
    private ParseResult parseInputFile() throws IOException {
        return parseFile(getTestFileName(false));
    }

    private ParseResult parseFile(@TestDataFile String fileName) throws IOException {
        final DialectUriXmlBuilder builder = new DialectUriXmlBuilder();
        final String inputString = loadFile(fileName);
        final Reader reader = CharArrayUtil.readerFromCharSequence(inputString);
        NanoXmlUtil.parse(reader, builder);
        return new ParseResult(builder);
    }

    @SuppressWarnings({"NonBooleanMethodNameMayNotStartWithQuestion", "FeatureEnvy"})
    private static class ParseResult {
        @SuppressWarnings("InstanceVariableOfConcreteClass")
        private final DialectUriXmlBuilder builder;

        public ParseResult(DialectUriXmlBuilder builder) {
            this.builder = builder;
        }

        public void shouldBeAccepted() {
            Assert.assertTrue("isDialectDescriptor() should be true", builder.isDialectDescriptor());
        }

        public void shouldBeRejected() {
            Assert.assertFalse("isDialectDescriptor() should be false", builder.isDialectDescriptor());
            shouldHavePrefix(null);
            shouldHaveUri(null);
        }

        public void shouldHavePrefix(String prefix) {
            if (prefix == null) {
                Assert.assertFalse("isPrefixFound() is not false", builder.isPrefixFound());
                Assert.assertNotNull("getPrefix() is null", builder.getPrefix());
                Assert.assertEquals("getPrefix() is not empty", "", builder.getPrefix());
            } else {
                Assert.assertTrue("should have a prefix", builder.isPrefixFound());
                Assert.assertNotNull("getPrefix() is null", builder.getPrefix());
                Assert.assertEquals("getPrefix() has wrong value", prefix, builder.getPrefix());
            }
        }

        public void shouldHaveUri(String namespaceUri) {
            if (namespaceUri == null) {
                Assert.assertFalse("isUriFound() is not false", builder.isUriFound());
                Assert.assertEquals("getUri() is not empty", "", builder.getUri());
            } else {
                Assert.assertTrue("isUriFound() is not true", builder.isUriFound());
                Assert.assertEquals("getUri() has wrong value", namespaceUri, builder.getUri());
            }
        }
    }
}
