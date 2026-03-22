package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.customerResources.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
