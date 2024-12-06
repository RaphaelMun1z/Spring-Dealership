package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_vehicles")
public abstract class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	private String brand;

	@NotNull(message = "Required field")
	private String model;

	@NotNull(message = "Required field")
	private Integer type;

	@NotNull(message = "Required field")
	private Integer category;

	@NotNull(message = "Required field")
	private LocalDate manufactureYear;

	@NotNull(message = "Required field")
	private String color;

	@NotNull(message = "Required field")
	private Double mileage;

	@NotNull(message = "Required field")
	private Double weight;

	@NotNull(message = "Required field")
	private Integer fuelType;

	@NotNull(message = "Required field")
	private Integer numberOfCylinders;

	@NotNull(message = "Required field")
	private String infotainmentSystem;

	@NotNull(message = "Required field")
	private Double fuelTankCapacity;

	@NotNull(message = "Required field")
	private Double enginePower;

	@NotNull(message = "Required field")
	private Integer passengerCapacity;

	@NotNull(message = "Required field")
	private Double salePrice;

	private Integer status;

	private Integer availability;

	@NotNull(message = "Required field")
	private String description;

	private LocalDate lastUpdate;

	@JsonIgnore
	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "branch_id")
	private Branch branch;

	@JsonIgnore
	@OneToMany(mappedBy = "vehicle")
	private Set<InventoryItem> inventoryItems = new HashSet<>();

	@OneToMany(mappedBy = "id.vehicle", orphanRemoval = true)
	private Set<VehicleConfiguration> specificDetails = new HashSet<>();

	@OneToMany(mappedBy = "vehicle")
	private Set<VehicleImageFile> images = new HashSet<>();

	protected Vehicle() {
	}

	protected Vehicle(String id, @NotNull(message = "Required field") String brand,
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
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.manufactureYear = manufactureYear;
		this.color = color;
		this.mileage = mileage;
		this.weight = weight;
		this.numberOfCylinders = numberOfCylinders;
		this.infotainmentSystem = infotainmentSystem;
		this.fuelTankCapacity = fuelTankCapacity;
		this.enginePower = enginePower;
		this.passengerCapacity = passengerCapacity;
		this.salePrice = salePrice;
		this.description = description;
		this.lastUpdate = lastUpdate;
		this.branch = branch;
		this.inventoryItems = inventoryItems;
		this.specificDetails = specificDetails;
		this.images = images;
		setType(type);
		setCategory(category);
		setFuelType(fuelType);
		setStatus(status);
		setAvailability(availability);
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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getNumberOfCylinders() {
		return numberOfCylinders;
	}

	public void setNumberOfCylinders(Integer numberOfCylinders) {
		this.numberOfCylinders = numberOfCylinders;
	}

	public String getInfotainmentSystem() {
		return infotainmentSystem;
	}

	public void setInfotainmentSystem(String infotainmentSystem) {
		this.infotainmentSystem = infotainmentSystem;
	}

	public Double getFuelTankCapacity() {
		return fuelTankCapacity;
	}

	public void setFuelTankCapacity(Double fuelTankCapacity) {
		this.fuelTankCapacity = fuelTankCapacity;
	}

	public Double getEnginePower() {
		return enginePower;
	}

	public void setEnginePower(Double enginePower) {
		this.enginePower = enginePower;
	}

	public Integer getPassengerCapacity() {
		return passengerCapacity;
	}

	public void setPassengerCapacity(Integer passengerCapacity) {
		this.passengerCapacity = passengerCapacity;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public void setInventoryItems(Set<InventoryItem> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	public void setSpecificDetails(Set<VehicleConfiguration> specificDetails) {
		this.specificDetails = specificDetails;
	}

	public void setImages(Set<VehicleImageFile> images) {
		this.images = images;
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
