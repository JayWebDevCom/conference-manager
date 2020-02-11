package com.learning.conferencemanager.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5555/conference_app")
                .build();
    }
}
