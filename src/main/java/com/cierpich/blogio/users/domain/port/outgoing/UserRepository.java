package com.cierpich.blogio.users.domain.port.outgoing;

import com.cierpich.blogio.users.domain.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends Repository<User>{

    Collection<User> findByFullName(String firstName, String lastName);
    Optional<User> findByEmail(String email);

}
