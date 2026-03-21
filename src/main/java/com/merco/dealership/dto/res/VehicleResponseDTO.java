package com.merco.dealership.dto.res;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.*;
import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.TransmissionType;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;
import com.merco.dealership.mapper.Mapper;

public class VehicleResponseDTO extends RepresentationModel<VehicleResponseDTO> {
	private String id;
	private Double weight;
	private Integer numberOfCylinders;
	private String infotainmentSystem;
	private Double fuelTankCapacity;
	private Double enginePower;
	private Integer passengerCapacity;
	private String brand;
	private String model;
	private VehicleType type;
	private VehicleCategory category;
	private LocalDate manufactureYear;
	private String color;
	private Double mileage;
	private FuelType fuelType;
	private Double salePrice;
	private VehicleStatus status;
	private VehicleAvailability availability;
	private String description;
	private LocalDate lastUpdate;
	private Branch branch;
	private List<VehicleSpecificDetailResponseDTO> specificDetails = new ArrayList<>();
	private Set<VehicleImageFile> images = new HashSet<>();

	private TransmissionType transmissionType;
	private String brakeType;
	private Double groundClearance;
	private Double autonomyRoad;
	private Double autonomyCity;
	private Integer numberOfGears;
	private String steeringType;
	private Integer tireSize;

	private Integer doors;
	private Double trunkCapacity;
	private String driveType;

	private Boolean hasLuggageCarrier;

	private Boolean isCargo;
	private Double cargoVolume;

	private Double loadCapacity;
	private Integer axles;

	private Integer numberOfSeats;
	private Boolean hasAccessibility;

	private Double length;
	private String hullMaterial;
	private Double autonomy;

	private String usage;

	public VehicleResponseDTO() {
	}

	public VehicleResponseDTO(Vehicle vehicle) {
		this.id = vehicle.getId();
		this.weight = vehicle.getWeight();
		this.numberOfCylinders = vehicle.getNumberOfCylinders();
		this.infotainmentSystem = vehicle.getInfotainmentSystem();
		this.fuelTankCapacity = vehicle.getFuelTankCapacity();
		this.enginePower = vehicle.getEnginePower();
		this.passengerCapacity = vehicle.getPassengerCapacity();
		this.brand = vehicle.getBrand();
		this.model = vehicle.getModel();
		this.type = vehicle.getType();
		this.category = vehicle.getCategory();
		this.manufactureYear = vehicle.getManufactureYear();
		this.color = vehicle.getColor();
		this.mileage = vehicle.getMileage();
		this.fuelType = vehicle.getFuelType();
		this.salePrice = vehicle.getSalePrice();
		this.status = vehicle.getStatus();
		this.availability = vehicle.getAvailability();
		this.description = vehicle.getDescription();
		this.lastUpdate = vehicle.getLastUpdate();
		this.branch = vehicle.getBranch();
		this.specificDetails = Mapper.modelMapperList(vehicle.getSpecificDetails(),
				VehicleSpecificDetailResponseDTO.class);
		this.images = vehicle.getImages();

		if (vehicle instanceof LandVehicle landVehicle) {
			this.transmissionType = landVehicle.getTransmissionType();
			this.brakeType = landVehicle.getBrakeType();
			this.groundClearance = landVehicle.getGroundClearance();
			this.autonomyRoad = landVehicle.getAutonomyRoad();
			this.autonomyCity = landVehicle.getAutonomyCity();
			this.numberOfGears = landVehicle.getNumberOfGears();
			this.steeringType = landVehicle.getSteeringType();
			this.tireSize = landVehicle.getTireSize();
		}

		if (vehicle instanceof Car car) {
			this.doors = car.getDoors();
			this.trunkCapacity = car.getTrunkCapacity();
			this.driveType = car.getDriveType();
		} else if (vehicle instanceof Motorcycle moto) {
			this.hasLuggageCarrier = moto.getHasLuggageCarrier();
		} else if (vehicle instanceof Van van) {
			this.isCargo = van.getIsCargo();
			this.cargoVolume = van.getCargoVolume();
		} else if (vehicle instanceof Truck truck) {
			this.loadCapacity = truck.getLoadCapacity();
			this.axles = truck.getAxles();
		} else if (vehicle instanceof Bus bus) {
			this.numberOfSeats = bus.getNumberOfSeats();
			this.hasAccessibility = bus.getHasAccessibility();
		} else if (vehicle instanceof Boat boat) {
			this.length = boat.getLength();
			this.hullMaterial = boat.getHullMaterial();
			this.autonomy = boat.getAutonomy();
		} else if (vehicle instanceof OtherVehicleType other) {
			this.usage = other.getUsage();
		}
	}

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public Double getWeight() { return weight; }
	public void setWeight(Double weight) { this.weight = weight; }
	public Integer getNumberOfCylinders() { return numberOfCylinders; }
	public void setNumberOfCylinders(Integer numberOfCylinders) { this.numberOfCylinders = numberOfCylinders; }
	public String getInfotainmentSystem() { return infotainmentSystem; }
	public void setInfotainmentSystem(String infotainmentSystem) { this.infotainmentSystem = infotainmentSystem; }
	public Double getFuelTankCapacity() { return fuelTankCapacity; }
	public void setFuelTankCapacity(Double fuelTankCapacity) { this.fuelTankCapacity = fuelTankCapacity; }
	public Double getEnginePower() { return enginePower; }
	public void setEnginePower(Double enginePower) { this.enginePower = enginePower; }
	public Integer getPassengerCapacity() { return passengerCapacity; }
	public void setPassengerCapacity(Integer passengerCapacity) { this.passengerCapacity = passengerCapacity; }
	public String getBrand() { return brand; }
	public void setBrand(String brand) { this.brand = brand; }
	public String getModel() { return model; }
	public void setModel(String model) { this.model = model; }
	public VehicleType getType() { return type; }
	public void setType(VehicleType type) { this.type = type; }
	public VehicleCategory getCategory() { return category; }
	public void setCategory(VehicleCategory category) { this.category = category; }
	public LocalDate getManufactureYear() { return manufactureYear; }
	public void setManufactureYear(LocalDate manufactureYear) { this.manufactureYear = manufactureYear; }
	public String getColor() { return color; }
	public void setColor(String color) { this.color = color; }
	public Double getMileage() { return mileage; }
	public void setMileage(Double mileage) { this.mileage = mileage; }
	public FuelType getFuelType() { return fuelType; }
	public void setFuelType(FuelType fuelType) { this.fuelType = fuelType; }
	public Double getSalePrice() { return salePrice; }
	public void setSalePrice(Double salePrice) { this.salePrice = salePrice; }
	public VehicleStatus getStatus() { return status; }
	public void setStatus(VehicleStatus status) { this.status = status; }
	public VehicleAvailability getAvailability() { return availability; }
	public void setAvailability(VehicleAvailability availability) { this.availability = availability; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public LocalDate getLastUpdate() { return lastUpdate; }
	public void setLastUpdate(LocalDate lastUpdate) { this.lastUpdate = lastUpdate; }
	public Branch getBranch() { return branch; }
	public void setBranch(Branch branch) { this.branch = branch; }
	public List<VehicleSpecificDetailResponseDTO> getSpecificDetails() { return specificDetails; }
	public void setSpecificDetails(List<VehicleSpecificDetailResponseDTO> specificDetails) { this.specificDetails = specificDetails; }
	public Set<VehicleImageFile> getImages() { return images; }
	public void setImages(Set<VehicleImageFile> images) { this.images = images; }
	public TransmissionType getTransmissionType() { return transmissionType; }
	public void setTransmissionType(TransmissionType transmissionType) { this.transmissionType = transmissionType; }
	public String getBrakeType() { return brakeType; }
	public void setBrakeType(String brakeType) { this.brakeType = brakeType; }
	public Double getGroundClearance() { return groundClearance; }
	public void setGroundClearance(Double groundClearance) { this.groundClearance = groundClearance; }
	public Double getAutonomyRoad() { return autonomyRoad; }
	public void setAutonomyRoad(Double autonomyRoad) { this.autonomyRoad = autonomyRoad; }
	public Double getAutonomyCity() { return autonomyCity; }
	public void setAutonomyCity(Double autonomyCity) { this.autonomyCity = autonomyCity; }
	public Integer getNumberOfGears() { return numberOfGears; }
	public void setNumberOfGears(Integer numberOfGears) { this.numberOfGears = numberOfGears; }
	public String getSteeringType() { return steeringType; }
	public void setSteeringType(String steeringType) { this.steeringType = steeringType; }
	public Integer getTireSize() { return tireSize; }
	public void setTireSize(Integer tireSize) { this.tireSize = tireSize; }
	public Integer getDoors() { return doors; }
	public void setDoors(Integer doors) { this.doors = doors; }
	public Double getTrunkCapacity() { return trunkCapacity; }
	public void setTrunkCapacity(Double trunkCapacity) { this.trunkCapacity = trunkCapacity; }
	public String getDriveType() { return driveType; }
	public void setDriveType(String driveType) { this.driveType = driveType; }
	public Boolean getHasLuggageCarrier() { return hasLuggageCarrier; }
	public void setHasLuggageCarrier(Boolean hasLuggageCarrier) { this.hasLuggageCarrier = hasLuggageCarrier; }
	public Boolean getIsCargo() { return isCargo; }
	public void setIsCargo(Boolean isCargo) { this.isCargo = isCargo; }
	public Double getCargoVolume() { return cargoVolume; }
	public void setCargoVolume(Double cargoVolume) { this.cargoVolume = cargoVolume; }
	public Double getLoadCapacity() { return loadCapacity; }
	public void setLoadCapacity(Double loadCapacity) { this.loadCapacity = loadCapacity; }
	public Integer getAxles() { return axles; }
	public void setAxles(Integer axles) { this.axles = axles; }
	public Integer getNumberOfSeats() { return numberOfSeats; }
	public void setNumberOfSeats(Integer numberOfSeats) { this.numberOfSeats = numberOfSeats; }
	public Boolean getHasAccessibility() { return hasAccessibility; }
	public void setHasAccessibility(Boolean hasAccessibility) { this.hasAccessibility = hasAccessibility; }
	public Double getLength() { return length; }
	public void setLength(Double length) { this.length = length; }
	public String getHullMaterial() { return hullMaterial; }
	public void setHullMaterial(String hullMaterial) { this.hullMaterial = hullMaterial; }
	public Double getAutonomy() { return autonomy; }
	public void setAutonomy(Double autonomy) { this.autonomy = autonomy; }
	public String getUsage() { return usage; }
	public void setUsage(String usage) { this.usage = usage; }
}