package org.thymeleaf.extras.idea.inspection;

public class InsertUrlItem {
    private final String myLookupString;
    private final String myPresentation;

    public InsertUrlItem(String lookupString, String presentation) {
        myLookupString = lookupString;
        myPresentation = presentation;
    }

    public String getPresentation() {
        return myPresentation;
    }

    public String getLookupString() {
        return myLookupString;
    }
}
