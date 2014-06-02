package org.thymeleaf.extras.idea.util.xml;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.text.CharArrayUtil;
import com.intellij.util.xml.NanoXmlUtil;
import net.n3.nanoxml.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyNanoXmlUtil {
    private static final Logger LOG = Logger.getInstance("#org.thymeleaf.extras.idea.util.xml.MyNanoXmlUtil");

    @NotNull
    public static XmlNamespaceInfo parseRootNamespacesWithException(FileContent fileContent) throws IOException {
        return parseRootNamespacesWithException(fileContent.getContentAsText());
    }

    @NotNull
    public static XmlNamespaceInfo parseRootNamespacesWithException(CharSequence text) throws IOException {
        final RootNamespacesXmlBuilder builder = new RootNamespacesXmlBuilder();
        //noinspection IOResourceOpenedButNotSafelyClosed
        parseNamespaceInfo(new MyXMLReader(CharArrayUtil.readerFromCharSequence(text)), builder);
        return new XmlNamespaceInfo(builder.getRootNamespaces());
    }

    private static class RootNamespacesXmlBuilder extends NanoXmlUtil.IXMLBuilderAdapter implements IXMLBuilderEx {
        private static final String XMLNS = "xmlns";

        private final Map<String, String> rootNamespaces = new HashMap<String, String>();

        @Override
        public void addNamespace(String prefix, String nsURI) {
            rootNamespaces.put(prefix, nsURI);
        }

        public Map<String, String> getRootNamespaces() {
            return rootNamespaces;
        }
    }

    // TODO Default namespaces are not detected
    private static void parseNamespaceInfo(final IXMLReader r, final IXMLBuilder builder) {
        // TODO This is a hack: Use a custom IXMLParser to get the namespace information
        final IXMLParser parser = new MyXMLParser();
        parser.setReader(r);
        parser.setBuilder(builder);
        parser.setValidator(new NanoXmlUtil.EmptyValidator());
        parser.setResolver(new EmptyEntityResolver());
        try {
            parser.parse();
        } catch (XMLException e) {
            //noinspection ChainOfInstanceofChecks
            if (e.getException() instanceof NanoXmlUtil.ParserStoppedException) return;
            if (e.getException() instanceof NanoXmlUtil.ParserStoppedXmlException) return;
            LOG.debug(e);
        }
    }

    private interface IXMLBuilderEx {
        void addNamespace(String prefix, String nsURI);
    }

    private static class MyXMLParser extends StdXMLParser {
        @Override
        protected void processElement(String defaultNamespace, Properties namespaces) throws Exception {
            super.processElement(defaultNamespace, namespaces);

            if (getBuilder() instanceof IXMLBuilderEx) {
                for (Map.Entry<Object, Object> nsDecl : namespaces.entrySet()) {
                    ((IXMLBuilderEx) getBuilder()).addNamespace((String) nsDecl.getKey(), (String) nsDecl.getValue());
                }
            }
        }

        @Override
        protected void scanSomeTag(boolean allowCDATA, String defaultNamespace, Properties namespaces) throws Exception {
            super.scanSomeTag(allowCDATA, defaultNamespace, namespaces);
        }
    }

    private static class MyXMLReader extends StdXMLReader {
        private String publicId;
        private String systemId;

        public MyXMLReader(final Reader documentReader) {
            super(documentReader);
        }

        public MyXMLReader(InputStream stream) throws IOException {
            super(stream);
        }

        @Override
        public Reader openStream(String publicId, String systemId) throws IOException {
            this.publicId = StringUtil.isEmpty(publicId) ? null : publicId;
            this.systemId = StringUtil.isEmpty(systemId) ? null : systemId;

            return new StringReader(" ");
        }
    }

    private static class EmptyEntityResolver implements IXMLEntityResolver {
        @Override
        public void addInternalEntity(String name, String value) {
        }

        @Override
        public void addExternalEntity(String name, String publicID, String systemID) {
        }

        @Override
        public Reader getEntity(IXMLReader xmlReader, String name) throws XMLParseException {
            return new StringReader("");
        }

        @Override
        public boolean isExternalEntity(String name) {
            return false;
        }
    }
}
