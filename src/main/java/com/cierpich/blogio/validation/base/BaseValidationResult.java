package com.cierpich.blogio.validation.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BaseValidationResult implements ValidationResult {

    private List<String> errorMessages = new ArrayList<>();

    public void addError(String errorMessage) {
        addErrors(List.of(errorMessage));
    }

    public void addErrors(Collection<String> errorMessages) {
        if(errorMessages.isEmpty()){return;}
        this.errorMessages.addAll(errorMessages);
    }

    public List<String> getErrorMessages() {
        return Collections.unmodifiableList(errorMessages);
    }

    public void merge(BaseValidationResult baseValidationResult) {
        addErrors(baseValidationResult.getErrorMessages());
    }

    public boolean isValid() {
        return errorMessages.isEmpty();
    }

    @Override
    public String getRaw() {
        return String.join("\n", getErrorMessages());
    }

    public static BaseValidationResult of(String... validationErrorMessages){
        return new BaseValidationResult(){{addErrors(List.of(validationErrorMessages));}};
    }

    public static BaseValidationResult of(Collection<String> validationErrorMessages){
        return new BaseValidationResult(){{addErrors(validationErrorMessages);}};
    }

}
