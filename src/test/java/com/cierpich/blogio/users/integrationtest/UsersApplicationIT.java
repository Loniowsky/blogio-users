package com.cierpich.blogio.users.integrationtest;


import com.cierpich.blogio.users.presentation.response.DeleteUserResponse;
import com.cierpich.blogio.users.presentation.response.ModifyReputationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: Add separation for tests e.g.: Transaction for each test
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@SuppressWarnings({"rawtypes", "unchecked"})
class UsersApplicationIT extends IntegrationTestWithDatabase {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final Supplier<String> endpoint = () -> "http://localhost:" + port + "/users";

    @Test
    public void canHandleCreateUserRequest() {
        HttpEntity<String> request = new HttpEntity<>(createUserRequestBody(), applicationJsonHeaders());
        ResponseEntity<UUID> response = testRestTemplate.postForEntity(endpoint.get(), request, UUID.class);
        UUID userId = response.getBody();
        ResponseEntity<Map> savedUserResponse = testRestTemplate.getForEntity(endpoint.get() + "/" + userId, Map.class);
        Map savedUser = savedUserResponse.getBody();

        assertThat(savedUser).isNotNull();
        assertThat(userId).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(savedUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(savedUser.get("id")).isEqualTo(userId.toString());
        assertThat(savedUser.get("firstName")).isEqualTo("Jarek");
        assertThat(savedUser.get("lastName")).isEqualTo("Ce");
        assertThat(savedUser.get("email")).isEqualTo("jarek@gmail.com");
        assertThat(savedUser.get("description")).isEqualTo("My awesome description");
        assertThat(savedUser.get("birthDate")).isEqualTo("1997-09-06");
        assertThat(savedUser.get("gender")).isEqualTo("MALE");
    }

    @Test
    public void canHandleDeleteUserRequest(){
        UUID id = testRestTemplate.postForEntity(endpoint.get(), new HttpEntity<>(createUserRequestBody(), applicationJsonHeaders()), UUID.class).getBody();

        DeleteUserResponse response = testRestTemplate.exchange(endpoint.get()+"/"+id, HttpMethod.DELETE, null, DeleteUserResponse.class).getBody();

        assertThat(response).isNotNull();
        assertThat(response.message).isEqualTo("Deleted");
        assertThat(response.id).isEqualTo(id);
        assertThat(testRestTemplate.getForEntity(endpoint.get()+"/"+id, Object.class).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void canModifyReputationToNonNegativeValues(){
        //WA: Cannot call PATCH method via SimpleClientHttpRequestFactory see: https://bugs.openjdk.java.net/browse/JDK-7016595
        RestTemplate restTemplate = new RestTemplateBuilder().requestFactory(HttpComponentsClientHttpRequestFactory.class).build();
        //ENDOF WA
        UUID id = testRestTemplate.postForEntity(endpoint.get(), new HttpEntity<>(new UserRequestBodyBuilder().withEmail("another@email.com").build(), applicationJsonHeaders()), UUID.class).getBody();
        assertThat(id).isNotNull();

        restTemplate.patchForObject(endpoint.get()+"/"+id+"/reputation", new HttpEntity<>("{\"value\":10}", applicationJsonHeaders()), ModifyReputationResponse.class);
        Map afterModification = testRestTemplate.getForEntity(endpoint.get() + "/" + id, Map.class).getBody();

        assertThat(afterModification).isNotNull();
        assertThat(afterModification.get("reputation")).isEqualTo(10);
    }

    @Test
    public void canUpdateUser(){
        UUID id = testRestTemplate.postForEntity(endpoint.get(), new HttpEntity<>(new UserRequestBodyBuilder().withEmail("yetAnotherEmail@email.com").build(), applicationJsonHeaders()), UUID.class).getBody();

        testRestTemplate.put(endpoint.get()+"/"+id, new HttpEntity<>(new UserRequestBodyBuilder().withEmail("changedEmail@email.com").withGender("FEMALE").build(), applicationJsonHeaders()));

        Map modifiedUser = testRestTemplate.getForEntity(endpoint.get()+"/"+id, Map.class).getBody();

        assertThat(modifiedUser).isNotNull();
        assertThat(modifiedUser.get("email")).isEqualTo("changedEmail@email.com");
        assertThat(modifiedUser.get("gender")).isEqualTo("FEMALE");
    }


    private String createUserRequestBody() {
        return new UserRequestBodyBuilder().build();
    }

    private HttpHeaders applicationJsonHeaders(){
        return new HttpHeaders(){{add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);}};
    }

}

class UserRequestBodyBuilder{
    private String firstName = "Jarek";
    private String lastName = "Ce";
    private String email = "jarek@gmail.com";
    private String description = "My awesome description";
    private String birthDate = "1997-09-06";
    private String gender = "MALE";

    public String build(){
        return "{\n" + MessageFormat.format("""
                    "firstName":"{0}",
                    "lastName":"{1}",
                    "email":"{2}",
                    "description":"{3}",
                    "birthDate":"{4}",
                    "gender":"{5}"
                """, firstName, lastName, email, description, birthDate, gender) + "}";
    }

    public UserRequestBodyBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserRequestBodyBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserRequestBodyBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRequestBodyBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public UserRequestBodyBuilder withBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserRequestBodyBuilder withGender(String gender) {
        this.gender = gender;
        return this;
    }

}
