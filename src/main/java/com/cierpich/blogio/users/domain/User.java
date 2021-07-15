package com.cierpich.blogio.users.domain;

import com.cierpich.blogio.validation.ValidationResult;

import java.time.LocalDate;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private UserBio bio;

    private class UserBio {

        private String description;
        private LocalDate birthDate;
        private Gender gender;

        private enum Gender {
            MALE, FEMALE, HIDDEN
        }

    }

}
