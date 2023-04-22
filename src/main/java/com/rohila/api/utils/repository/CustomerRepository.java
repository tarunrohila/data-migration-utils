package com.rohila.api.utils.repository;

import com.rohila.api.utils.repository.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface which is used to create a repository for {@link Customer}
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {}
