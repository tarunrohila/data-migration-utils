package com.rohila.api.utils.config;

import static com.rohila.api.utils.constant.AppConstant.*;

import com.rohila.api.utils.repository.entity.Customer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Class which is used to initialze batch application
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since 21-04-2023 13:48
 */
@Configuration
@ConditionalOnProperty(prefix = APP_BATCH_CONFIG_PREFIX, name = TYPE, havingValue = PARTITION)
@EnableBatchProcessing
public class PartitionBatchConfig extends AbstractPartitionBatchConfig<Customer, Customer> {
} // TestBatch
/* @@_END: CLASS DEFINITION --------------------------------------*/
