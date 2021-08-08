package com.cierpich.blogio.users.domain.service;

import com.cierpich.blogio.users.domain.BusinessRuleException;
import com.cierpich.blogio.users.domain.entity.User;
import com.cierpich.blogio.users.domain.port.outgoing.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.UUID;

@Service
@Transactional
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

    public UUID createUser(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresentOrElse(
                        (presentUser) -> {
                            throw new BusinessRuleException(MessageFormat.format("User with email {0} already exists", presentUser.getEmail()));
                        },
                        () -> userRepository.save(user)
                );

        return user.getId();
    }

    public void deleteUser(UUID id) {
        User toBeDeleted = getUser(id);
        userRepository.delete(toBeDeleted);
    }

    public void updateUser(User user) {
        User actualUser = getUser(user.getId());
        user.setReputation(actualUser.getReputation());
        user.markNotNew();
        userRepository.update(user);
    }

    public void modifyReputation(UUID userId, int value) {
        User user = getUser(userId);
        user.modifyReputationNonNegative(value);
        userRepository.save(user);
    }

}
