package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.TransmissionType;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_land_vehicles")
public abstract class LandVehicle extends Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer transmissionType;
	private String brakeType;
	private Double groundClearance;
	private Double autonomyRoad;
	private Double autonomyCity;
	private Integer numberOfGears;
	private String steeringType;
	private Integer tireSize;

	protected LandVehicle() {
	}

	protected LandVehicle(String id, @NotNull(message = "Required field") String brand,
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
			String steeringType, Integer tireSize) {
		super(id, brand, model, type, category, manufactureYear, color, mileage, weight, fuelType, numberOfCylinders,
				infotainmentSystem, fuelTankCapacity, enginePower, passengerCapacity, salePrice, status, availability,
				description, lastUpdate, branch, inventoryItems, specificDetails, images);
		this.transmissionType = transmissionType;
		this.brakeType = brakeType;
		this.groundClearance = groundClearance;
		this.autonomyRoad = autonomyRoad;
		this.autonomyCity = autonomyCity;
		this.numberOfGears = numberOfGears;
		this.steeringType = steeringType;
		this.tireSize = tireSize;
	}

	public TransmissionType getTransmissionType() {
		return TransmissionType.valueOf(transmissionType);
	}

	public void setTransmissionType(TransmissionType transmissionType) {
		if (transmissionType != null) {
			this.transmissionType = transmissionType.getCode();
		}
	}

	public String getBrakeType() {
		return brakeType;
	}

	public void setBrakeType(String brakeType) {
		this.brakeType = brakeType;
	}

	public Double getGroundClearance() {
		return groundClearance;
	}

	public void setGroundClearance(Double groundClearance) {
		this.groundClearance = groundClearance;
	}

	public Double getAutonomyRoad() {
		return autonomyRoad;
	}

	public void setAutonomyRoad(Double autonomyRoad) {
		this.autonomyRoad = autonomyRoad;
	}

	public Double getAutonomyCity() {
		return autonomyCity;
	}

	public void setAutonomyCity(Double autonomyCity) {
		this.autonomyCity = autonomyCity;
	}

	public Integer getNumberOfGears() {
		return numberOfGears;
	}

	public void setNumberOfGears(Integer numberOfGears) {
		this.numberOfGears = numberOfGears;
	}

	public String getSteeringType() {
		return steeringType;
	}

	public void setSteeringType(String steeringType) {
		this.steeringType = steeringType;
	}

	public Integer getTireSize() {
		return tireSize;
	}

	public void setTireSize(Integer tireSize) {
		this.tireSize = tireSize;
	}

}
