package com.rohila.api.utils.batch.partitioner;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Class file create a partitioner for mongo data
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
public class MongoRangePartitioner implements Partitioner {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoRangePartitioner.class);

    /** Variable declaration for mongoTemplate */
    private MongoTemplate mongoTemplate;

    /** Variable declaration for collectionClass */
    private String collectionName;

    /**
     * Method to create partitions
     *
     * @param gridSize the size of the map to return
     * @return partitions data
     */
    @Override
    public Map<String, ExecutionContext> partition(final int gridSize) {
        LOGGER.info("creating partitions with gridSize = [{}]", gridSize);
        int min = 1;
        int max = (int) getMongoTemplate().count(new Query(), getCollectionName());
        LOGGER.info("No of records = [{}] found based on query criteria = [{}]", max);
        int targetSize = max / gridSize;

        LOGGER.info("Based on the records calculated targetSize = [{}]", targetSize);
        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 0;
        int start = min;
        int end = start + targetSize - 1;
        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            if (end >= max) {
                end = max;
            }
            value.putInt("pageNo", number);
            value.putInt("pageSize", targetSize);
            value.putInt("maxSize", end - start + 1);
            result.put("partition" + number, value);
            LOGGER.info(
                    "Starting value start = [{}] and Ending value end = [{}], pageNo = [{}], pageSize = [{}], maxSize = [{}]",
                    start,
                    end,
                    number,
                    targetSize,
                    end - start + 1);
            start += targetSize;
            end += targetSize;
            number++;
        }
        LOGGER.info("Execution map has been creating with following params = [{}]", result);
        return result;
    }

    /**
     * Method to get mongoTemplate
     *
     * @return mongoTemplate
     */
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    /**
     * Method to set mongoTemplate
     *
     * @param mongoTemplate
     */
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Method to get collectionClass
     *
     * @return collectionClass
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * Method to set collectionClass
     *
     * @param collectionName
     */
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}
