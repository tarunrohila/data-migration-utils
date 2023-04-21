package com.rohila.api.utils.config;

import static com.rohila.api.utils.constant.AppConstant.*;
import static com.rohila.api.utils.constant.BatchConstant.*;

import com.rohila.api.utils.batch.mapper.CustomRowMapper;
import com.rohila.api.utils.batch.partitioner.JdbcRangePartitioner;
import com.rohila.api.utils.batch.partitioner.MongoRangePartitioner;
import com.rohila.api.utils.batch.processor.CustomItemProcessor;
import com.rohila.api.utils.batch.reader.CustomMongoItemReaderBuilder;
import com.rohila.api.utils.repository.entity.Customer;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Class file to configure Partition based Batch Job
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Configuration
@ConditionalOnProperty(prefix = APP_BATCH_CONFIG_PREFIX, name = TYPE, havingValue = PARTITION)
@EnableBatchProcessing
public abstract class AbstractPartitionBatchConfig<S, T> {

    /** Logger declaration */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AbstractPartitionBatchConfig.class);

    /** Auto wired dependency for {@link BatchConfigProperties} */
    @Autowired private BatchConfigProperties batchConfigProperties;

    /**
     * Creation of bean for {@link Job}
     *
     * @param jobRepository
     * @param migrateDataMasterStep
     * @return a bean of {@link Job}
     */
    @Bean(MIGRATE_DATA_JOB)
    public Job migrateDataJob(
            final JobRepository jobRepository,
            final @Qualifier(MIGRATE_DATA_MASTER_STEP) Step migrateDataMasterStep,
            final @Qualifier(BATCH_JOB_EXECUTION_LISTENER) JobExecutionListener
                            mongoJobExecutionListener) {
        LOGGER.trace(
                "A bean of type = Job is created with name = migrateDataJob, migrateDataJob({},{},{})",
                jobRepository,
                migrateDataMasterStep,
                mongoJobExecutionListener);
        return new JobBuilder(MIGRATE_DATA_JOB, jobRepository)
                .listener(mongoJobExecutionListener)
                .start(migrateDataMasterStep)
                .build();
    }

    /**
     * Creation of bean for master {@link Step}
     *
     * @param jobRepository
     * @param migrateDataSlaveStep
     * @param rangePartitioner
     * @param migrateDataPartitionHandler
     * @return a bean of master {@link Step}
     */
    @Bean(MIGRATE_DATA_MASTER_STEP)
    public Step migrateDataMasterStep(
            final JobRepository jobRepository,
            final @Qualifier(MIGRATE_DATA_SLAVE_STEP) Step migrateDataSlaveStep,
            final @Qualifier(RANGE_PARTITIONER) Partitioner rangePartitioner,
            final @Qualifier(MIGRATE_DATA_PARTITION_HANDLER) PartitionHandler
                            migrateDataPartitionHandler,
            final @Qualifier(BATCH_STEP_EXECUTION_LISTENER) StepExecutionListener
                            mongoStepExecutionListener) {
        LOGGER.trace(
                "A bean of type = Step is created with name = migrateDataMasterStep, migrateDataMasterStep({},{},{},{},{})",
                jobRepository,
                migrateDataSlaveStep,
                rangePartitioner,
                migrateDataPartitionHandler,
                mongoStepExecutionListener);
        return new StepBuilder(MIGRATE_DATA_MASTER_STEP, jobRepository)
                .listener(mongoStepExecutionListener)
                .partitioner(migrateDataSlaveStep.getName(), rangePartitioner)
                .partitionHandler(migrateDataPartitionHandler)
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
    @Bean(MIGRATE_DATA_SLAVE_STEP)
    public Step migrateDataSlaveStep(
            final JobRepository jobRepository,
            final PlatformTransactionManager transactionManager,
            final @Qualifier(MIGRATE_DATA_TASK_EXECUTOR) TaskExecutor migrateDataTaskExecutor,
            final @Qualifier(MIGRATE_DATA_READER) ItemReader<S> migrateDataReader,
            final @Qualifier(MIGRATE_DATA_PROCESSOR) ItemProcessor<S, T> migrateDataProcessor,
            final @Qualifier(MIGRATE_DATA_WRITER) ItemWriter<T> migrateDataWriter,
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
        return new StepBuilder(MIGRATE_DATA_SLAVE_STEP, jobRepository)
                .listener(mongoStepExecutionListener)
                .<S, T>chunk(batchConfigProperties.getChunkSize(), transactionManager)
                .reader(migrateDataReader)
                .processor(migrateDataProcessor)
                .writer(migrateDataWriter)
                .taskExecutor(migrateDataTaskExecutor)
                .build();
    }

    /**
     * Creation of bean for {@link MongoRangePartitioner}
     *
     * @param mongoTemplate
     * @param srcCollectionName
     * @return a bean of {@link MongoRangePartitioner}
     */
    @Bean(RANGE_PARTITIONER)
    @StepScope
    @ConditionalOnProperty(
            prefix = APP_BATCH_CONFIG_PREFIX,
            name = SOURCE_DB,
            havingValue = MONGODB)
    public Partitioner mongoRangePartitioner(
            final @Value(JOB_PARAMETERS_SOURCE_NAME) String srcCollectionName,
            final @Qualifier(SRC_MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        MongoRangePartitioner mongoRangePartitioner = new MongoRangePartitioner();
        mongoRangePartitioner.setMongoTemplate(mongoTemplate);
        mongoRangePartitioner.setCollectionName(srcCollectionName);
        LOGGER.trace(
                "A bean of type = Partitioner is created with name = mongoRangePartitioner, mongoRangePartitioner({})",
                mongoTemplate);
        return mongoRangePartitioner;
    }

    /**
     * Creation of bean for {@link JdbcRangePartitioner}
     *
     * @param jdbcTemplate
     * @param srcTableName
     * @return a bean of {@link JdbcRangePartitioner}
     */
    @Bean(RANGE_PARTITIONER)
    @StepScope
    @ConditionalOnProperty(prefix = APP_BATCH_CONFIG_PREFIX, name = SOURCE_DB, havingValue = JDBC)
    public Partitioner jdbcRangePartitioner(
            final @Value(JOB_PARAMETERS_SOURCE_NAME) String srcTableName,
            final @Value(JOB_PARAMETERS_IDENTIFIER_NAME) String identifier,
            final @Qualifier(SRC_JDBC_TEMPLATE) JdbcTemplate jdbcTemplate) {
        JdbcRangePartitioner rangePartitioner = new JdbcRangePartitioner();
        rangePartitioner.setJdbcTemplate(jdbcTemplate);
        rangePartitioner.setTable(srcTableName);
        rangePartitioner.setColumn(identifier);
        LOGGER.trace(
                "A bean of type = Partitioner is created with name = jdbcRangePartitioner, jdbcRangePartitioner({})",
                jdbcTemplate);
        return rangePartitioner;
    }

    /**
     * Creation of bean for {@link PartitionHandler}
     *
     * @param migrateDataTaskExecutor
     * @param migrateDataSlaveStep
     * @return a bean of {@link PartitionHandler}
     */
    @Bean(MIGRATE_DATA_PARTITION_HANDLER)
    @StepScope
    public PartitionHandler migrateDataPartitionHandler(
            final @Qualifier(MIGRATE_DATA_TASK_EXECUTOR) TaskExecutor migrateDataTaskExecutor,
            final @Qualifier(MIGRATE_DATA_SLAVE_STEP) Step migrateDataSlaveStep) {
        LOGGER.trace(
                "A bean of type = PartitionHandler is created with name = migrateDataPartitionHandler, migrateDataPartitionHandler({},{})",
                migrateDataTaskExecutor,
                migrateDataSlaveStep);
        TaskExecutorPartitionHandler taskExecutorPartitionHandler =
                new TaskExecutorPartitionHandler();
        taskExecutorPartitionHandler.setGridSize(batchConfigProperties.getGridSize());
        taskExecutorPartitionHandler.setTaskExecutor(migrateDataTaskExecutor);
        taskExecutorPartitionHandler.setStep(migrateDataSlaveStep);
        return taskExecutorPartitionHandler;
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
        /*int threadCount = 50;
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(threadCount);
        taskExecutor.setCorePoolSize(threadCount);
        taskExecutor.setQueueCapacity(
                Math.max(5 * batchConfigProperties.getChunkSize(), 5 * threadCount));
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);*/
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
    @ConditionalOnProperty(
            prefix = APP_BATCH_CONFIG_PREFIX,
            name = SOURCE_DB,
            havingValue = MONGODB)
    public ItemReader<S> migrateDataReader(
            final @Value(JOB_PARAMETERS_SOURCE_NAME) String srcCollectionName,
            final @Value(STEP_EXECUTION_CONTEXT_MIN_VALUE) Long minValue,
            final @Value(STEP_EXECUTION_CONTEXT_MAX_VALUE) Long maxValue,
            final @Value(STEP_EXECUTION_CONTEXT_PAGE_NO) int pageNo,
            final @Value(STEP_EXECUTION_CONTEXT_PAGE_SIZE) int pageSize,
            final @Qualifier(SRC_MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        LOGGER.trace(
                "A bean of type = ItemReader is created with name = migrateDataReader, migrateDataReader({},{},{},{})",
                srcCollectionName,
                minValue,
                maxValue,
                pageNo);
        Class<S> targetClazz =
                (Class<S>)
                        ((ParameterizedType)
                                        ((Class<?>) getClass().getGenericSuperclass())
                                                .getGenericSuperclass())
                                .getActualTypeArguments()[0];
        return new CustomMongoItemReaderBuilder<S>()
                .name(MIGRATE_DATA_READER)
                .collection(srcCollectionName)
                .targetType(targetClazz)
                .template(mongoTemplate)
                .query(new Query())
                .page(pageNo)
                .pageSize(pageSize)
                .sorts(
                        new HashMap<String, Sort.Direction>() {
                            {
                                put(ID, Sort.Direction.ASC);
                            }
                        })
                .build();
    }

    @Bean(MIGRATE_DATA_READER)
    @StepScope
    @ConditionalOnProperty(prefix = APP_BATCH_CONFIG_PREFIX, name = SOURCE_DB, havingValue = JDBC)
    public JdbcPagingItemReader<S> jdbcPagingItemReader(
            final @Qualifier(SRC_DATASOURCE) DataSource dataSource,
            final @Value(STEP_EXECUTION_CONTEXT_MIN_VALUE) Long minValue,
            final @Value(JOB_PARAMETERS_SOURCE_NAME) String srcTableName,
            final @Value(STEP_EXECUTION_CONTEXT_MAX_VALUE) Long maxValue) {
        Class<S> sourceClazz =
                (Class<S>)
                        ((ParameterizedType)
                                        ((Class<?>) getClass().getGenericSuperclass())
                                                .getGenericSuperclass())
                                .getActualTypeArguments()[0];
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, name, active");
        queryProvider.setFromClause("from " + srcTableName + " ");
        queryProvider.setWhereClause("where id >= " + minValue + " and id < " + maxValue);
        queryProvider.setSortKeys(sortKeys);
        JdbcPagingItemReader<S> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(1000);
        reader.setRowMapper(new CustomRowMapper(sourceClazz));
        reader.setQueryProvider(queryProvider);
        return reader;
    }

    /**
     * Creation of bean for {@link MongoItemWriterBuilder<Customer>}
     *
     * @param mongoTemplate
     * @return a bean of {@link MongoItemWriterBuilder<Customer>}
     */
    @Bean(MIGRATE_DATA_WRITER)
    @StepScope
    @ConditionalOnProperty(
            prefix = APP_BATCH_CONFIG_PREFIX,
            name = DESTINATION_DB,
            havingValue = MONGODB)
    public ItemWriter<T> migrateDataWriter(
            final @Value(JOB_PARAMETERS_DESTINATION_NAME) String destCollectionName,
            final @Qualifier(DEST_MONGO_TEMPLATE) MongoTemplate mongoTemplate) {
        LOGGER.trace(
                "A bean of type = ItemWriter is created with name = migrateDataWriter, migrateDataWriter({},{})",
                destCollectionName,
                mongoTemplate);
        return new MongoItemWriterBuilder<T>()
                .template(mongoTemplate)
                .collection(destCollectionName)
                .build();
    }

    /**
     * Creation of bean for {@link ItemProcessor<Customer, Customer>}
     *
     * @return a bean of {@link ItemProcessor<Customer, Customer>}
     */
    @Bean(MIGRATE_DATA_WRITER)
    @StepScope
    @ConditionalOnExpression(CONDITIONAL_EXP_DESTINATION_DB_NE_MONGODB)
    public ItemWriter<T> jdbcMigrateDataWriter(
            final @Qualifier(DEST_DATASOURCE) DataSource dataSource,
            final @Value(JOB_PARAMETERS_DESTINATION_NAME) String destinationTableName)
            throws SQLException {
        StringBuilder sql =
                new StringBuilder("Insert into ").append(destinationTableName).append(" values (");
        Connection connection = null;
        List<String> fields = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            ResultSet rs =
                    connection.getMetaData().getColumns(null, null, destinationTableName, null);
            while (rs.next()) {
                fields.add(":" + rs.getString("COLUMN_NAME"));
            }
            sql.append(String.join(",", fields)).append(")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
        return new JdbcBatchItemWriterBuilder<T>()
                .dataSource(dataSource)
                .sql(sql.toString())
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    /**
     * Creation of bean for {@link ItemProcessor<Customer, Customer>}
     *
     * @return a bean of {@link ItemProcessor<Customer, Customer>}
     */
    @Bean(MIGRATE_DATA_PROCESSOR)
    @StepScope
    public ItemProcessor<S, T> migrateDataProcessor() {
        LOGGER.trace(
                "A bean of type = ItemProcessor is created with name = migrateDataProcessor, migrateDataProcessor()");
        Class<S> sourceClazz =
                (Class<S>)
                        ((ParameterizedType)
                                        ((Class<?>) getClass().getGenericSuperclass())
                                                .getGenericSuperclass())
                                .getActualTypeArguments()[0];
        Class<T> targetClazz =
                (Class<T>)
                        ((ParameterizedType)
                                        ((Class<?>) getClass().getGenericSuperclass())
                                                .getGenericSuperclass())
                                .getActualTypeArguments()[0];
        CustomItemProcessor processor = new CustomItemProcessor(sourceClazz, targetClazz);
        return processor;
    }
}
