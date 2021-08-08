package com.cierpich.blogio.validation.validationrules;

import java.util.function.Function;

public interface ValidationRule<T> {
    boolean isValid(T value);

    String getValidationErrorMessage();

    default Function<T, Boolean> asFunction(){
        return this::isValid;
    }
}
