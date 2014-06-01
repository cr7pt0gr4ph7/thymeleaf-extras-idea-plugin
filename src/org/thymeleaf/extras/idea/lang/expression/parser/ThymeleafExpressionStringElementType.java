package org.thymeleaf.extras.idea.lang.expression.parser;

import com.intellij.lang.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.jsp.el.ELLanguage;
import com.intellij.psi.jsp.ELElementType;
import com.intellij.psi.tree.ILazyParseableElementType;

public class ThymeleafExpressionStringElementType extends ILazyParseableElementType {
    public ThymeleafExpressionStringElementType(String debugName) {
        super(debugName, ELLanguage.INSTANCE);
    }

    @Override
    protected ASTNode doParseContents(ASTNode chameleon, PsiElement psi) {
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
