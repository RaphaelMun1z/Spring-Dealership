package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, String> {

}
