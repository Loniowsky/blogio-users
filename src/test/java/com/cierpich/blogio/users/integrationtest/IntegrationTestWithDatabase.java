package com.cierpich.blogio.users.integrationtest;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public abstract class IntegrationTestWithDatabase {

    @Container
    @SuppressWarnings({"rawtypes"})
    private static final MariaDBContainer mariaDB;

    static {
        mariaDB = new MariaDBContainer<>(DockerImageName.parse("mariadb:latest"));
        mariaDB.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariaDB::getJdbcUrl);
        registry.add("spring.datasource.username", mariaDB::getUsername);
        registry.add("spring.datasource.password", mariaDB::getPassword);
    }

}
