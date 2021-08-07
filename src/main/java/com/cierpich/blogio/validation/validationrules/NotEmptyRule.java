package com.cierpich.blogio.validation.validationrules;

public class NotEmptyRule implements ValidationRule<String>{
    @Override
    public boolean isValid(String value) {
        return !value.isEmpty();
    }

    @Override
    public String getValidationErrorMessage() {
        return "Must not be empty.";
    }
}
