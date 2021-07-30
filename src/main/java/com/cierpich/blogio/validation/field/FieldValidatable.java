package com.cierpich.blogio.validation.field;

import java.util.List;
import java.util.function.Supplier;

public interface FieldValidatable {

    FieldValidationResult validate();
    default FieldValidationResult validate(List<Supplier<FieldValidationResult>> validationSuppliers){
        return validationSuppliers.stream().map(Supplier::get).reduce(new FieldValidationResult(), FieldValidationResult::merge);
    }

}
