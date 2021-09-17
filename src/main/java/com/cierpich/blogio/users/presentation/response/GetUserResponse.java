package com.cierpich.blogio.users.presentation.response;

import com.cierpich.blogio.users.domain.entity.User;

import java.time.LocalDate;
import java.util.UUID;

public record GetUserResponse (UUID id, String firstName, String lastName, LocalDate birthDate, String email, User.Bio.Gender gender, String description, int reputation){

    public static GetUserResponse ofUser(User user){
        return new GetUserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getEmail(), user.getGender(), user.getDescription(), user.getReputation());
    }

}
