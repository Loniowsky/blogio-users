package com.cierpich.blogio.users.domain.port.outgoing;

import java.util.Optional;
import java.util.UUID;

public interface Repository<T> {

    void save(T t);
    void delete(T t);
    void update(T t);
    Optional<T> findById(UUID id);

}
