package com.rohila.api.utils.config;

import static com.rohila.api.utils.constant.AppConstant.APP_BATCH_CONFIG_PREFIX;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class file to read spring batch configuration start wtih app.batch prefix
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Data
@Component
@ConfigurationProperties(prefix = APP_BATCH_CONFIG_PREFIX)
public class BatchConfigProperties {

    /** Variable declaration for chunkSize */
    private int chunkSize = 10;
    /** Variable declaration for gridSize */
    private int gridSize = 10;
}
