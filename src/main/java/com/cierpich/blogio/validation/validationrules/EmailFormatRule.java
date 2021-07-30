package com.cierpich.blogio.validation.validationrules;

public class EmailFormatRule implements ValidationRule<String> {

    @Override
    public boolean isValid(String value) {
        return value.matches("^\\w+@\\w+\\.\\w+$");
    }

    @Override
    public String getValidationErrorMessage() {
        return "Invalid email format.";
    }

}
