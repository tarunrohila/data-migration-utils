package com.rohila.api.utils.dao;

import com.rohila.api.utils.repository.entity.Customer;

/**
 * Dao to handle requests for customer api
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
public interface CustomerDao {

    /**
     * Method to add or update customer
     *
     * @param customer
     * @return customer
     */
    Customer saveOrUpdateCustomer(Customer customer);
}
