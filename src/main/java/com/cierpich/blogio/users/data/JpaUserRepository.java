package com.cierpich.blogio.users.data;

import com.cierpich.blogio.users.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends CrudRepository<User, UUID> {
    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);
    Optional<User> findByEmail(String email);
}
