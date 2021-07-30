package com.cierpich.blogio.users.presentation.controller;

import com.cierpich.blogio.users.domain.service.UserService;
import com.cierpich.blogio.users.presentation.request.CreateUserRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser(@RequestBody CreateUserRequest createUserRequest){
        createUserRequest.validate().throwIfNotValid();
        userService.createUser(createUserRequest.toUser());
    }

}
