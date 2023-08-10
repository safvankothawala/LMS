package com.lms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.app.entity.Customer;

/**
 * Repository for Customer Entity
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByCustomerName(String customerName);

	Customer findByCustomerIdentity(String customerIdentity);
}
