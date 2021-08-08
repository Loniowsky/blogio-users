package com.cierpich.blogio.users.unitest;

import com.cierpich.blogio.users.domain.entity.User;
import com.cierpich.blogio.users.presentation.request.CreateOrUpdateUserRequest;
import com.cierpich.blogio.validation.validationrules.AlphabeticalCharactersRule;
import com.cierpich.blogio.validation.validationrules.EmailFormatRule;
import com.cierpich.blogio.validation.validationrules.NotEmptyRule;
import com.cierpich.blogio.validation.validationrules.NotNullRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrUpdateUserRequestTests {

    @Test
    public void canProperlyInvalidateWrongCreateUserRequest(){
        var sut = new CreateOrUpdateUserRequest("", "123", "emailemail.com", "", null, null);

        var fieldValidationResult = sut.validate();

        assertThat(fieldValidationResult.isValid()).isFalse();
        assertThat(fieldValidationResult.getRaw().get("email")).containsExactlyInAnyOrder(new EmailFormatRule().getValidationErrorMessage());
        assertThat(fieldValidationResult.getRaw().get("firstName")).containsExactlyInAnyOrder(new AlphabeticalCharactersRule().getValidationErrorMessage(), new NotEmptyRule().getValidationErrorMessage());
        assertThat(fieldValidationResult.getRaw().get("lastName")).containsExactlyInAnyOrder(new AlphabeticalCharactersRule().getValidationErrorMessage());
        assertThat(fieldValidationResult.getRaw().get("description")).containsExactlyInAnyOrder(new NotEmptyRule().getValidationErrorMessage());
        assertThat(fieldValidationResult.getRaw().get("birthDate")).containsExactlyInAnyOrder(new NotNullRule().getValidationErrorMessage());
        assertThat(fieldValidationResult.getRaw().get("gender")).containsExactlyInAnyOrder(new NotNullRule().getValidationErrorMessage());
    }

    @Test
    void canProperlyValidateValidCreateUserRequest(){
        var sut = new CreateOrUpdateUserRequest("Jarek", "ce", "email@email.com", "description", LocalDate.of(1997, 9, 6), User.Bio.Gender.HIDDEN);

        var fieldValidationResult = sut.validate();

        assertThat(fieldValidationResult.isValid()).isTrue();
    }


}
