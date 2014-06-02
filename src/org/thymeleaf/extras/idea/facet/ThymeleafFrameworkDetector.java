package org.thymeleaf.extras.idea.facet;

import com.intellij.facet.FacetType;
import com.intellij.framework.detection.FacetBasedFrameworkDetector;
import com.intellij.framework.detection.FileContentPattern;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PatternCondition;
import com.intellij.util.ProcessingContext;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.text.CharArrayUtil;
import com.intellij.util.xml.NanoXmlUtil;
import com.intellij.util.xml.XmlFileHeader;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider;
import org.thymeleaf.extras.idea.util.xml.XmlNamespaceInfo;

import java.io.IOException;

import static org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL;
import static org.thymeleaf.extras.idea.util.xml.MyNanoXmlUtil.parseRootNamespacesWithException;

/**
 * A framework detector for detecting the Thymeleaf framework-
 */
public class ThymeleafFrameworkDetector extends FacetBasedFrameworkDetector<ThymeleafFacet, ThymeleafFacetConfiguration> {

    public static final String DETECTOR_ID = "thymeleaf";

    public ThymeleafFrameworkDetector() {
        super(DETECTOR_ID);
    }

    @Override
    public FacetType<ThymeleafFacet, ThymeleafFacetConfiguration> getFacetType() {
        return ThymeleafFacetType.getInstance();
    }



    @NotNull
    @Override
    public FileType getFileType() {
        return StdFileTypes.HTML;
    }

    @NotNull
    @Override
    public ElementPattern<FileContent> createSuitableFilePattern() {
        return FileContentPattern.fileContent()
                .with(new PatternCondition<FileContent>("htmlFileWithThymeleafNamespace") {
                    @Override
                    public boolean accepts(@NotNull FileContent fileContent, ProcessingContext context) {
                        try {
                            final XmlNamespaceInfo nsInfo = parseRootNamespacesWithException(fileContent);
                            return nsInfo.getRootNamespaces().containsValue(STANDARD_DIALECT_URL);
                        } catch (IOException ignored) {
                            return false;
                        }
                    }
                });
    }
}
