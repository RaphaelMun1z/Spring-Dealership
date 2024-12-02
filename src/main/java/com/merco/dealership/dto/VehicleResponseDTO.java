package com.merco.dealership.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.entities.Vehicle;
import com.merco.dealership.entities.VehicleConfiguration;
import com.merco.dealership.entities.VehicleImageFile;
import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.TransmissionType;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;

public class VehicleResponseDTO {
	private String id;
	private String brand;
	private String model;
	private VehicleType type;
	private VehicleCategory category;
	private LocalDate manufactureYear;
	private String color;
	private Double mileage;
	private FuelType fuelType;
	private TransmissionType transmissionType;
	private Double salePrice;
	private VehicleStatus status;
	private VehicleAvailability availability;
	private String description;
	private LocalDate lastUpdate;
	private String location;
	private Set<InventoryItem> inventoryItems = new HashSet<>();
	private Set<VehicleConfiguration> specificDetails = new HashSet<>();
	private Set<VehicleImageFile> images = new HashSet<>();

	public VehicleResponseDTO() {
	}

	public VehicleResponseDTO(Vehicle vehicle) {
		this.id = vehicle.getId();
		this.brand = vehicle.getBrand();
		this.model = vehicle.getModel();
		this.type = vehicle.getType();
		this.category = vehicle.getCategory();
		this.manufactureYear = vehicle.getManufactureYear();
		this.color = vehicle.getColor();
		this.mileage = vehicle.getMileage();
		this.fuelType = vehicle.getFuelType();
		this.transmissionType = vehicle.getTransmissionType();
		this.salePrice = vehicle.getSalePrice();
		this.status = vehicle.getStatus();
		this.availability = vehicle.getAvailability();
		this.description = vehicle.getDescription();
		this.lastUpdate = vehicle.getLastUpdate();
		this.location = vehicle.getLocation();
		this.inventoryItems = vehicle.getInventoryItems();
		this.specificDetails = vehicle.getSpecificDetails();
		this.images = vehicle.getImages();
	}

	public String getId() {
		return id;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public VehicleType getType() {
		return type;
	}

	public VehicleCategory getCategory() {
		return category;
	}

	public LocalDate getManufactureYear() {
		return manufactureYear;
	}

	public String getColor() {
		return color;
	}

	public Double getMileage() {
		return mileage;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

	public TransmissionType getTransmissionType() {
		return transmissionType;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public VehicleAvailability getAvailability() {
		return availability;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getLastUpdate() {
		return lastUpdate;
	}

	public String getLocation() {
		return location;
	}

	public Set<InventoryItem> getInventoryItems() {
		return inventoryItems;
	}

	public Set<VehicleConfiguration> getSpecificDetails() {
		return specificDetails;
	}

	public Set<VehicleImageFile> getImages() {
		return images;
	}

}
