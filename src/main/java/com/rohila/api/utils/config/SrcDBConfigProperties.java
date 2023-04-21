package com.rohila.api.utils.config;

/**
 * Class which is used to keep configuration for db connection
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since 18-04-2023 23:47
 */
import static com.rohila.api.utils.constant.AppConstant.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component(SRC_DB_CONFIG_PROPERTIES)
@ConfigurationProperties(prefix = SRC_DB_CONFIG_PREFIX)
@ConditionalOnProperty(prefix = APP_BATCH_CONFIG_PREFIX, name = SOURCE_DB, havingValue = JDBC)
@EqualsAndHashCode(callSuper = true)
public class SrcDBConfigProperties extends DBConfigProperties {}
