package com.cierpich.blogio.validation.base;

import com.cierpich.blogio.validation.exception.ValidationException;

public interface ValidationResult {

    boolean isValid();
    Object getRaw();
    default void throwIfNotValid(){
        if(!isValid())throw new ValidationException(this);
    }

}
