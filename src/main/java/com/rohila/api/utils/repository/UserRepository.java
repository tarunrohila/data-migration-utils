package com.rohila.api.utils.repository;

import com.rohila.api.utils.repository.entity.Customer;
import com.rohila.api.utils.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface which is used to create a repository for {@link Customer}
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
