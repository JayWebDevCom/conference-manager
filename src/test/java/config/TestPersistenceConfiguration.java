package config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;
import spock.lang.AutoCleanup;

import javax.sql.DataSource;
import java.util.Properties;

@TestConfiguration
@EnableJpaRepositories(basePackages = "com.learning.conferencemanager.repositories")
public class TestPersistenceConfiguration {

    @AutoCleanup
    PostgreSQLContainer postgreSQLContainer;

    @Bean
    public PostgreSQLContainer postgreSQLContainer() {
        postgreSQLContainer = new PostgreSQLContainer("postgres:11.1");
        return postgreSQLContainer;
    }

    @Bean
    public static DataSource dataSource(PostgreSQLContainer postgreSQLContainer) {

        postgreSQLContainer.start();

        final DataSource dataSource = DataSourceBuilder.create()
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();

        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();

        return dataSource;
    }

    @Bean
    public static LocalContainerEntityManagerFactoryBean entityManagerFactory(PostgreSQLContainer postgreSQLContainer) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource(postgreSQLContainer));
        factoryBean.setPackagesToScan("com.learning.conferencemanager.models");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;
    }

    @Bean
    public static JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager dbTransactionManager(PostgreSQLContainer postgreSQLContainer) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory(postgreSQLContainer).getObject());
        return transactionManager;
    }

    private static Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }
}
