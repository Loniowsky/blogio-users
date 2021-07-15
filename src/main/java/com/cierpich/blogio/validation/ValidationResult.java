package com.cierpich.blogio.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private List<String> errorMessages;

    public void addError(String errorMessage) {
        addErrors(List.of(errorMessage));
    }

    public void addErrors(Collection<String> errorMessages) {
        this.errorMessages.addAll(errorMessages);
    }

    public List<String> getErrorMessages() {
        return Collections.unmodifiableList(errorMessages);
    }

    public void merge(ValidationResult validationResult) {
        addErrors(validationResult.getErrorMessages());
    }

    public boolean isValid() {
        return errorMessages.isEmpty();
    }

}
