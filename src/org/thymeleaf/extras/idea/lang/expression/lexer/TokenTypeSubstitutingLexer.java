package org.thymeleaf.extras.idea.lang.expression.lexer;

import com.intellij.lexer.DelegateLexer;
import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.NotNullFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * A lexer tht
 */
public class TokenTypeSubstitutingLexer extends DelegateLexer {
    private final NotNullFunction<IElementType, IElementType> mySubstitutionFunction;

    public TokenTypeSubstitutingLexer(final Lexer delegate, final Map<IElementType, IElementType> substitutionMap) {
        this(delegate, new NotNullFunction<IElementType, IElementType>() {
            @NotNull
            @Override
            @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
            public IElementType fun(@NotNull IElementType original) {
                final IElementType substituted = substitutionMap.get(original);
                return (substituted != null) ? substituted : original;
            }
        });
    }

    public TokenTypeSubstitutingLexer(final Lexer delegate, final NotNullFunction<IElementType, IElementType> substitutionFunction) {
        super(delegate);
        mySubstitutionFunction = substitutionFunction;
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        final IElementType original = super.getTokenType();
        return (original != null) ? mySubstitutionFunction.fun(original) : null;
    }
}
