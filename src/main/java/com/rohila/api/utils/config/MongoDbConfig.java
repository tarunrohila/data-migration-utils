package com.rohila.api.utils.config;

import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;
import com.mongodb.connection.SslSettings;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Class file create configuration to connect with mongodb
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Configuration
public class MongoDbConfig {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbConfig.class);

    /**
     * Creation of bean for {@link MongoClient}
     *
     * @return a bean of {@link MongoClient}
     */
    @Bean
    public MongoClient mongoClient(final MongoDbConfigProperties mongoDbConfigProperties) {
        LOGGER.trace(
                "A bean of type = MongoClient is created with name = mongoClient, mongoClient({})",
                mongoDbConfigProperties);
        return MongoClients.create(mongoClientSettings(mongoDbConfigProperties));
    }

    /**
     * Creation of bean for {@link MongoTemplate}
     *
     * @param mongoClient
     * @return a bean of {@link MongoTemplate}
     */
    @Bean
    @Primary
    public MongoTemplate mongoTemplate(
            final MongoClient mongoClient, final MongoDbConfigProperties mongoDbConfigProperties) {
        LOGGER.trace(
                "A bean of type = MongoTemplate is created with name = mongoTemplate, mongoTemplate({}, {})",
                mongoClient,
                mongoDbConfigProperties);
        return new MongoTemplate(mongoClient, mongoDbConfigProperties.getDatabase());
    }

    /**
     * Method to prepare mongo client settings
     *
     * @return mongoclientsettings
     */
    private MongoClientSettings mongoClientSettings(
            final MongoDbConfigProperties mongoDbConfigProperties) {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoDbConfigProperties.getUri()))
                .applyToSocketSettings(socketSettings(mongoDbConfigProperties))
                .applyToConnectionPoolSettings(connectionPoolSettings(mongoDbConfigProperties))
                .applyToSslSettings(sslSettings(mongoDbConfigProperties))
                .credential(mongoCredential(mongoDbConfigProperties))
                .build();
    }

    /**
     * Method to prepare socket settings
     *
     * @return socket settings
     */
    private Block<SocketSettings.Builder> socketSettings(
            final MongoDbConfigProperties mongoDbConfigProperties) {
        return builder -> {
            builder.connectTimeout(
                    mongoDbConfigProperties.getConnectTimeout(), TimeUnit.MILLISECONDS);
            builder.readTimeout(mongoDbConfigProperties.getReadTimeout(), TimeUnit.MILLISECONDS);
        };
    }

    /**
     * Method to prepare connection pool settings
     *
     * @return connection pool settings
     */
    private Block<ConnectionPoolSettings.Builder> connectionPoolSettings(
            final MongoDbConfigProperties mongoDbConfigProperties) {
        return builder -> {
            builder.maxWaitTime(mongoDbConfigProperties.getMaxWaitTime(), TimeUnit.MILLISECONDS);
            builder.maxConnectionIdleTime(
                    mongoDbConfigProperties.getMaxIdleTime(), TimeUnit.MILLISECONDS);
            builder.maxConnectionLifeTime(
                    mongoDbConfigProperties.getMaxLifeTime(), TimeUnit.MILLISECONDS);
            builder.minSize(mongoDbConfigProperties.getMinPoolSize());
            builder.maxSize(mongoDbConfigProperties.getMaxPoolSize());
        };
    }

    /**
     * Method to prepare ssl settings
     *
     * @return ssl settings
     */
    private Block<SslSettings.Builder> sslSettings(
            final MongoDbConfigProperties mongoDbConfigProperties) {
        return builder -> {
            builder.enabled(mongoDbConfigProperties.isSslEnabled());
            builder.invalidHostNameAllowed(mongoDbConfigProperties.isInvalidHostNameAllowed());
        };
    }

    /**
     * Method to prepare mongo Credentials
     *
     * @return mongo Credentials
     */
    private MongoCredential mongoCredential(final MongoDbConfigProperties mongoDbConfigProperties) {
        return MongoCredential.createCredential(
                mongoDbConfigProperties.getUsername(),
                mongoDbConfigProperties.getDatabase(),
                mongoDbConfigProperties.getPassword().toCharArray());
    }
}
