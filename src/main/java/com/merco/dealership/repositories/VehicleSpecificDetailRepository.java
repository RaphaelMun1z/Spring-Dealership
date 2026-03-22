package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.vehicles.details.VehicleSpecificDetail;

public interface VehicleSpecificDetailRepository extends JpaRepository<VehicleSpecificDetail, String> {

}
