package com.cierpich.blogio.validation.base;

import com.cierpich.blogio.validation.field.FieldValueExtractor;
import com.cierpich.blogio.validation.validationrules.ValidationRule;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class Validator {

    public static <T> BaseValidationResult validate(FieldValueExtractor<T> extractor, List<ValidationRule<T>> validationRules) {
        var result = new BaseValidationResult();

        result.addErrors(
                validationRules.stream()
                        .filter(validationRule -> !validationRule.isValid(extractor.extract()))
                        .map(ValidationRule::getValidationErrorMessage).collect(Collectors.toList())
        );

        return result;
    }

    public static <T> Supplier<BaseValidationResult> prepareValidation(FieldValueExtractor<T> extractor, List<ValidationRule<T>> validationRules) {
        return () -> validate(extractor, validationRules);
    }

}
