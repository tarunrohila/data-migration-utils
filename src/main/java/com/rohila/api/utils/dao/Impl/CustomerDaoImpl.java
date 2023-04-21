package com.rohila.api.utils.dao.Impl;

import com.rohila.api.utils.dao.CustomerDao;
import com.rohila.api.utils.repository.CustomerRepository;
import com.rohila.api.utils.repository.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Dao to handle requests for address api
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

    /** Autowired dependency for {@link CustomerMongoRepository} */
    @Autowired public CustomerRepository customerMongoRepository;

    /** Autowired dependency for {@link CustomerRepository} */
    @Autowired public CustomerRepository customerRepository;

    @Value("${app.batch.source-db}")
    private String sourceDb;

    /**
     * Method to add or update customer
     *
     * @param customer
     * @return customer
     */
    @Override
    public Customer saveOrUpdateCustomer(Customer customer) {
        if (sourceDb.equals("mongodb")) {
            return customerMongoRepository.save(customer);
        } else {
            return customerRepository.save(customer);
        }
    }
}
