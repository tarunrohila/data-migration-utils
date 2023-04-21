package com.rohila.api.utils.constant;

/**
 * Class file to declare constants for app
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
public class AppConstant {
    /** Constant declaration for EXECUTE_URI */
    public static final String EXECUTE_URI = "/execute";
    /** Constant declaration for SRC_COLLECTION_NAME */
    public static final String SOURCE = "source";
    /** Constant declaration for DEST_COLLECTION_NAME */
    public static final String DESTINATION = "destination";
    /** Constant declaration for APP_BATCH_CONFIG_PREFIX */
    public static final String APP_BATCH_CONFIG_PREFIX = "app.batch";

    /** Constant declaration for MONGO_CONFIG_PREFIX */
    public static final String MONGO_CONFIG_PREFIX = "spring.data.mongodb";

    /** Constant declaration for SRC_MONGO_CONFIG_PREFIX */
    public static final String SRC_MONGO_CONFIG_PREFIX = "spring.data.mongodb.source";

    /** Constant declaration for DEST_MONGO_CONFIG_PREFIX */
    public static final String DEST_MONGO_CONFIG_PREFIX = "spring.data.mongodb.destination";

    /** Constant declaration for DB_CONFIG_PREFIX */
    public static final String DB_CONFIG_PREFIX = "spring.datasource";

    /** Constant declaration for SRC_DB_CONFIG_PREFIX */
    public static final String SRC_DB_CONFIG_PREFIX = "spring.datasource.source";

    /** Constant declaration for DEST_DB_CONFIG_PREFIX */
    public static final String DEST_DB_CONFIG_PREFIX = "spring.datasource.destination";

    /** Constant declaration for SOURCE_DB */
    public static final String SOURCE_DB = "source-db";

    /** Constant declaration for DESTINATION_DB */
    public static final String DESTINATION_DB = "destination-db";

    /** Constant declaration for MONGODB */
    public static final String MONGODB = "mongodb";

    /** Constant declaration for JDBC */
    public static final String JDBC = "jdbc";

    public static final String SRC_DATASOURCE = "srcDatasource";
    public static final String CONDITIONAL_EXP_SOURCE_DB_NE_MONGODB =
            "#{'${app.batch.source-db}' ne 'mongodb'}";

    public static final String CONDITIONAL_EXP_DESTINATION_DB_NE_MONGODB =
            "#{'${app.batch.destination-db}' ne 'mongodb'}";

    public static final String CONDITIONAL_EXP_SOURCE_DESTINATION_DB_NE_MONGODB =
            "#{'${app.batch.source-db}' ne 'mongodb' || '${app.batch.destination-db}' ne 'mongodb'}";
    public static final String SRC_DB_CONFIG_PROPERTIES = "srcDbConfigProperties";
    public static final String DEST_DATASOURCE = "destDatasource";

    public static final String DEST_DB_CONFIG_PROPERTIES = "destDbConfigProperties";
    public static final String SRC_JDBC_TEMPLATE = "srcJdbcTemplate";
    public static final String DEST_JDBC_TEMPLATE = "destJdbcTemplate";

    public static final String DB_CONFIG_PROPERTIES = "dbConfigProperties";
    public static final String DEST_MONGO_DB_CONFIG_PROPERTIES = "destMongoDbConfigProperties";

    public static final String SRC_MONGO_DB_CONFIG_PROPERTIES = "srcMongoDbConfigProperties";

    public static final String TYPE = "type";

    public static final String PARTITION = "partition";
    public static final String SIMPLE = "simple";

    public static final String SRC_MONGO_CLIENT = "srcMongoClient";
    public static final String DEST_MONGO_CLIENT = "destMongoClient";
    public static final String MONGO_DB_CONFIG_PROPERTIES = "mongoDbConfigProperties";
    public static final String SRC_MONGO_TEMPLATE = "srcMongoTemplate";
    public static final String DEST_MONGO_TEMPLATE = "destMongoTemplate";

    private AppConstant() {}
}
