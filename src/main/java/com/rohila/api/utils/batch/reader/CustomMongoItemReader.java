package com.rohila.api.utils.batch.reader;

import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

/**
 * Class which is used to create a custom reader for mongo items
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since 17-04-2023 16:08
 */
public class CustomMongoItemReader<T> extends MongoItemReader<T> {
    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMongoItemReader.class);
    /** lock declaration */
    private final Object lock = new Object();
    /** counter declaration */
    private final AtomicInteger counter = new AtomicInteger(0);

    /** Constructor declaration */
    public CustomMongoItemReader() {
        super();
        setName(ClassUtils.getShortName(CustomMongoItemReader.class));
    }

    /**
     * method to set page
     *
     * @return
     */
    public void setPage(int page) {
        super.page = page;
    }

    /**
     * overridden method to read the items
     *
     * @return item
     * @throws Exception
     */
    @Nullable
    @Override
    protected T doRead() throws Exception {

        synchronized (lock) {
            if (results == null || !results.hasNext()) {

                LOGGER.debug(
                        "Reading the data with pagination from mongodb for page = [{}], pageSize = [{}]",
                        page,
                        pageSize);
                results = doPageRead();

                if (results == null || !results.hasNext()) {
                    LOGGER.debug("No Data Found for page = [{}], pageSize = [{}]", page, pageSize);
                    return null;
                }
            }

            if (results.hasNext() && counter.get() < pageSize) {
                counter.getAndIncrement();
                return results.next();
            } else {
                return null;
            }
        }
    }
}
