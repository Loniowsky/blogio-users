package com.cierpich.blogio.users;

import com.cierpich.blogio.users.domain.entity.User;

import java.time.LocalDate;

public class UserBuilderMother {

    public static User.Builder aTypicalUser(){
        return new User.Builder().withBirthDate(LocalDate.of(1990, 1, 1))
                .withDescription("My own description")
                .withEmail("my@email.com")
                .withFirstName("John")
                .withLastName("Snow")
                .withGender(User.Bio.Gender.MALE);
    }
}
