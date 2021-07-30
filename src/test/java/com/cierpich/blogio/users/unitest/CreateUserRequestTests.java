package com.cierpich.blogio.users.unitest;

import com.cierpich.blogio.users.domain.entity.User;
import com.cierpich.blogio.users.presentation.request.CreateUserRequest;
import com.cierpich.blogio.validation.validationrules.AlphabeticalCharactersRule;
import com.cierpich.blogio.validation.validationrules.EmailFormatRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserRequestTests {

    @Test
    public void canProperlyInvalidateWrongCreateUserRequest(){
        var sut = new CreateUserRequest("123", "ce", "emailemail.com", "description", LocalDate.of(1997, 9, 6), User.Bio.Gender.HIDDEN);

        var fieldValidationResult = sut.validate();

        assertThat(fieldValidationResult.isValid()).isFalse();
        assertThat(fieldValidationResult.getRaw().get("email")).containsExactlyInAnyOrder(new EmailFormatRule().getValidationErrorMessage());
        assertThat(fieldValidationResult.getRaw().get("firstName")).containsExactlyInAnyOrder(new AlphabeticalCharactersRule().getValidationErrorMessage());
    }

    @Test
    void canProperlyValidateValidCreateUserRequest(){
        var sut = new CreateUserRequest("Jarek", "ce", "email@email.com", "description", LocalDate.of(1997, 9, 6), User.Bio.Gender.HIDDEN);

        var fieldValidationResult = sut.validate();

        assertThat(fieldValidationResult.isValid()).isTrue();
    }


}
