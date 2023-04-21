package com.rohila.api.utils.service;

import com.rohila.api.utils.repository.entity.Customer;

/**
 * Service to handle requests for Customer api
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
public interface CustomerService {
    /**
     * Method to add or update customer
     *
     * @param customer
     * @return customer
     */
    Customer saveOrUpdateCustomer(Customer customer);
}
