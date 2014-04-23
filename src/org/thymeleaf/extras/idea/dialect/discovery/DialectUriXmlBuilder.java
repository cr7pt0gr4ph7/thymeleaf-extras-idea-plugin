package org.thymeleaf.extras.idea.dialect.discovery;

import com.intellij.util.text.CharArrayUtil;
import com.intellij.util.xml.NanoXmlUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;

@NonNls
public class DialectUriXmlBuilder extends NanoXmlUtil.BaseXmlBuilder {
    private static final String DIALECT_TAG = "dialect";
    private static final String DIALECT_SCHEMA_URI = "http://www.thymeleaf.org/extras/dialect";
    private static final String NAMESPACE_URI_ATTR = "namespace-uri";
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

        myPrefixFound = false;
        myUriFound = false;
        myDialectFound = false;
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

        // NOTE If we have got something like:
        //        <nd:dialect xmlns:nd="http://example.org" prefix="abc">...</dialect>
        //      then nsURI is *null* for the prefix attribute!
        //      It is non-null for the nd:prefix attribute in the following case:
        //        <nd:dialect xmlns:cd="http://example.org" nd:prefix="abc">...</dialect>
        //      Note that the first case should be accepted, while the second one should be rejected.
        if (isInDialectTag() && (nsURI == null)) {
            if (PREFIX_ATTR.equals(key)) {
                myPrefix = value;
                myPrefixFound = true;
            } else if (NAMESPACE_URI_ATTR.equals(key)) {
                myUri = value;
                myUriFound = true;
            }
        }
    }

    @Override
    public void startElement(String name, String nsPrefix, String nsURI, String systemID, int lineNr)
            throws Exception {
        super.startElement(name, nsPrefix, nsURI, systemID, lineNr);

        // Is the root tag a <dialect/> tag with the appropriate namespace?
        if (isInDialectTag() && isDialectSchemaUri(nsURI)) {
            myDialectFound = true;
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

    private static boolean isDialectSchemaUri(@Nullable String nsUri) {
        return DIALECT_SCHEMA_URI.equals(nsUri);
    }

    private boolean isInDialectTag() {
        return DIALECT_LOCATION.equals(getLocation());
    }

    /**
     * Returns true if this is a dialect descriptor file.
     */
    public boolean isDialectDescriptor() {
        return myDialectFound;
    }

    /**
     * Whether a @namespace-uri attribute was found.
     */
    public boolean isUriFound() {
        return myUriFound;
    }

    /**
     * Whether a @prefix attribute was found.
     */
    public boolean isPrefixFound() {
        return myPrefixFound;
    }

    public String getUri() {
        return myUri;
    }

    public String getPrefix() {
        return myPrefix;
    }
}
