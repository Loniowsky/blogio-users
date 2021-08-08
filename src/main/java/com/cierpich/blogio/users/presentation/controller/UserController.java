package com.cierpich.blogio.users.presentation.controller;

import com.cierpich.blogio.users.domain.entity.User;
import com.cierpich.blogio.users.domain.service.UserService;
import com.cierpich.blogio.users.presentation.request.CreateOrUpdateUserRequest;
import com.cierpich.blogio.users.presentation.request.ModifyReputationRequest;
import com.cierpich.blogio.users.presentation.response.DeleteUserResponse;
import com.cierpich.blogio.users.presentation.response.ModifyReputationResponse;
import com.cierpich.blogio.users.presentation.response.UpdateUserReponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UUID createUser(@RequestBody CreateOrUpdateUserRequest createUserRequest){
        createUserRequest.validate().throwIfNotValid();
        return userService.createUser(createUserRequest.toUser());
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable UUID id){
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public DeleteUserResponse deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return new DeleteUserResponse(id);
    }

    @PatchMapping("/{id}/reputation")
    public ModifyReputationResponse modifyReputation(@PathVariable UUID id, @RequestBody ModifyReputationRequest modifyReputationRequest){
        userService.modifyReputation(id, modifyReputationRequest.value);
        return new ModifyReputationResponse();
    }

    @PutMapping("/{id}")
    public UpdateUserReponse updateUser(@PathVariable UUID id, @RequestBody CreateOrUpdateUserRequest updateUserRequest){
        User user = updateUserRequest.toUserBuilder().withId(id).build();
        userService.updateUser(user);
        return new UpdateUserReponse();
    }

}
