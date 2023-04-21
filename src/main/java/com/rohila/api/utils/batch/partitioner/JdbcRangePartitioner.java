package com.rohila.api.utils.batch.partitioner;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Class file create a partitioner for jdbc data
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
public class JdbcRangePartitioner implements Partitioner {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcRangePartitioner.class);

    /** Variable declaration for jdbcTemplate */
    private JdbcTemplate jdbcTemplate;

    /** Variable declaration for table */
    private String table;

    private String column;

    /**
     * Method to create partitions
     *
     * @param gridSize the size of the map to return
     * @return partitions data
     */
    @Override
    public Map<String, ExecutionContext> partition(final int gridSize) {
        LOGGER.info("creating partitions with gridSize = [{}]", gridSize);
        int min =
                jdbcTemplate.queryForObject(
                        "SELECT MIN(" + column + ") FROM " + table, Integer.class);

        int max =
                jdbcTemplate.queryForObject(
                        "SELECT MAX(" + column + ") FROM " + table, Integer.class);
        LOGGER.info("No of records = [{}] found based on query criteria = [{}]", max);
        int targetSize = (max - min) / gridSize + 1;

        LOGGER.info("Based on the records calculated targetSize = [{}]", targetSize);
        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 0;
        int start = min;
        int end = start + targetSize - 1;
        int offset = 0;
        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            if (end >= max) {
                end = max;
            }
            value.putInt("offset", offset);
            value.putLong("minValue", start);
            value.putLong("maxValue", end);
            value.putInt("pageNo", number);
            int endOffset = end - start + 1;
            value.putInt("pageSize", endOffset);
            result.put("partition" + number, value);
            LOGGER.info(
                    "Starting value start = [{}] and Ending value end = [{}], pageNo = [{}], pageSize = [{}]",
                    start,
                    end,
                    number,
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
     * Method to get jdbcTemplate
     *
     * @return jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * Method to set jdbcTemplate
     *
     * @param jdbcTemplate
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
