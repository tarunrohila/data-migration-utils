package com.rohila.api.utils.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Class file create configuration to connect with db
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.rohila.api.utils.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class DefaultDbConfig {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDbConfig.class);

    /** Autowired environment dependency. */
    @Autowired private Environment environment;

    /**
     * Creation of bean for {@link DataSource}
     *
     * @return a bean of {@link DataSource}
     */
    @Bean("dataSource")
    @Primary
    public DataSource dataSource(
            final @Qualifier("dbConfigProperties") DBConfigProperties dbConfigProperties) {
        LOGGER.trace("A bean of type = DataSource is created with name = dataSource, dataSource()");
        return new HikariDataSource(getHikariConfig(dbConfigProperties));
    }

    /**
     * Creation of bean for {@link DataSource}
     *
     * @return a bean of {@link DataSource}
     */
    @Bean("jdbcTemplate")
    @Primary
    public JdbcTemplate jdbcTemplate(final @Qualifier("dataSource") DataSource dataSource) {
        LOGGER.trace(
                "A bean of type = JdbcTemplate is created with name = jdbcTemplate, jdbcTemplate()");
        return new JdbcTemplate(dataSource);
    }

    /**
     * Method to create hikari config
     *
     * @param dbConfigProperties
     * @return hikari config
     */
    private HikariConfig getHikariConfig(DBConfigProperties dbConfigProperties) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dbConfigProperties.getDriverClassName());
        config.setJdbcUrl(dbConfigProperties.getUrl());
        config.setUsername(dbConfigProperties.getUsername());
        config.setPassword(dbConfigProperties.getPassword());
        config.setMinimumIdle(dbConfigProperties.getHikari().getMinimumIdle());
        config.setMaximumPoolSize(dbConfigProperties.getHikari().getMaximumPoolSize());
        config.setConnectionTimeout(dbConfigProperties.getHikari().getConnectionTimeout());
        config.setLeakDetectionThreshold(
                dbConfigProperties.getHikari().getLeakDetectionThreshold());
        config.setMaxLifetime(dbConfigProperties.getHikari().getMaxLifetime());
        config.setIdleTimeout(dbConfigProperties.getHikari().getIdleTimeout());
        return config;
    }

    /**
     * Entitiy Manager factory.
     *
     * @return - factoryBean
     * @throws NamingException -NamingException
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource)
            throws NamingException {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.rohila.api.utils.repository");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;
    }

    /**
     * JPA vendor adaptor to connect with JPA
     *
     * @return - hibernateJpaVendorAdapter
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        return hibernateJpaVendorAdapter;
    }

    /**
     * JPA properties
     *
     * @return - properties
     */
    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put(
                "hibernate.hbm2ddl.auto",
                environment.getRequiredProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
        properties.put(
                "hibernate.show_sql",
                environment.getRequiredProperty("spring.jpa.properties.hibernate.show_sql"));
        properties.put(
                "hibernate.format_sql",
                environment.getRequiredProperty("spring.jpa.properties.hibernate.format_sql"));
        return properties;
    }

    /**
     * Transaction Manager
     *
     * @param emf - entityManagerFactory
     * @return txManager - transaction manager
     */
    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
}
