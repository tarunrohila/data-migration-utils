package com.rohila.api.utils.config;

import static com.rohila.api.utils.constant.AppConstant.*;
import static com.rohila.api.utils.constant.BatchConstant.*;

import com.rohila.api.utils.batch.processor.CustomItemProcessor;
import com.rohila.api.utils.repository.entity.Customer;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Class file to configure Chunk based Batch Job
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Configuration
@ConditionalOnProperty(prefix = APP_BATCH_CONFIG_PREFIX, name = TYPE, havingValue = SIMPLE)
public class SimpleBatchConfig {

    /** Logger declaration */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AbstractPartitionBatchConfig.class);
    /** Auto wired dependency for {@link BatchConfigProperties} */
    @Autowired private BatchConfigProperties batchConfigProperties;

    /**
     * Creation of bean for {@link Job}
     *
     * @param jobRepository
     * @param migrateDataStep
     * @return a bean of {@link Job}
     */
    @Bean(MIGRATE_DATA_JOB)
    public Job migrateDataJob(
            final JobRepository jobRepository,
            final @Qualifier(MIGRATE_DATA_STEP) Step migrateDataStep,
            final @Qualifier(BATCH_JOB_EXECUTION_LISTENER) JobExecutionListener
                            mongoJobExecutionListener) {
        LOGGER.trace(
                "A bean of type = Job is created with name = migrateDataJob, migrateDataJob({},{},{})",
                jobRepository,
                migrateDataStep,
                mongoJobExecutionListener);
        return new JobBuilder(MIGRATE_DATA_JOB, jobRepository)
                .listener(mongoJobExecutionListener)
                .start(migrateDataStep)
                .build();
    }

    /**
     * Creation of bean for slave {@link Step}
     *
     * @param jobRepository
     * @param transactionManager
     * @param migrateDataTaskExecutor
     * @param migrateDataReader
     * @param migrateDataWriter
     * @return a bean of slave {@link Step}
     */
    @Bean(MIGRATE_DATA_STEP)
    public Step migrateDataStep(
            final JobRepository jobRepository,
            final PlatformTransactionManager transactionManager,
            final @Qualifier(MIGRATE_DATA_TASK_EXECUTOR) TaskExecutor migrateDataTaskExecutor,
            final @Qualifier(MIGRATE_DATA_READER) ItemReader<Customer> migrateDataReader,
            final @Qualifier(MIGRATE_DATA_PROCESSOR) ItemProcessor<Customer, Customer>
                            migrateDataProcessor,
            final @Qualifier(MIGRATE_DATA_WRITER) ItemWriter<Customer> migrateDataWriter,
            final @Qualifier(BATCH_STEP_EXECUTION_LISTENER) StepExecutionListener
                            mongoStepExecutionListener) {
        LOGGER.trace(
                "A bean of type = Step is created with name = migrateDataSlaveStep, migrateDataSlaveStep({},{},{},{},{},{})",
                jobRepository,
                transactionManager,
                migrateDataTaskExecutor,
                migrateDataReader,
                migrateDataProcessor,
                migrateDataWriter);
        return new StepBuilder(MIGRATE_DATA_STEP, jobRepository)
                .listener(mongoStepExecutionListener)
                .<Customer, Customer>chunk(batchConfigProperties.getChunkSize(), transactionManager)
                .reader(migrateDataReader)
                .processor(migrateDataProcessor)
                .writer(migrateDataWriter)
                .taskExecutor(migrateDataTaskExecutor)
                .build();
    }

    /**
     * Creation of bean for {@link TaskExecutor}
     *
     * @return taskExecutor
     */
    @Bean(MIGRATE_DATA_TASK_EXECUTOR)
    @StepScope
    public TaskExecutor migrateDataTaskExecutor() {
        LOGGER.trace(
                "A bean of type = TaskExecutor is created with name = migrateDataTaskExecutor, migrateDataTaskExecutor()");
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        return taskExecutor;
    }

    /**
     * Creation of bean for {@link MongoItemReaderBuilder<Customer>}
     *
     * @param minValue
     * @param maxValue
     * @param mongoTemplate
     * @return a bean of {@link MongoItemReaderBuilder<Customer>}
     */
    @Bean(MIGRATE_DATA_READER)
    @StepScope
    public ItemReader<Customer> migrateDataReader(
            final @Value(JOB_PARAMETERS_SOURCE_NAME) String srcCollectionName,
            final @Value(STEP_EXECUTION_CONTEXT_MIN_VALUE) Long minValue,
            final @Value(STEP_EXECUTION_CONTEXT_MAX_VALUE) Long maxValue,
            MongoTemplate mongoTemplate) {
        LOGGER.trace(
                "A bean of type = ItemReader is created with name = migrateDataReader, migrateDataReader({},{},{},{})",
                srcCollectionName,
                minValue,
                maxValue);
        return new MongoItemReaderBuilder<Customer>()
                .name(MIGRATE_DATA_READER)
                .collection(srcCollectionName)
                .targetType(Customer.class)
                .template(mongoTemplate)
                .query(new Query())
                .sorts(
                        new HashMap<String, Sort.Direction>() {
                            {
                                put(ID, Sort.Direction.ASC);
                            }
                        })
                .build();
    }

    /**
     * Creation of bean for {@link MongoItemWriterBuilder<Customer>}
     *
     * @param mongoTemplate
     * @return a bean of {@link MongoItemWriterBuilder<Customer>}
     */
    @Bean(MIGRATE_DATA_WRITER)
    @StepScope
    public ItemWriter<Customer> migrateDataWriter(
            final @Value(JOB_PARAMETERS_DESTINATION_NAME) String destCollectionName,
            final MongoTemplate mongoTemplate) {
        LOGGER.trace(
                "A bean of type = ItemWriter is created with name = migrateDataWriter, migrateDataWriter({},{})",
                destCollectionName,
                mongoTemplate);
        return new MongoItemWriterBuilder<Customer>()
                .template(mongoTemplate)
                .collection(destCollectionName)
                .build();
    }

    /**
     * Creation of bean for {@link ItemProcessor<Customer, Customer>}
     *
     * @return a bean of {@link ItemProcessor<Customer, Customer>}
     */
    @Bean(MIGRATE_DATA_PROCESSOR)
    @StepScope
    public ItemProcessor<Customer, Customer> migrateDataProcessor() {
        LOGGER.trace(
                "A bean of type = ItemProcessor is created with name = migrateDataProcessor, migrateDataProcessor()");
        CustomItemProcessor processor = new CustomItemProcessor();
        return processor;
    }
}
