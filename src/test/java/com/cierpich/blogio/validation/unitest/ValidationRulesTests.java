package com.cierpich.blogio.validation.unitest;

import com.cierpich.blogio.validation.validationrules.AlphabeticalCharactersRule;
import com.cierpich.blogio.validation.validationrules.EmailFormatRule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationRulesTests {

    @ParameterizedTest
    @CsvSource({"Jarek,true", "Ceee,true", "123asda,false", " sad story bro, false"})
    public void testIsContainingOnlyAlphabeticalCharacters(String toBeTested, boolean expected){
        assertThat(new AlphabeticalCharactersRule().isValid(toBeTested)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"email@email.com,true", "my_email_is_valid@gmail.com,true", "myEmailIsAlsoValid;),false"})
    public void testIsValidEmail(String email, boolean expected){
        assertThat(new EmailFormatRule().isValid(email)).isEqualTo(expected);
    }

}
