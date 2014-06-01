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
    public ASTNode parseContents(ASTNode chameleon) {
        // TODO This is a hack: Use getTreeParent().getPsi() instead of getPsi() alone.
        PsiElement psi = chameleon.getTreeParent().getPsi();
        assert (psi != null) : chameleon;

        Project project = psi.getProject();
        PsiBuilder builder = PsiBuilderFactory.getInstance().createBuilder(project, chameleon);
        PsiParser parser = LanguageParserDefinitions.INSTANCE.forLanguage(getLanguage()).createParser(project);

        builder.putUserData(ELElementType.ourContextNodeKey, chameleon.getTreeParent());
        ASTNode result = parser.parse(this, builder).getFirstChildNode();
        builder.putUserData(ELElementType.ourContextNodeKey, null);
        return result;
    }
}
