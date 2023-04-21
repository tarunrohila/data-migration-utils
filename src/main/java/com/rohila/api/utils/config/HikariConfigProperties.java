package com.rohila.api.utils.config;

import lombok.Data;

/**
 * Class which is used to keep hikari config
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since 18-04-2023 23:47
 */
@Data
public class HikariConfigProperties {
    /** Variable declaration for minimumIdle */
    private int minimumIdle = 2;

    /** Variable declaration for maximumPoolSize */
    private int maximumPoolSize = 10;

    /** Variable declaration for connectionTimeout */
    private long connectionTimeout = 30000;

    /** Variable declaration for maxLifetime */
    private long maxLifetime = 30000;

    /** Variable declaration for driverClassName */
    private long idleTimeout = 90000;

    /** Variable declaration for leakDetectionThreshold */
    private long leakDetectionThreshold = 90000;
}
