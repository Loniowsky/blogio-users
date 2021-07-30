package com.cierpich.blogio.validation.field;

import com.cierpich.blogio.validation.base.BaseValidationResult;
import com.cierpich.blogio.validation.base.ValidationResult;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FieldValidationResult implements ValidationResult {

    private final HashMap<String, BaseValidationResult> validationResultByFieldName = new HashMap<>();

    public boolean isValid(){
        return validationResultByFieldName.isEmpty();
    }

    public void addFieldValidationError(String fieldName, String errorMessage){
        addFieldValidationErrors(fieldName, List.of(errorMessage));
    }

    public void addFieldValidationErrors(String fieldName, List<String> fieldValidationErrors){
        if(fieldValidationErrors.isEmpty())return;
        validationResultByFieldName.putIfAbsent(fieldName, new BaseValidationResult());
        validationResultByFieldName.get(fieldName).addErrors(fieldValidationErrors);
    }

    public void addFieldsValidationErrors(Map<String, List<String>> errorMessagesByFieldName){
        errorMessagesByFieldName.forEach(this::addFieldValidationErrors);
    }

    public FieldValidationResult merge(FieldValidationResult fieldValidationResult){
        addFieldsValidationErrors(fieldValidationResult.getRaw());
        return this;
    }

    public Map<String, List<String>> getRaw(){
        return validationResultByFieldName.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().getErrorMessages()))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }


}
