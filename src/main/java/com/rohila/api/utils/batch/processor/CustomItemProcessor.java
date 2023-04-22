package com.rohila.api.utils.batch.processor;

import com.rohila.api.utils.repository.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Class file create a processor for data
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
public class CustomItemProcessor implements ItemProcessor<Customer, Customer> {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomItemProcessor.class);

    /**
     * Process the provided item, returning a potentially modified or new item for continued
     * processing. If the returned result is {@code null}, it is assumed that processing of the item
     * should not continue.
     *
     * <p>A {@code null} item will never reach this method because the only possible sources are:
     *
     * <ul>
     *   <li>an ItemReader (which indicates no more items)
     *   <li>a previous {@link ItemProcessor} in a composite processor (which indicates a filtered
     *       item)
     * </ul>
     *
     * @param customer to be processed, never {@code null}.
     * @return potentially modified or new item for continued processing, {@code null} if processing
     *     of the provided item should not continue.
     * @throws Exception thrown if exception occurs during processing.
     */
    @Override
    public Customer process(Customer customer) throws Exception {
        return customer;
    }
}
