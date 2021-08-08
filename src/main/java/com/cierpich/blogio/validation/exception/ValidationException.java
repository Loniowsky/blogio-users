package com.cierpich.blogio.validation.exception;

import com.cierpich.blogio.validation.base.ValidationResult;

public class ValidationException extends RuntimeException{

    private ValidationResult validationResult;

    public ValidationException(ValidationResult validationResult){
        this.validationResult = validationResult;
    }

    public Object getValidationSummary() {
        return validationResult.getRaw();
    }
}
