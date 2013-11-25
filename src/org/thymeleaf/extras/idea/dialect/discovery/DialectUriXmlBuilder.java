package org.thymeleaf.extras.idea.dialect.discovery;

import com.intellij.util.text.CharArrayUtil;
import com.intellij.util.xml.NanoXmlUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;

public class DialectUriXmlBuilder extends NanoXmlUtil.BaseXmlBuilder {

    @NonNls
    private static final String DIALECT_TAG = "dialect";
    @NonNls
    private static final String DIALECT_SCHEMA_URI = "http://www.thymeleaf.org/extras/dialect";
    @NonNls
    private static final String NAMESPACE_URI_ATTR = "namespace-uri";
    @NonNls
    private static final String PREFIX_ATTR = "prefix";
    private static final String DIALECT_LOCATION = NanoXmlUtil.createLocation(DIALECT_TAG);
    private String myPrefix;
    private String myUri;
    private boolean myPrefixFound;
    private boolean myUriFound;
    private boolean myDialectFound;

    public DialectUriXmlBuilder() {
        myPrefix = "";
        myUri = "";
    }

    public static DialectUriXmlBuilder computeInfo(@NotNull final CharSequence text) {
        return computeInfo(CharArrayUtil.readerFromCharSequence(text));
    }

    public static DialectUriXmlBuilder computeInfo(@NotNull final Reader reader) {
        final DialectUriXmlBuilder builder = new DialectUriXmlBuilder();
        NanoXmlUtil.parse(reader, builder);
        return builder;
    }

    @Override
    public void addAttribute(String key, String nsPrefix, String nsURI, String value, String type) throws Exception {
        super.addAttribute(key, nsPrefix, nsURI, value, type);

        // Is the condition (nsUri == null) || isDialectSchemaUri(nsURI) correct?
        if (isInDialectTag() && ((nsURI == null) || isDialectSchemaUri(nsURI))) {
            if (PREFIX_ATTR.equals(key)) {
                myPrefix = value;
                myPrefixFound = true;
            } else if (NAMESPACE_URI_ATTR.equals(key)) {
                myUri = value;
                myUriFound = true;
            }
        }
    }

    public void startElement(String name, String nsPrefix, String nsURI, String systemID, int lineNr)
            throws Exception {
        super.startElement(name, nsPrefix, nsURI, systemID, lineNr);

        // Is the root tag a <dialect/> tag with the appropriate namespace?
        if (isInDialectTag() && isDialectSchemaUri(nsURI)) {
            this.myDialectFound = true;
        } else {
            stop();
        }
    }

    @Override
    public void elementAttributesProcessed(String name, String nsPrefix, String nsURI) throws Exception {
        if (isInDialectTag() && isDialectSchemaUri(nsURI)) {
            stopAfterDialectFound();
        }

        super.elementAttributesProcessed(name, nsPrefix, nsURI);
    }

    protected static void stopAfterDialectFound() throws Exception {
        stop();
    }

    private boolean isDialectSchemaUri(@Nullable String nsUri) {
        return DIALECT_SCHEMA_URI.equals(nsUri);
    }

    private boolean isInDialectTag() {
        return DIALECT_LOCATION.equals(getLocation());
    }

    /**
     * Returns true if this is a dialect descriptor file.
     */
    public boolean isDialectDescriptor() {
        return this.myDialectFound;
    }

    /**
     * Whether a @namespace-uri attribute was found.
     */
    public boolean isUriFound() {
        return this.myUriFound;
    }

    /**
     * Whether a @prefix attribute was found.
     */
    public boolean isPrefixFound() {
        return this.myPrefixFound;
    }

    public String getUri() {
        return this.myUri;
    }

    public String getPrefix() {
        return this.myPrefix;
    }
}
