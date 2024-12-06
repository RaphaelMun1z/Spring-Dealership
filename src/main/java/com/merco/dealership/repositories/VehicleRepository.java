package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.Vehicle;

public interface VehicleRepository<T extends Vehicle> extends JpaRepository<T, String> {
	
}
