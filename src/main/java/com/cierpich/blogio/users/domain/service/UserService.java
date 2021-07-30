package com.cierpich.blogio.users.domain.service;

import com.cierpich.blogio.users.domain.port.outgoing.UserRepository;
import com.cierpich.blogio.users.domain.BusinessRuleException;
import com.cierpich.blogio.users.domain.entity.User;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> (
                new BusinessRuleException(MessageFormat.format("User with id {0} does not exist", userId))
        ));
    }

    public void createUser(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(
                (presentUser) -> {
                    throw new BusinessRuleException(MessageFormat.format("User with email {0} already exists", presentUser.getEmail()));
                }
        );

        userRepository.save(user);
    }

    public void removeUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.delete(user);
        }
    }

    public void updateUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.update(user);
        } else {
            throw new BusinessRuleException(MessageFormat.format("User with id {0} does not exist", user.getId()));
        }
    }

    public void modifyReputation(UUID userId, int value) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessRuleException(MessageFormat.format("User with id {0} does not exist", userId)));
        user.modifyReputationNonNegative(value);
        userRepository.save(user);
    }

}
