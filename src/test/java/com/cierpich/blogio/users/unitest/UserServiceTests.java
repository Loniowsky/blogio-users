package com.cierpich.blogio.users.unitest;

import com.cierpich.blogio.users.UserBuilderMother;
import com.cierpich.blogio.users.data.InMemoryUserRepository;
import com.cierpich.blogio.users.domain.BusinessRuleException;
import com.cierpich.blogio.users.domain.entity.User;
import com.cierpich.blogio.users.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserServiceTests {

    private UserService sut;

    @BeforeEach
    void beforeAll() {
        sut = new UserService(new InMemoryUserRepository());
    }

    @Test
    public void canCreateUser() {
        var id = UUID.randomUUID();

        sut.createUser(new User(id, "Jarek", "Ce", "jarek@gmail.com", new User.Bio(LocalDate.of(1997, 9, 6), "My awesome description", User.Bio.Gender.MALE), 0));
        var result = sut.getUser(id);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getBirthDate()).isEqualTo(LocalDate.of(1997, 9, 6));
        assertThat(result.getDescription()).isEqualTo("My awesome description");
        assertThat(result.getEmail()).isEqualTo("jarek@gmail.com");
        assertThat(result.getFirstName()).isEqualTo("Jarek");
        assertThat(result.getLastName()).isEqualTo("Ce");
        assertThat(result.getReputation()).isEqualTo(0);
        assertThat(result.getGender()).isEqualTo(User.Bio.Gender.MALE);
    }

    @Test
    public void canDeleteUser() {
        var id = UUID.randomUUID();
        var user = UserBuilderMother.aTypicalUser().withId(id).build();

        sut.createUser(user);
        sut.deleteUser(id);

        assertThatThrownBy(() -> sut.getUser(id)).isInstanceOf(BusinessRuleException.class).hasMessage("User with id " + id + " does not exist");
    }

    @Test
    public void canUpdateUser() {
        var id = UUID.randomUUID();
        var builder = UserBuilderMother.aTypicalUser();
        var originalUser = builder.withId(id).build();
        var updatedUser = builder.withId(id).withDescription("New description").build();

        sut.createUser(originalUser);
        sut.updateUser(updatedUser);

        assertThat(sut.getUser(id).getDescription()).isEqualTo("New description");
    }

    @ParameterizedTest
    @MethodSource("source_canModifyReputation")
    public void canModifyReputation(List<Integer> modificationValues, int expectedReputationValue){
        var id = UUID.randomUUID();

        sut.createUser(UserBuilderMother.aTypicalUser().withId(id).build());
        modificationValues.forEach(value -> sut.modifyReputation(id, value));

        assertThat(sut.getUser(id).getReputation()).isEqualTo(expectedReputationValue);
    }

    public static Stream<Arguments> source_canModifyReputation(){
        return Stream.of(
                Arguments.of(List.of(5, 4, -12, 3, 4), 7),
                Arguments.of(List.of(-4, -3, 4), 4),
                Arguments.of(List.of(5, 4, -12), 0)
        );
    }

    @Test
    public void cannotCreateUserWithoutUniqueEmail(){
        var user1 = UserBuilderMother.aTypicalUser().withEmail("my@email.com").build();
        var user2 = UserBuilderMother.aTypicalUser().withEmail("my@email.com").build();

        sut.createUser(user1);

        assertThatThrownBy(() -> sut.createUser(user2)).isInstanceOf(BusinessRuleException.class).hasMessage("User with email my@email.com already exists");
    }

    @Test
    public void cannotGetNonExistingUser(){
        var id = UUID.randomUUID();
        assertThatThrownBy(() -> sut.getUser(id)).isInstanceOf(BusinessRuleException.class).hasMessage("User with id " + id + " does not exist");
    }

    @Test
    public void cannotUpdateNonExistingUser(){
        var id = UUID.randomUUID();
        var user = UserBuilderMother.aTypicalUser().withId(id).build();
        assertThatThrownBy(() -> sut.updateUser(user)).isInstanceOf(BusinessRuleException.class).hasMessage("User with id " + id + " does not exist");
    }

    @Test
    public void cannotModifyReputationOfNonExistingUser(){
        var id = UUID.randomUUID();
        assertThatThrownBy(() -> sut.modifyReputation(id, 10)).isInstanceOf(BusinessRuleException.class).hasMessage("User with id " + id + " does not exist");
    }

    @Test
    public void cannotDeleteNonExistingUser(){
        var id = UUID.randomUUID();
        assertThatThrownBy(() -> sut.deleteUser(id)).isInstanceOf(BusinessRuleException.class).hasMessage("User with id " + id + " does not exist");
    }

}
