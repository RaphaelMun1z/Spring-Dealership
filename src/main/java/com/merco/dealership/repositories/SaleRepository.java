package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, String> {

}
