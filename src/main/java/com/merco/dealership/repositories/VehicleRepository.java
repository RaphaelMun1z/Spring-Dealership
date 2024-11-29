package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

}
