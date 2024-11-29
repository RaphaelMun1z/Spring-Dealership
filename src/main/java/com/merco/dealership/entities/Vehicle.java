package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_vehicles")
public class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	private String brand;

	@NotNull(message = "Required field")
	private String model;

	@NotNull(message = "Required field")
	private String type;

	@NotNull(message = "Required field")
	private String category;

	@NotNull(message = "Required field")
	private LocalDate manufactureYear;

	@NotNull(message = "Required field")
	private String color;

	@NotNull(message = "Required field")
	private Double mileage;

	@NotNull(message = "Required field")
	private String fuelType;

	@NotNull(message = "Required field")
	private String transmissionType;

	private String specificDetails;

	@NotNull(message = "Required field")
	private Double salePrice;

	private String status;

	private String availability;

	@NotNull(message = "Required field")
	private String description;

	private LocalDate lastUpdate;

	@NotNull(message = "Required field")
	private String location;

	private String images;

	public Vehicle() {

	}

	public Vehicle(String id, @NotNull(message = "Required field") String brand,
			@NotNull(message = "Required field") String model, @NotNull(message = "Required field") String type,
			@NotNull(message = "Required field") String category, LocalDate manufactureYear,
			@NotNull(message = "Required field") String color, @NotNull(message = "Required field") Double mileage,
			@NotNull(message = "Required field") String fuelType,
			@NotNull(message = "Required field") String transmissionType, String specificDetails,
			@NotNull(message = "Required field") Double salePrice, String status, String availability,
			@NotNull(message = "Required field") String description, LocalDate lastUpdate,
			@NotNull(message = "Required field") String location, String images) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.type = type;
		this.category = category;
		this.manufactureYear = manufactureYear;
		this.color = color;
		this.mileage = mileage;
		this.fuelType = fuelType;
		this.transmissionType = transmissionType;
		this.specificDetails = specificDetails;
		this.salePrice = salePrice;
		this.status = status;
		this.availability = availability;
		this.description = description;
		this.lastUpdate = lastUpdate;
		this.location = location;
		this.images = images;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
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

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getSpecificDetails() {
		return specificDetails;
	}

	public void setSpecificDetails(String specificDetails) {
		this.specificDetails = specificDetails;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
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

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(id, other.id);
	}

}
