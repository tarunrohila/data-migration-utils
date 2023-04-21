package com.rohila.api.utils.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
// @Document
@Entity
@Table(name = "CUSTOMER")
public class Customer {
    /** variable declaration for id */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    /** variable declaration for name */
    private String name;
    /** variable declaration for active */
    private String active;
}
