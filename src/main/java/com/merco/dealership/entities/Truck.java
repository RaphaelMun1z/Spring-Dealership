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
@Table(name = "tb_trucks")
public class Truck extends LandVehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Double loadCapacity;
	private Integer axles;

	public Truck() {
	}

	public Truck(String id, @NotNull(message = "Required field") String brand,
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
			Set<VehicleConfiguration> specificDetails, Set<VehicleImageFile> images, Integer transmissionType,
			String brakeType, Double groundClearance, Double autonomyRoad, Double autonomyCity, Integer numberOfGears,
			String steeringType, Integer tireSize, Double loadCapacity, Integer axles) {
		super(id, brand, model, type, category, manufactureYear, color, mileage, weight, fuelType, numberOfCylinders,
				infotainmentSystem, fuelTankCapacity, enginePower, passengerCapacity, salePrice, status, availability,
				description, lastUpdate, branch, inventoryItems, specificDetails, images, transmissionType, brakeType,
				groundClearance, autonomyRoad, autonomyCity, numberOfGears, steeringType, tireSize);
		this.loadCapacity = loadCapacity;
		this.axles = axles;
	}

	public Double getLoadCapacity() {
		return loadCapacity;
	}

	public void setLoadCapacity(Double loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	public Integer getAxles() {
		return axles;
	}

	public void setAxles(Integer axles) {
		this.axles = axles;
	}

}
