package com.example.springbootcustomermanagement.repository;

import com.example.springbootcustomermanagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
}
