package com.cierpich.blogio.users.presentation.response;

import java.util.UUID;

public class DeleteUserResponse {

    public String message = "Deleted";
    public UUID id;

    public DeleteUserResponse(){}

    public DeleteUserResponse(UUID id){
        this.id = id;
    }

}
