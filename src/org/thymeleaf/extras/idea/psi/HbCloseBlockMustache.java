package org.thymeleaf.extras.idea.psi;

/**
 * Element for close block mustaches: "{{/foo}}"
 */
public interface HbCloseBlockMustache extends HbBlockMustache {

    @Override
    HbOpenBlockMustache getPairedElement();
}
