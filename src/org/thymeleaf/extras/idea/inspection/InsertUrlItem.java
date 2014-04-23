package org.thymeleaf.extras.idea.inspection;

public class InsertUrlItem {
    private final String myLookupString;
    private final String myPresentation;

    public InsertUrlItem(String lookupString, String presentation) {
        this.myLookupString = lookupString;
        this.myPresentation = presentation;
    }

    public String getPresentation() {
        return myPresentation;
    }

    public String getLookupString() {
        return myLookupString;
    }
}
