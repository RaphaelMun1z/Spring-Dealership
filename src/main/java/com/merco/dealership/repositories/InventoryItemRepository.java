package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.InventoryItem;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {

}
