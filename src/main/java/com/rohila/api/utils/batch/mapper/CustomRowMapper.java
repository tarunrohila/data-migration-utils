package com.rohila.api.utils.batch.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

/**
 * Class which is used to
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since 20-04-2023 15:00
 */
public class CustomRowMapper<S> implements RowMapper<S> {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRowMapper.class);

    /** Variable declaration for class */
    private final Class<S> clazz;

    /** Constructor declaration */
    public CustomRowMapper(Class<S> sourceClazz) {
        this.clazz = sourceClazz;
        LOGGER.debug("Class mapper is created for class = [{}]", this.clazz);
    }

    /**
     * Method to map rows
     *
     * @param rs the {@code ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return mapped object
     * @throws SQLException
     */
    @Override
    public S mapRow(ResultSet rs, int rowNum) throws SQLException {
        return getFieldSet(rs);
    }

    /**
     * Method to get fieldset
     *
     * @param rs
     * @return fieldset
     * @throws SQLException
     */
    private S getFieldSet(final ResultSet rs) throws SQLException {
        LOGGER.debug("Generating fieldset from the resultSet = [{}]", rs);
        final Map<String, Object> fieldSet = new HashMap<>();
        final ResultSetMetaData metaData = rs.getMetaData();
        final int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            fieldSet.put(metaData.getColumnName(i), rs.getString(i));
        }
        return new ObjectMapper().convertValue(fieldSet, this.clazz);
    }
}
