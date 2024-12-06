package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.TransmissionType;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	private Integer type = 1;

	@NotNull(message = "Required field")
	private Integer category = 1;

	@NotNull(message = "Required field")
	private LocalDate manufactureYear;

	@NotNull(message = "Required field")
	private String color;

	@NotNull(message = "Required field")
	private Double mileage;

	@NotNull(message = "Required field")
	private Integer fuelType = 1;

	@NotNull(message = "Required field")
	private Integer transmissionType = 1;

	@NotNull(message = "Required field")
	private Double salePrice;

	private Integer status = 1;

	private Integer availability = 1;

	@NotNull(message = "Required field")
	private String description;

	private LocalDate lastUpdate;

	@NotNull(message = "Required field")
	private String location;

	@JsonIgnore
	@OneToMany(mappedBy = "vehicle")
	private Set<InventoryItem> inventoryItems = new HashSet<>();

	@OneToMany(mappedBy = "id.vehicle", orphanRemoval = true)
	private Set<VehicleConfiguration> specificDetails = new HashSet<>();

	@OneToMany(mappedBy = "vehicle")
	private Set<VehicleImageFile> images = new HashSet<>();

	public Vehicle() {

	}

	public Vehicle(String id, @NotNull(message = "Required field") String brand,
			@NotNull(message = "Required field") String model, @NotNull(message = "Required field") VehicleType type,
			@NotNull(message = "Required field") VehicleCategory category, LocalDate manufactureYear,
			@NotNull(message = "Required field") String color, @NotNull(message = "Required field") Double mileage,
			@NotNull(message = "Required field") FuelType fuelType,
			@NotNull(message = "Required field") TransmissionType transmissionType,
			@NotNull(message = "Required field") Double salePrice, VehicleStatus status,
			VehicleAvailability availability, @NotNull(message = "Required field") String description,
			LocalDate lastUpdate, @NotNull(message = "Required field") String location) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		setType(type);
		setCategory(category);
		this.manufactureYear = manufactureYear;
		this.color = color;
		this.mileage = mileage;
		setFuelType(fuelType);
		setTransmissionType(transmissionType);
		this.salePrice = salePrice;
		setStatus(status);
		setAvailability(availability);
		this.description = description;
		this.lastUpdate = lastUpdate;
		this.location = location;
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
		return VehicleType.valueOf(type);
	}

	public void setType(VehicleType vehicleType) {
		if (vehicleType != null) {
			this.type = vehicleType.getCode();
		}
	}

	public VehicleCategory getCategory() {
		return VehicleCategory.valueOf(category);
	}

	public void setCategory(VehicleCategory vehicleCategory) {
		if (vehicleCategory != null) {
			this.category = vehicleCategory.getCode();
		}
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
		return FuelType.valueOf(fuelType);
	}

	public void setFuelType(FuelType fuelType) {
		if (fuelType != null) {
			this.fuelType = fuelType.getCode();
		}
	}

	public TransmissionType getTransmissionType() {
		return TransmissionType.valueOf(transmissionType);
	}

	public void setTransmissionType(TransmissionType transmissionType) {
		if (transmissionType != null) {
			this.transmissionType = transmissionType.getCode();
		}
	}

	public Set<VehicleConfiguration> getSpecificDetails() {
		return specificDetails;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public VehicleStatus getStatus() {
		return VehicleStatus.valueOf(status);
	}

	public void setStatus(VehicleStatus status) {
		if (status != null) {
			this.status = status.getCode();
		}
	}

	public VehicleAvailability getAvailability() {
		return VehicleAvailability.valueOf(availability);
	}

	public void setAvailability(VehicleAvailability availability) {
		if (availability != null) {
			this.availability = availability.getCode();
		}
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

	public Set<VehicleImageFile> getImages() {
		return images;
	}

	public Set<InventoryItem> getInventoryItems() {
		return inventoryItems;
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
