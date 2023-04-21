package com.rohila.api.utils.batch.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Class which is used to create a custom reader builder for mongo items
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since 17-04-2023 23:37
 */
public class CustomMongoItemReaderBuilder<T> {

    /** Variable declaration for page */
    protected int page = 0;
    /** Variable declaration for pageSize */
    protected int pageSize = 10;
    /** Variable declaration for template */
    private MongoOperations template;
    /** Variable declaration for jsonQuery */
    private String jsonQuery;
    /** Variable declaration for targetType */
    private Class<? extends T> targetType;
    /** Variable declaration for sorts */
    private Map<String, Sort.Direction> sorts;
    /** Variable declaration for hint */
    private String hint;
    /** Variable declaration for fields */
    private String fields;
    /** Variable declaration for collection */
    private String collection;
    /** Variable declaration for parameterValues */
    private List<Object> parameterValues = new ArrayList<>();
    /** Variable declaration for saveState */
    private boolean saveState = true;

    /** Variable declaration for name */
    private String name;

    /** Variable declaration for maxItemCount */
    private int maxItemCount = Integer.MAX_VALUE;

    /** Variable declaration for currentItemCount */
    private int currentItemCount;

    /** Variable declaration for query */
    private Query query;

    /**
     * Configure if the state of the {@link org.springframework.batch.item.ItemStreamSupport} should
     * be persisted within the {@link org.springframework.batch.item.ExecutionContext} for restart
     * purposes.
     *
     * @param saveState defaults to true
     * @return The current instance of the builder.
     */
    public CustomMongoItemReaderBuilder<T> saveState(boolean saveState) {
        this.saveState = saveState;

        return this;
    }

    /**
     * The name used to calculate the key within the {@link
     * org.springframework.batch.item.ExecutionContext}. Required if {@link #saveState(boolean)} is
     * set to true.
     *
     * @param name name of the reader instance
     * @return The current instance of the builder.
     * @see org.springframework.batch.item.ItemStreamSupport#setName(String)
     */
    public CustomMongoItemReaderBuilder<T> name(String name) {
        this.name = name;

        return this;
    }

    /**
     * Configure the max number of items to be read.
     *
     * @param maxItemCount the max items to be read
     * @return The current instance of the builder.
     * @see
     *     org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader#setMaxItemCount(int)
     */
    public CustomMongoItemReaderBuilder<T> maxItemCount(int maxItemCount) {
        this.maxItemCount = maxItemCount;

        return this;
    }

    /**
     * Index for the current item. Used on restarts to indicate where to start from.
     *
     * @param currentItemCount current index
     * @return this instance for method chaining
     * @see
     *     org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader#setCurrentItemCount(int)
     */
    public CustomMongoItemReaderBuilder<T> currentItemCount(int currentItemCount) {
        this.currentItemCount = currentItemCount;

        return this;
    }

    /**
     * Used to perform operations against the MongoDB instance. Also handles the mapping of
     * documents to objects.
     *
     * @param template the MongoOperations instance to use
     * @return The current instance of the builder
     * @see MongoOperations
     * @see CustomMongoItemReader#setTemplate(MongoOperations)
     */
    public CustomMongoItemReaderBuilder<T> template(MongoOperations template) {
        this.template = template;

        return this;
    }

    /**
     * A JSON formatted MongoDB jsonQuery. Parameterization of the provided jsonQuery is allowed via
     * ?&lt;index&gt; placeholders where the &lt;index&gt; indicates the index of the parameterValue
     * to substitute.
     *
     * @param query JSON formatted Mongo jsonQuery
     * @return The current instance of the builder
     * @see CustomMongoItemReader#setQuery(String)
     */
    public CustomMongoItemReaderBuilder<T> jsonQuery(String query) {
        this.jsonQuery = query;

        return this;
    }

    /**
     * The type of object to be returned for each {@link CustomMongoItemReader#read()} call.
     *
     * @param targetType the type of object to return
     * @return The current instance of the builder
     * @see CustomMongoItemReader#setTargetType(Class)
     */
    public CustomMongoItemReaderBuilder<T> targetType(Class<? extends T> targetType) {
        this.targetType = targetType;

        return this;
    }

    /**
     * {@link List} of values to be substituted in for each of the parameters in the query.
     *
     * @param parameterValues values
     * @return The current instance of the builder
     * @see CustomMongoItemReader#setParameterValues(List)
     */
    public CustomMongoItemReaderBuilder<T> parameterValues(List<Object> parameterValues) {
        this.parameterValues = parameterValues;

        return this;
    }

    /**
     * Values to be substituted in for each of the parameters in the query.
     *
     * @param parameterValues values
     * @return The current instance of the builder
     * @see CustomMongoItemReader#setParameterValues(List)
     */
    public CustomMongoItemReaderBuilder<T> parameterValues(Object... parameterValues) {
        return parameterValues(Arrays.asList(parameterValues));
    }

    /**
     * JSON defining the fields to be returned from the matching documents by MongoDB.
     *
     * @param fields JSON string that identifies the fields to sort by.
     * @return The current instance of the builder
     * @see CustomMongoItemReader#setFields(String)
     */
    public CustomMongoItemReaderBuilder<T> fields(String fields) {
        this.fields = fields;

        return this;
    }

    /**
     * {@link Map} of property names/{@link org.springframework.data.domain.Sort.Direction} values
     * to sort the input by.
     *
     * @param sorts map of properties and direction to sort each.
     * @return The current instance of the builder
     * @see CustomMongoItemReader#setSort(Map)
     */
    public CustomMongoItemReaderBuilder<T> sorts(Map<String, Sort.Direction> sorts) {
        this.sorts = sorts;

        return this;
    }

    /**
     * Establish an optional collection that can be queried.
     *
     * @param collection Mongo collection to be queried.
     * @return The current instance of the builder
     * @see CustomMongoItemReader#setCollection(String)
     */
    public CustomMongoItemReaderBuilder<T> collection(String collection) {
        this.collection = collection;

        return this;
    }

    /**
     * JSON String telling MongoDB what index to use.
     *
     * @param hint string indicating what index to use.
     * @return The current instance of the builder
     * @see CustomMongoItemReader#setHint(String)
     */
    public CustomMongoItemReaderBuilder<T> hint(String hint) {
        this.hint = hint;

        return this;
    }

    /**
     * The number of items to be read with each page.
     *
     * @param page the number of items
     * @return this instance for method chaining
     * @see CustomMongoItemReader#setPageSize(int)
     */
    public CustomMongoItemReaderBuilder<T> page(int page) {
        this.page = page;

        return this;
    }

    /**
     * The number of items to be read with each page.
     *
     * @param pageSize the number of items
     * @return this instance for method chaining
     * @see CustomMongoItemReader#setPageSize(int)
     */
    public CustomMongoItemReaderBuilder<T> pageSize(int pageSize) {
        this.pageSize = pageSize;

        return this;
    }

    /**
     * Provide a Spring Data Mongo {@link Query}. This will take precedence over a JSON configured
     * query.
     *
     * @param query Query to execute
     * @return this instance for method chaining
     * @see CustomMongoItemReader#setQuery(Query)
     */
    public CustomMongoItemReaderBuilder<T> query(Query query) {
        this.query = query;

        return this;
    }

    /**
     * Validates and builds a {@link CustomMongoItemReader}.
     *
     * @return a {@link CustomMongoItemReader}
     */
    public CustomMongoItemReader<T> build() {
        Assert.notNull(this.template, "template is required.");
        if (this.saveState) {
            Assert.hasText(this.name, "A name is required when saveState is set to true");
        }
        Assert.notNull(this.targetType, "targetType is required.");
        Assert.state(
                StringUtils.hasText(this.jsonQuery) || this.query != null, "A query is required");

        if (StringUtils.hasText(this.jsonQuery) || this.query != null) {
            Assert.notNull(this.sorts, "sorts map is required.");
        }

        CustomMongoItemReader<T> reader = new CustomMongoItemReader<>();
        reader.setTemplate(this.template);
        reader.setTargetType(this.targetType);
        reader.setQuery(this.jsonQuery);
        reader.setSort(this.sorts);
        reader.setHint(this.hint);
        reader.setFields(this.fields);
        reader.setCollection(this.collection);
        reader.setParameterValues(this.parameterValues);
        reader.setQuery(this.query);

        reader.setPageSize(this.pageSize);
        reader.setName(this.name);
        reader.setSaveState(this.saveState);
        reader.setCurrentItemCount(this.currentItemCount);
        reader.setMaxItemCount(this.maxItemCount);
        reader.setPage(this.page);

        return reader;
    }
} // CustomCustomMongoItemReaderBuilder
/* @@_END: CLASS DEFINITION --------------------------------------*/
