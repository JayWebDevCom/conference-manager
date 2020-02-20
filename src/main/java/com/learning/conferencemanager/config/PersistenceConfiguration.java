package com.learning.conferencemanager.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public static DataSource dataSource() {

        PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
                .withDatabaseName("conference_app")
                .withUsername("postgres")
                .withPassword("Welcome");

        postgreSQLContainer.start();

        Flyway.configure()
                .dataSource(
                        postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(),
                        postgreSQLContainer.getPassword())
                .load()
                .migrate();

        return DataSourceBuilder.create()
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }
}
