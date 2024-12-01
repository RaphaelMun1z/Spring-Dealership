package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.Branch;

public interface BranchRepository extends JpaRepository<Branch, String> {

}
