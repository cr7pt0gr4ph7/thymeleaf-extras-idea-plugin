package org.thymeleaf.extras.idea.util.xml;

import com.intellij.testFramework.TestDataFile;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.collection.IsEmptyIterable;
import org.thymeleaf.extras.idea.dialect.discovery.DialectUriXmlBuilder;
import org.thymeleaf.extras.idea.dialect.discovery.ExternalParsingTestCase;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class MyNanoXmlUtilTests extends ExternalParsingTestCase {
    public MyNanoXmlUtilTests() {
        super("util/xml", ".xml");
    }

    public void testExplicitDefaultNS_NotSupported() throws Exception {
        final XmlNamespaceInfo result = parseInputFile();
        final Map<String, String> namespaces = result.getRootNamespaces();

        assertThat(namespaces.keySet(), hasSize(0));
        // assertThat(namespaces, hasEntry(null, "http://www.example.org/default/namespace"));
        // assertThat(namespaces.keySet(), hasSize(1));
    }

    public void testNonDefaultNS_IsOK() throws Exception {
        final XmlNamespaceInfo result = parseInputFile();
        final Map<String, String> namespaces = result.getRootNamespaces();

        assertThat(namespaces, hasEntry("nd", "http://www.example.org/non-default/namespace"));
        assertThat(namespaces.keySet(), hasSize(1));
    }

    /**
     * Parse the file corresponding to the current test method.
     */
    private XmlNamespaceInfo parseInputFile() throws IOException {
        return parseFile(getTestFileName(false));
    }

    private XmlNamespaceInfo parseFile(@TestDataFile String fileName) throws IOException {
        final DialectUriXmlBuilder builder = new DialectUriXmlBuilder();
        final String inputString = loadFile(fileName);
        return MyNanoXmlUtil.parseRootNamespacesWithException(inputString);
    }
}
