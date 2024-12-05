package com.merco.dealership.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.Vehicle;
import com.merco.dealership.entities.VehicleImageFile;
import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.TransmissionType;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;
import com.merco.dealership.mapper.Mapper;

public class VehicleResponseDTO extends RepresentationModel<VehicleResponseDTO> {
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
	private List<VehicleSpecificDetailResponseDTO> specificDetails = new ArrayList<>();
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
		this.specificDetails = Mapper.modelMapperList(vehicle.getSpecificDetails(),
				VehicleSpecificDetailResponseDTO.class);
		this.images = vehicle.getImages();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public VehicleCategory getCategory() {
		return category;
	}

	public void setCategory(VehicleCategory category) {
		this.category = category;
	}

	public LocalDate getManufactureYear() {
		return manufactureYear;
	}

	public void setManufactureYear(LocalDate manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

	public TransmissionType getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(TransmissionType transmissionType) {
		this.transmissionType = transmissionType;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public void setStatus(VehicleStatus status) {
		this.status = status;
	}

	public VehicleAvailability getAvailability() {
		return availability;
	}

	public void setAvailability(VehicleAvailability availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDate lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<VehicleSpecificDetailResponseDTO> getSpecificDetails() {
		return specificDetails;
	}

	public void setSpecificDetails(List<VehicleSpecificDetailResponseDTO> specificDetails) {
		this.specificDetails = specificDetails;
	}

	public Set<VehicleImageFile> getImages() {
		return images;
	}

	public void setImages(Set<VehicleImageFile> images) {
		this.images = images;
	}

}
