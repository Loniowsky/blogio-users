package com.cierpich.blogio.validation.validationrules;

public class NotNullRule implements ValidationRule<Object>{

    @Override
    public boolean isValid(Object value) {
        return value!=null;
    }

    @Override
    public String getValidationErrorMessage() {
        return "Must not be null.";
    }
}
