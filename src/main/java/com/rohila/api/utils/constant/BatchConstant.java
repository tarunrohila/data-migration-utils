package com.rohila.api.utils.constant;

/**
 * Class file to declare constants for batch processing
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
public class BatchConstant {
    /** Constant declaration for MIGRATE_DATA_JOB */
    public static final String MIGRATE_DATA_JOB = "migrateDataJob";
    /** Constant declaration for MIGRATE_DATA_STEP */
    public static final String MIGRATE_DATA_STEP = "migrateDataStep";
    /** Constant declaration for MIGRATE_DATA_MASTER_STEP */
    public static final String MIGRATE_DATA_MASTER_STEP = "migrateDataMasterStep";
    /** Constant declaration for MIGRATE_DATA_SLAVE_STEP */
    public static final String MIGRATE_DATA_SLAVE_STEP = "migrateDataSlaveStep";
    /** Constant declaration for MONGO_RANGE_PARTITIONER */
    public static final String RANGE_PARTITIONER = "rangePartitioner";
    /** Constant declaration for MIGRATE_DATA_PARTITION_HANDLER */
    public static final String MIGRATE_DATA_PARTITION_HANDLER = "migrateDataPartitionHandler";
    /** Constant declaration for MIGRATE_DATA_TASK_EXECUTOR */
    public static final String MIGRATE_DATA_TASK_EXECUTOR = "migrateDataTaskExecutor";
    /** Constant declaration for MIGRATE_DATA_READER */
    public static final String MIGRATE_DATA_READER = "migrateDataReader";
    /** Constant declaration for MIGRATE_DATA_WRITER */
    public static final String MIGRATE_DATA_WRITER = "migrateDataWriter";
    /** Constant declaration for MIGRATE_DATA_PROCESSOR */
    public static final String MIGRATE_DATA_PROCESSOR = "migrateDataProcessor";
    /** Constant declaration for MONGO_JOB_EXECUTION_LISTENER */
    public static final String BATCH_JOB_EXECUTION_LISTENER = "jobExecutionListener";
    /** Constant declaration for MONGO_STEP_EXECUTION_LISTENER */
    public static final String BATCH_STEP_EXECUTION_LISTENER = "stepExecutionListener";
    /** Constant declaration for ACTIVE */
    public static final String ACTIVE = "active";
    /** Constant declaration for ID */
    public static final String ID = "_id";
    /** Constant declaration for CUSTOMER */
    public static final String CUSTOMER = "customer";
    /** Constant declaration for USER */
    public static final String USER = "user";
    /** Constant declaration for STEP_EXECUTION_CONTEXT_MIN_VALUE */
    public static final String STEP_EXECUTION_CONTEXT_MIN_VALUE =
            "#{stepExecutionContext['minValue']}";
    /** Constant declaration for STEP_EXECUTION_CONTEXT_MAX_VALUE */
    public static final String STEP_EXECUTION_CONTEXT_MAX_VALUE =
            "#{stepExecutionContext['maxValue']}";
    /** Constant declaration for STEP_EXECUTION_CONTEXT_PAGE_NO */
    public static final String STEP_EXECUTION_CONTEXT_PAGE_NO = "#{stepExecutionContext['pageNo']}";
    /** Constant declaration for STEP_EXECUTION_CONTEXT_PAGE_SIZE */
    public static final String STEP_EXECUTION_CONTEXT_PAGE_SIZE =
            "#{stepExecutionContext['pageSize']}";

    /** Constant declaration for STEP_EXECUTION_CONTEXT_MAX_SIZE */
    public static final String STEP_EXECUTION_CONTEXT_MAX_SIZE =
            "#{stepExecutionContext['maxSize']}";
    /** Constant declaration for JOB_PARAMETERS_DEST_COLLECTION_NAME */
    public static final String JOB_PARAMETERS_DESTINATION_NAME = "#{jobParameters['destination']}";
    /** Constant declaration for JOB_PARAMETERS_SRC_COLLECTION_NAME */
    public static final String JOB_PARAMETERS_SOURCE_NAME = "#{jobParameters['source']}";

    /** Constant declaration for JOB_ID */
    public static final String JOB_ID = "JobId";
    /** Constant declaration for DATE */
    public static final String DATE = "date";
    /** Constant declaration for TIME */
    public static final String TIME = "time";

    private BatchConstant() {}
}
