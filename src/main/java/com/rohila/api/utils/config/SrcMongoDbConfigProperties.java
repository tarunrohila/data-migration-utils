package com.rohila.api.utils.config;

import static com.rohila.api.utils.constant.AppConstant.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class which is used to create configuration for mongo db
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since 18-04-2023 23:14
 */
@Data
@Component(SRC_MONGO_DB_CONFIG_PROPERTIES)
@ConfigurationProperties(prefix = SRC_MONGO_CONFIG_PREFIX)
@ConditionalOnProperty(prefix = APP_BATCH_CONFIG_PREFIX, name = SOURCE_DB, havingValue = MONGODB)
@EqualsAndHashCode(callSuper = true)
public class SrcMongoDbConfigProperties extends MongoDbConfigProperties {}
