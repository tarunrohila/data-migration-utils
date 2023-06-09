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
        Long min = 1L;
        Long max = getMongoTemplate().count(new Query(), getCollectionName());
        LOGGER.info("No of records = [{}] found based on query criteria = [{}]", max);
        Long targetSize = max / gridSize;

        LOGGER.info("Based on the records calculated targetSize = [{}]", targetSize);
        Map<String, ExecutionContext> result = new HashMap<>();

        Long number = 0L;
        Long start = min;
        Long end = start + targetSize - 1;
        int offset = 0;
        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            if (end >= max) {
                end = max;
            }
            value.putInt("offset", offset);
            value.putLong("minValue", start);
            value.putLong("maxValue", end);
            value.putInt("pageNo", number.intValue());
            int endOffset = end.intValue() - start.intValue() + 1;
            value.putInt("pageSize", endOffset);
            result.put("partition" + number, value);
            LOGGER.info(
                    "Starting value start = [{}] and Ending value end = [{}], pageNo = [{}], pageSize = [{}]",
                    start,
                    end,
                    number.intValue(),
                    endOffset);
            offset += endOffset;
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
