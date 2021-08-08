package com.cierpich.blogio.validation.validationrules;

public class AlphabeticalCharactersRule implements ValidationRule<String> {

    @Override
    public boolean isValid(String value) {
        return value.matches("^[a-zA-Z]+$");
    }

    @Override
    public String getValidationErrorMessage() {
        return "Does not contain alphabetical characters only.";
    }

}
