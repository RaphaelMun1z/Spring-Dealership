package com.merco.dealership.repositories;

import com.merco.dealership.entities.vehicles.details.VehicleImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleImageFileRepository extends JpaRepository<VehicleImageFile, String> {
}