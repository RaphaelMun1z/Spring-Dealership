package com.merco.dealership.dto.req;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class VehicleRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "A marca é obrigatória")
    private String brand;

    @NotBlank(message = "O modelo é obrigatório")
    private String model;

    @NotNull(message = "O tipo do veículo é obrigatório")
    private String type;

    @NotNull(message = "A categoria é obrigatória")
    private String category;

    @NotNull(message = "O ano de fabricação é obrigatório")
    private LocalDate manufactureYear;

    @NotBlank(message = "A cor é obrigatória")
    private String color;

    @NotNull(message = "A quilometragem é obrigatória")
    @PositiveOrZero(message = "A quilometragem não pode ser negativa")
    private Double mileage;

    @NotNull(message = "O peso é obrigatório")
    @Positive(message = "O peso deve ser maior que zero")
    private Double weight;

    @NotNull(message = "O tipo de combustível é obrigatório")
    private String fuelType;

    @NotNull(message = "O número de cilindros é obrigatório")
    @Positive(message = "O número de cilindros deve ser maior que zero")
    private Integer numberOfCylinders;

    @NotBlank(message = "O sistema de infotainment é obrigatório")
    private String infotainmentSystem;

    @NotNull(message = "A capacidade do tanque é obrigatória")
    @Positive(message = "A capacidade do tanque deve ser maior que zero")
    private Double fuelTankCapacity;

    @NotNull(message = "A potência do motor é obrigatória")
    @Positive(message = "A potência do motor deve ser maior que zero")
    private Double enginePower;

    @NotNull(message = "A capacidade de passageiros é obrigatória")
    @Positive(message = "A capacidade de passageiros deve ser maior que zero")
    private Integer passengerCapacity;

    @NotNull(message = "O preço de venda é obrigatório")
    @PositiveOrZero(message = "O preço não pode ser negativo")
    private Double salePrice;

    private String status;

    private String availability;

    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    @NotBlank(message = "A filial é obrigatória")
    private String branchId;

    private String transmissionType;
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

    public VehicleRequestDTO() {
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getManufactureYear() { return manufactureYear; }
    public void setManufactureYear(LocalDate manufactureYear) { this.manufactureYear = manufactureYear; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Double getMileage() { return mileage; }
    public void setMileage(Double mileage) { this.mileage = mileage; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

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

    public Double getSalePrice() { return salePrice; }
    public void setSalePrice(Double salePrice) { this.salePrice = salePrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public String getTransmissionType() { return transmissionType; }
    public void setTransmissionType(String transmissionType) { this.transmissionType = transmissionType; }

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