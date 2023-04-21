package com.rohila.api.utils.config;

import static com.rohila.api.utils.constant.AppConstant.MONGO_CONFIG_PREFIX;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class file to read spring mongodb configuration start with spring.data.mongodb prefix
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Data
@Component
@ConfigurationProperties(prefix = MONGO_CONFIG_PREFIX)
public class MongoDbConfigProperties {
    /** Variable declaration for uri */
    private String uri = "mongodb://localhost:27017/";

    /** Variable declaration for username */
    private String username;

    /** Variable declaration for password */
    private String password;

    /** Variable declaration for database */
    private String database;
    /** Variable declaration for connectTimeout */
    private int connectTimeout = 30000;

    /** Variable declaration for readTimeout */
    private int readTimeout = 30000;
    /** Variable declaration for maxWaitTime */
    private int maxWaitTime = 30000;

    /** Variable declaration for maxIdleTime */
    private int maxIdleTime = 30000;
    /** Variable declaration for maxLifeTime */
    private int maxLifeTime = 30000;

    /** Variable declaration for sslEnabled */
    private boolean sslEnabled = true;

    /** Variable declaration for invalidHostNameAllowed */
    private boolean invalidHostNameAllowed = false;

    /** Variable declaration for minPoolSize */
    private int minPoolSize = 2;
    /** Variable declaration for maxPoolSize */
    private int maxPoolSize = 10;
}
