package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
