package org.thymeleaf.extras.idea.lang.expression.parser.el;

import com.intellij.lang.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.jsp.el.ELContextProvider;
import com.intellij.psi.impl.source.jsp.el.ELLanguage;
import com.intellij.psi.jsp.ELElementType;
import com.intellij.psi.jsp.el.ELExpressionHolder;
import com.intellij.psi.tree.ILazyParseableElementType;
import com.intellij.spring.el.SpringELLanguage;
import com.intellij.spring.el.parser.SpringELElementType;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.extras.idea.integration.spring.el.ThymeleafELContextProvider;
import org.thymeleaf.extras.idea.lang.expression.ThymeleafExpressionLanguage;

/**
 * The element type used for the contents of {@code ${...}} and {@code *{...}}.
 */
public class ThymeleafExpressionStringElementType extends ILazyParseableElementType {
    public ThymeleafExpressionStringElementType(String debugName) {
        super(debugName, ThymeleafExpressionLanguage.INSTANCE);
    }

    @Override
    protected ASTNode doParseContents(@NotNull ASTNode chameleon, @NotNull PsiElement psi) {
        Project project = psi.getProject();
        PsiBuilder builder = PsiBuilderFactory.getInstance().createBuilder(project, chameleon);
        PsiParser parser = LanguageParserDefinitions.INSTANCE.forLanguage(getLanguageForParser(psi)).createParser(project);

        builder.putUserData(ELElementType.ourContextNodeKey, chameleon);
        ASTNode outer = parser.parse(this, builder);
        ASTNode result = outer.getFirstChildNode();
        builder.putUserData(ELElementType.ourContextNodeKey, null);

        // TODO This is a bad hack!
// final PsiElement parent = result.getPsi().getParent();
// if (parent != null) {
//     assert parent instanceof ELExpressionHolder;
//     parent.putUserData(ELContextProvider.ourContextProviderKey, new ThymeleafELContextProvider(parent));
// }
        return outer;
    }

    @Override
    protected Language getLanguageForParser(PsiElement psi) {
        // TODO Detect type of psi parent (SelectionExpr, VariableExpr)
        // TODO Remove dependency on Spring EL
        return  ELLanguage.INSTANCE;
    }
}
