package org.thymeleaf.extras.idea.inspection;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.dialect.ThymeleafDefaultDialectsProvider;
import org.thymeleaf.extras.idea.editor.ThymeleafUtil;

import static com.intellij.patterns.XmlPatterns.*;

public class HardcodedResourceUrlInspection extends XmlSuppressableInspectionTool {
    private static final ElementPattern<XmlAttributeValue> SRC_ATTRIBUTE_PATTERN = xmlAttributeValue()
            .withParent(xmlAttribute("src"))
            .withSuperParent(2, xmlTag().andNot(xmlTag().withChild(xmlAttribute("src").withNamespace(ThymeleafDefaultDialectsProvider.STANDARD_DIALECT_URL))));

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, final boolean isOnTheFly) {
        // TODO This is always true currently
        final boolean isXmlFileWithThymeleafSupport = ThymeleafUtil.canBeThymeleafFile(holder.getFile().getFileType());

        return new XmlElementVisitor() {
            @Override
            public void visitXmlAttributeValue(XmlAttributeValue value) {
                if (!isXmlFileWithThymeleafSupport) {
                    return;
                }

                if (!SRC_ATTRIBUTE_PATTERN.accepts(value)) {
                    return;
                }

                XmlTag tag = PsiTreeUtil.getParentOfType(value, XmlTag.class);
                if (tag == null) return;

                PsiFile referencedFile = FileReferenceUtil.findFile(value);
                if (referencedFile == null) return;

                // This is a local file reference
                TextRange range = ElementManipulators.getValueTextRange(value);
                holder.registerProblem(value, range, "Use th:src attribute in addition to hardcoded URL");
            }
        };
    }
}
