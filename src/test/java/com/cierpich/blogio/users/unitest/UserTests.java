package com.cierpich.blogio.users.unitest;

import com.cierpich.blogio.users.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTests {

    @Test
    public void canCreateUser(){
        var sut = new User("Jarek", "Jarek", "jjarek@gmail.com", new User.Bio(LocalDate.of(1997, 9, 6), "I am the best programmer in the World!", User.Bio.Gender.HIDDEN));

        assertThat(sut.getClass()).isEqualTo(User.class);
    }

}
