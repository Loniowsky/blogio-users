package com.cierpich.blogio.validation.field;

import com.cierpich.blogio.validation.base.Validator;
import com.cierpich.blogio.validation.validationrules.ValidationRule;

import java.util.List;
import java.util.function.Supplier;

public abstract class SingleFieldValidator {

    public static <T> FieldValidationResult validate(String fieldName, FieldValueExtractor<T> extractor, List<ValidationRule<T>> validationRules) {
        var fieldValidationResult = new FieldValidationResult();
        fieldValidationResult.addFieldValidationErrors(fieldName, Validator.validate(extractor, validationRules).getErrorMessages());
        return fieldValidationResult;
    }

    public static <T> Supplier<FieldValidationResult> prepareValidation(String fieldName, FieldValueExtractor<T> extractor, List<ValidationRule<T>> validationRules) {
        return () -> validate(fieldName, extractor, validationRules);
    }

}
