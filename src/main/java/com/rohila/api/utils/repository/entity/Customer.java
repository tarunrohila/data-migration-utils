package com.rohila.api.utils.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class which is used to create mongo document for {@link Customer}
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer1")
// @Entity
// @Table(name = "CUSTOMER")
public class Customer {
    /** variable declaration for id */
    //    @Id
    //    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    /** variable declaration for name */
    private String name;
    /** variable declaration for active */
    private Boolean active;
}
