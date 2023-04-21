package com.rohila.api.utils.controller;

import com.rohila.api.utils.repository.entity.Customer;
import com.rohila.api.utils.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Controller to handle requests for customer api
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@RestController
public class CustomerController {

    public static final String CUSTOMERS_URI = "/customers";
    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired private CustomerService service;

    @Value("${app.batch.source-db}")
    private String sourceDb;

    @PostMapping(CUSTOMERS_URI)
    public void addCustomers() {

        Executor executor = Executors.newFixedThreadPool(10);
        executor.execute(
                () -> {
                    IntStream.range(0, 1000)
                            .mapToObj(
                                    value ->
                                            Customer.builder()
                                                    .id(Long.valueOf(value))
                                                    .active("true")
                                                    .name(UUID.randomUUID().toString())
                                                    .build())
                            .forEach(
                                    customer -> {
                                        service.saveOrUpdateCustomer(customer);
                                    });
                });
    }
}
