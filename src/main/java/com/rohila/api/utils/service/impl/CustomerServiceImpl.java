package com.rohila.api.utils.service.impl;

import com.rohila.api.utils.dao.CustomerDao;
import com.rohila.api.utils.repository.entity.Customer;
import com.rohila.api.utils.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to handle requests for address api
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    /** Autowired dependency for {@link CustomerDao} */
    @Autowired public CustomerDao dao;

    /**
     * Method to add or update customer
     *
     * @param customer
     * @return customer
     */
    @Override
    public Customer saveOrUpdateCustomer(Customer customer) {
        return dao.saveOrUpdateCustomer(customer);
    }
}
