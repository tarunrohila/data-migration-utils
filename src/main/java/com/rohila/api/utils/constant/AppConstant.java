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

    public static final String TYPE = "type";

    public static final String PARTITION = "partition";

    public static final String MONGO_DB_CONFIG_PROPERTIES = "mongoDbConfigProperties";

    private AppConstant() {}
}
