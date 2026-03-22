package com.merco.dealership.unittests.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.VehicleRequestDTO;
import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;
import com.merco.dealership.entities.vehicles.Vehicle;
import com.merco.dealership.entities.vehicles.landVehicle.Car;

public class MockVehicle {

    public Vehicle mockEntity() {
        return mockEntity("id1");
    }

    public Vehicle mockEntity(String id) {
        Car car = new Car();
        car.setId(id);
        car.setBrand("Brand - Test" + id);
        car.setModel("Model - Test" + id);
        car.setColor("Color - Test" + id);
        car.setMileage(10000.0);
        car.setWeight(1500.0);
        car.setNumberOfCylinders(4);
        car.setInfotainmentSystem("Infotainment - Test" + id);
        car.setFuelTankCapacity(50.0);
        car.setEnginePower(150.0);
        car.setPassengerCapacity(5);
        car.setSalePrice(80000.0);
        car.setDescription("Description - Test" + id);
        car.setManufactureYear(LocalDate.of(2022, 6, 15));
        car.setBranch(null);

        // Campos obrigatórios para evitar NullPointerException no VehicleResponseDTO
        car.setType(VehicleType.CAR);
        car.setCategory(VehicleCategory.SEDAN);
        car.setFuelType(FuelType.GASOLINE);
        car.setStatus(VehicleStatus.NEW);
        car.setAvailability(VehicleAvailability.AVAILABLE);

        return car;
    }

    public List<Vehicle> mockEntityList() {
        List<Vehicle> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public VehicleRequestDTO mockRequestDTO() {
        return mockRequestDTO("branchId1");
    }

    public VehicleRequestDTO mockRequestDTO(String branchId) {
        VehicleRequestDTO dto = new VehicleRequestDTO();
        dto.setType("CAR");
        dto.setBrand("Brand - Test");
        dto.setModel("Model - Test");
        dto.setColor("Color - Test");
        dto.setMileage(10000.0);
        dto.setWeight(1500.0);
        dto.setNumberOfCylinders(4);
        dto.setInfotainmentSystem("Infotainment - Test");
        dto.setFuelTankCapacity(50.0);
        dto.setEnginePower(150.0);
        dto.setPassengerCapacity(5);
        dto.setSalePrice(80000.0);
        dto.setDescription("Description - Test");
        dto.setManufactureYear(LocalDate.of(2022, 6, 15));
        dto.setCategory("SEDAN");
        dto.setFuelType("GASOLINE");
        dto.setStatus("NEW");
        dto.setAvailability("AVAILABLE");
        dto.setBranchId(branchId);
        return dto;
    }
}