package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_boats")
public class Boat extends Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Double length;
	private String hullMaterial;
	private Double autonomy;

	public Boat() {
	}

	public Boat(String id, @NotNull(message = "Required field") String brand,
			@NotNull(message = "Required field") String model, @NotNull(message = "Required field") VehicleType type,
			@NotNull(message = "Required field") VehicleCategory category,
			@NotNull(message = "Required field") LocalDate manufactureYear,
			@NotNull(message = "Required field") String color, @NotNull(message = "Required field") Double mileage,
			@NotNull(message = "Required field") Double weight, @NotNull(message = "Required field") FuelType fuelType,
			@NotNull(message = "Required field") Integer numberOfCylinders,
			@NotNull(message = "Required field") String infotainmentSystem,
			@NotNull(message = "Required field") Double fuelTankCapacity,
			@NotNull(message = "Required field") Double enginePower,
			@NotNull(message = "Required field") Integer passengerCapacity,
			@NotNull(message = "Required field") Double salePrice, VehicleStatus status,
			VehicleAvailability availability, @NotNull(message = "Required field") String description,
			LocalDate lastUpdate, @NotNull(message = "Required field") Branch branch, Set<InventoryItem> inventoryItems,
			Set<VehicleConfiguration> specificDetails, Set<VehicleImageFile> images) {
		super(id, brand, model, type, category, manufactureYear, color, mileage, weight, fuelType, numberOfCylinders,
				infotainmentSystem, fuelTankCapacity, enginePower, passengerCapacity, salePrice, status, availability,
				description, lastUpdate, branch, inventoryItems, specificDetails, images);
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public String getHullMaterial() {
		return hullMaterial;
	}

	public void setHullMaterial(String hullMaterial) {
		this.hullMaterial = hullMaterial;
	}

	public Double getAutonomy() {
		return autonomy;
	}

	public void setAutonomy(Double autonomy) {
		this.autonomy = autonomy;
	}

}
