package org.thymeleaf.extras.idea.lang.expression.parser.el;

import com.intellij.lang.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.jsp.ELElementType;
import com.intellij.psi.tree.ILazyParseableElementType;
import com.intellij.spring.el.SpringELLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * The element type used for the contents of {@code ${...}} and {@code *{...}}.
 */
public class ThymeleafExpressionStringElementType extends ILazyParseableElementType {
    public ThymeleafExpressionStringElementType(String debugName) {
        super(debugName, SpringELLanguage.INSTANCE);
    }

    @Override
    protected ASTNode doParseContents(@NotNull ASTNode chameleon, @NotNull PsiElement psi) {
        Project project = psi.getProject();
        PsiBuilder builder = PsiBuilderFactory.getInstance().createBuilder(project, chameleon);
        PsiParser parser = LanguageParserDefinitions.INSTANCE.forLanguage(getLanguageForParser(psi)).createParser(project);

        builder.putUserData(ELElementType.ourContextNodeKey, chameleon.getTreeParent());
        ASTNode result = parser.parse(this, builder).getFirstChildNode();
        builder.putUserData(ELElementType.ourContextNodeKey, null);
        return result;
    }

    @Override
    protected Language getLanguageForParser(PsiElement psi) {
        // TODO Detect type of psi parent (SelectionExpr, VariableExpr)
        return super.getLanguageForParser(psi);
    }
}
