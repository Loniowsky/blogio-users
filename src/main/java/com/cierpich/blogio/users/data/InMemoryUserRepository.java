package com.cierpich.blogio.users.data;

import com.cierpich.blogio.users.domain.port.outgoing.UserRepository;
import com.cierpich.blogio.users.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final HashMap<UUID, User> userByIds = new HashMap<>();

    @Override
    public void save(User user) {
        userByIds.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        userByIds.remove(user.getId());
    }

    @Override
    public void update(User user) {
        userByIds.replace(user.getId(), user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(userByIds.getOrDefault(id, null));
    }

    @Override
    public Collection<User> findByFullName(String firstName, String lastName) {
        return userByIds.values().stream().filter(user -> user.getFirstName().equals(firstName)&&user.getLastName().equals(lastName)).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userByIds.values().stream().filter(user -> user.getEmail().equals(email)).findAny();
    }

}
