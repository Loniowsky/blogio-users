package com.cierpich.blogio.users.domain;

public class BusinessRuleException extends RuntimeException{

    public BusinessRuleException(String message){
        super(message);
    }

}
