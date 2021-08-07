package com.cierpich.blogio.users.presentation.request;

import com.cierpich.blogio.users.domain.entity.User;
import com.cierpich.blogio.validation.field.FieldValidatable;
import com.cierpich.blogio.validation.field.FieldValidationResult;
import com.cierpich.blogio.validation.field.SingleFieldValidator;
import com.cierpich.blogio.validation.validationrules.AlphabeticalCharactersRule;
import com.cierpich.blogio.validation.validationrules.EmailFormatRule;
import com.cierpich.blogio.validation.validationrules.NotEmptyRule;
import com.cierpich.blogio.validation.validationrules.NotNullRule;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;


public class CreateOrUpdateUserRequest implements FieldValidatable {

    public String firstName;
    public String lastName;
    public String email;
    public String description;
    public LocalDate birthDate;
    public User.Bio.Gender gender;

    private final List<Supplier<FieldValidationResult>> validationSuppliers = List.of(
            SingleFieldValidator.prepareValidation("firstName", () -> this.firstName, List.of(
                    new AlphabeticalCharactersRule(),
                    new NotEmptyRule()
            )),
            SingleFieldValidator.prepareValidation("lastName", () -> this.lastName, List.of(
                    new AlphabeticalCharactersRule(),
                    new NotEmptyRule()
            )),
            SingleFieldValidator.prepareValidation("email", () -> this.email, List.of(
                    new EmailFormatRule(),
                    new NotEmptyRule()
            )),
            SingleFieldValidator.prepareValidation("description", () -> this.description, List.of(
                    new NotEmptyRule()
            )),
            SingleFieldValidator.prepareValidation("birthDate", () -> this.birthDate, List.of(
                    new NotNullRule()
            )),
            SingleFieldValidator.prepareValidation("gender", () -> this.gender, List.of(
                    new NotNullRule()
            ))
    );

    public CreateOrUpdateUserRequest(String firstName, String lastName, String email, String description, LocalDate birthDate, User.Bio.Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    @Override
    public FieldValidationResult validate() {
        return validate(validationSuppliers);
    }

    public User toUser() {
        return toUserBuilder().build();
    }

    public User.Builder toUserBuilder() {
        return new User.Builder().withGender(gender).withEmail(email).withLastName(lastName)
                .withFirstName(firstName).withDescription(description).withBirthDate(birthDate);
    }

}