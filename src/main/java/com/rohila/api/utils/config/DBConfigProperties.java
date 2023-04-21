package com.rohila.api.utils.config;

/**
 * Class which is used to keep configuration for db connection
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since 18-04-2023 23:47
 */
import static com.rohila.api.utils.constant.AppConstant.DB_CONFIG_PREFIX;
import static com.rohila.api.utils.constant.AppConstant.DB_CONFIG_PROPERTIES;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component(DB_CONFIG_PROPERTIES)
@ConfigurationProperties(prefix = DB_CONFIG_PREFIX)
public class DBConfigProperties {
    /** Variable declaration for url */
    private String url;

    /** Variable declaration for username */
    private String username;

    /** Variable declaration for password */
    private String password;

    /** Variable declaration for driverClassName */
    private String driverClassName;

    /** Variable declaration for hikari */
    private HikariConfigProperties hikari = new HikariConfigProperties();
}
