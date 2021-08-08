package com.cierpich.blogio.validation.unitest;

import com.cierpich.blogio.validation.validationrules.AlphabeticalCharactersRule;
import com.cierpich.blogio.validation.validationrules.EmailFormatRule;
import com.cierpich.blogio.validation.validationrules.NotEmptyRule;
import com.cierpich.blogio.validation.validationrules.NotNullRule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("source_testIsNotEmpty")
    public void testIsNotEmpty(String value, boolean expected){
        assertThat(new NotEmptyRule().isValid(value)).isEqualTo(expected);
    }

    public static Stream<Arguments> source_testIsNotEmpty(){
        return Stream.of(
                Arguments.of("", false),
                Arguments.of(".", true),
                Arguments.of("NotEmpty", true)
        );
    }

    @ParameterizedTest
    @MethodSource("source_testIsNotNull")
    public void testIsNotNull(Object value, boolean expected){
        assertThat(new NotNullRule().isValid(value)).isEqualTo(expected);
    }

    public static Stream<Arguments> source_testIsNotNull(){
        return Stream.of(
                Arguments.of("", true),
                Arguments.of(".", true),
                Arguments.of(null, false),
                Arguments.of(123, true)
        );
    }

}
