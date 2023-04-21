package com.rohila.api.utils.config;

import static com.rohila.api.utils.constant.AppConstant.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Class file create configuration to connect with db
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Configuration
@ConditionalOnExpression(CONDITIONAL_EXP_SOURCE_DESTINATION_DB_NE_MONGODB)
public class DbConfig {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(DbConfig.class);

    /**
     * Creation of bean for {@link DataSource}
     *
     * @return a bean of {@link DataSource}
     */
    @Bean(SRC_DATASOURCE)
    @ConditionalOnExpression(CONDITIONAL_EXP_SOURCE_DB_NE_MONGODB)
    public DataSource srcDatasource(
            final @Qualifier(SRC_DB_CONFIG_PROPERTIES) DBConfigProperties dbConfigProperties) {
        LOGGER.trace(
                "A bean of type = DataSource is created with name = srcDatasource, srcDatasource({})",
                dbConfigProperties);
        return new HikariDataSource(getHikariConfig(dbConfigProperties));
    }

    /**
     * Creation of bean for {@link DataSource}
     *
     * @return a bean of {@link DataSource}
     */
    @Bean(DEST_DATASOURCE)
    @ConditionalOnExpression(CONDITIONAL_EXP_DESTINATION_DB_NE_MONGODB)
    public DataSource destDatasource(
            final @Qualifier(DEST_DB_CONFIG_PROPERTIES) DBConfigProperties dbConfigProperties) {
        LOGGER.trace(
                "A bean of type = DataSource is created with name = destDatasource, destDatasource({})",
                dbConfigProperties);
        return new HikariDataSource(getHikariConfig(dbConfigProperties));
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
     * Creation of bean for {@link JdbcTemplate}
     *
     * @param dataSource
     * @return a bean of {@link JdbcTemplate}
     */
    @Bean(SRC_JDBC_TEMPLATE)
    @ConditionalOnExpression(CONDITIONAL_EXP_SOURCE_DB_NE_MONGODB)
    public JdbcTemplate srcJdbcTemplate(final @Qualifier("srcDatasource") DataSource dataSource) {
        LOGGER.trace(
                "A bean of type = JdbcTemplate is created with name = srcJdbcTemplate, srcJdbcTemplate({})",
                dataSource);
        return new JdbcTemplate(dataSource);
    }

    /**
     * Creation of bean for {@link JdbcTemplate}
     *
     * @param dataSource
     * @return a bean of {@link JdbcTemplate}
     */
    @Bean(DEST_JDBC_TEMPLATE)
    @ConditionalOnExpression(CONDITIONAL_EXP_DESTINATION_DB_NE_MONGODB)
    public JdbcTemplate destJdbcTemplate(final @Qualifier("destDatasource") DataSource dataSource) {
        LOGGER.trace(
                "A bean of type = JdbcTemplate is created with name = destJdbcTemplate, destJdbcTemplate({})",
                dataSource);
        return new JdbcTemplate(dataSource);
    }
}
