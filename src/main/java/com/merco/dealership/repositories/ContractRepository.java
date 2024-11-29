package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.Contract;

public interface ContractRepository extends JpaRepository<Contract, String> {

}
