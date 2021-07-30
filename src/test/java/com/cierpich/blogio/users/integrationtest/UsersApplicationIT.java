package com.cierpich.blogio.users.integrationtest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersApplicationIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void canHandleCreateUserRequest() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> request = new HttpEntity<>("""
                        {
                            "firstName":"Jarek",
                            "lastName":"Ce",
                            "email":"jarek@gmail.com",
                            "description":"My awesome description",
                            "birthDate":"1997-09-06",
                            "gender":"MALE"
                        }""", httpHeaders);
        ResponseEntity<String> response = this.testRestTemplate.postForEntity("http://localhost:"+port+"/users", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
