package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

import com.merco.dealership.entities.*;
import com.merco.dealership.entities.vehicles.*;
import com.merco.dealership.entities.vehicles.landVehicle.*;
import com.merco.dealership.entities.vehicles.waterVehicle.Boat;
import com.merco.dealership.repositories.BranchRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.controllers.VehicleController;
import com.merco.dealership.dto.req.VehicleRequestDTO;
import com.merco.dealership.dto.res.VehicleResponseDTO;
import com.merco.dealership.entities.enums.FuelType;
import com.merco.dealership.entities.enums.TransmissionType;
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;
import com.merco.dealership.repositories.VehicleRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class VehicleService {

	private final VehicleRepository<Vehicle> repository;
	private final BranchRepository branchRepository;
	private final PagedResourcesAssembler<VehicleResponseDTO> assembler;

	public VehicleService(VehicleRepository<Vehicle> repository, BranchRepository branchRepository, PagedResourcesAssembler<VehicleResponseDTO> assembler) {
		this.repository = repository;
		this.branchRepository = branchRepository;
		this.assembler = assembler;
	}

	@Transactional(readOnly = true)
	public PagedModel<EntityModel<VehicleResponseDTO>> findAll(Pageable pageable) {
		Page<Vehicle> vehiclePage = repository.findAll(pageable);
		Page<VehicleResponseDTO> vehiclePageDTO = vehiclePage.map(v -> {
			forceInitializeCollections(v);
			return new VehicleResponseDTO(v);
		});
		vehiclePageDTO.map(i -> i.add(linkTo(methodOn(VehicleController.class).findById(i.getId())).withSelfRel()));
		Link link = linkTo(
				methodOn(VehicleController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
				.withSelfRel();
		return assembler.toModel(vehiclePageDTO, link);
	}

	@Transactional(readOnly = true)
	public VehicleResponseDTO findById(String id) {
		Vehicle vehicle = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		forceInitializeCollections(vehicle);
		VehicleResponseDTO vehicleDTO = new VehicleResponseDTO(vehicle);
		vehicleDTO.add(linkTo(methodOn(VehicleController.class).findById(id)).withSelfRel());
		return vehicleDTO;
	}

	@Transactional
	public VehicleResponseDTO create(VehicleRequestDTO dto) {
		try {
			Vehicle entity = instantiateVehicleByType(dto.getType());
			updateData(entity, dto);
			Vehicle vehicle = repository.save(entity);
			forceInitializeCollections(vehicle);
			VehicleResponseDTO vehicleDTO = new VehicleResponseDTO(vehicle);
			vehicleDTO.add(linkTo(methodOn(VehicleController.class).findById(vehicle.getId())).withSelfRel());
			return vehicleDTO;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Transactional
	public VehicleResponseDTO patch(String id, VehicleRequestDTO dto) {
		try {
			Vehicle entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
			updateData(entity, dto);
			Vehicle vehicle = repository.save(entity);
			forceInitializeCollections(vehicle);
			VehicleResponseDTO responseDTO = new VehicleResponseDTO(vehicle);
			responseDTO.add(linkTo(methodOn(VehicleController.class).findById(vehicle.getId())).withSelfRel());
			return responseDTO;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void forceInitializeCollections(Vehicle vehicle) {
		if (vehicle.getImages() != null) {
			vehicle.getImages().size();
		}
		if (vehicle.getSpecificDetails() != null) {
			vehicle.getSpecificDetails().size();
		}
	}

	private Vehicle instantiateVehicleByType(String typeStr) {
		if (typeStr == null) throw new IllegalArgumentException();
		return switch (typeStr.toUpperCase()) {
			case "CAR" -> new Car();
			case "MOTORCYCLE" -> new Motorcycle();
			case "VAN" -> new Van();
			case "TRUCK" -> new Truck();
			case "BUS" -> new Bus();
			case "BOAT" -> new Boat();
			default -> new OtherVehicleType();
		};
	}

	private void updateData(Vehicle entity, VehicleRequestDTO dto) {
		if (dto.getBranchId() != null) {
			Branch branch = branchRepository.findById(dto.getBranchId())
					.orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada"));
			entity.setBranch(branch);
		}

		if (dto.getBrand() != null) entity.setBrand(dto.getBrand());
		if (dto.getModel() != null) entity.setModel(dto.getModel());
		if (dto.getManufactureYear() != null) {
			entity.setManufactureYear(LocalDate.of(dto.getManufactureYear().getYear(), 6, 15));
		}
		if (dto.getColor() != null) entity.setColor(dto.getColor());
		if (dto.getMileage() != null) entity.setMileage(dto.getMileage());
		if (dto.getWeight() != null) entity.setWeight(dto.getWeight());
		if (dto.getNumberOfCylinders() != null) entity.setNumberOfCylinders(dto.getNumberOfCylinders());
		if (dto.getInfotainmentSystem() != null) entity.setInfotainmentSystem(dto.getInfotainmentSystem());
		if (dto.getFuelTankCapacity() != null) entity.setFuelTankCapacity(dto.getFuelTankCapacity());
		if (dto.getEnginePower() != null) entity.setEnginePower(dto.getEnginePower());
		if (dto.getPassengerCapacity() != null) entity.setPassengerCapacity(dto.getPassengerCapacity());
		if (dto.getSalePrice() != null) entity.setSalePrice(dto.getSalePrice());
		if (dto.getDescription() != null) entity.setDescription(dto.getDescription());

		if (dto.getType() != null) entity.setType(VehicleType.valueOf(dto.getType()));
		if (dto.getCategory() != null) entity.setCategory(VehicleCategory.valueOf(dto.getCategory()));
		if (dto.getFuelType() != null) entity.setFuelType(FuelType.valueOf(dto.getFuelType()));
		if (dto.getStatus() != null && !dto.getStatus().isBlank()) entity.setStatus(VehicleStatus.valueOf(dto.getStatus()));
		if (dto.getAvailability() != null && !dto.getAvailability().isBlank()) entity.setAvailability(VehicleAvailability.valueOf(dto.getAvailability()));

		if (entity instanceof LandVehicle landVehicle) {
			if (dto.getTransmissionType() != null && !dto.getTransmissionType().isBlank())
				landVehicle.setTransmissionType(TransmissionType.valueOf(dto.getTransmissionType()));
			if (dto.getBrakeType() != null) landVehicle.setBrakeType(dto.getBrakeType());
			if (dto.getGroundClearance() != null) landVehicle.setGroundClearance(dto.getGroundClearance());
			if (dto.getAutonomyRoad() != null) landVehicle.setAutonomyRoad(dto.getAutonomyRoad());
			if (dto.getAutonomyCity() != null) landVehicle.setAutonomyCity(dto.getAutonomyCity());
			if (dto.getNumberOfGears() != null) landVehicle.setNumberOfGears(dto.getNumberOfGears());
			if (dto.getSteeringType() != null) landVehicle.setSteeringType(dto.getSteeringType());
			if (dto.getTireSize() != null) landVehicle.setTireSize(dto.getTireSize());
		}

		if (entity instanceof Car car) {
			if (dto.getDoors() != null) car.setDoors(dto.getDoors());
			if (dto.getTrunkCapacity() != null) car.setTrunkCapacity(dto.getTrunkCapacity());
			if (dto.getDriveType() != null) car.setDriveType(dto.getDriveType());
		} else if (entity instanceof Motorcycle moto) {
			if (dto.getHasLuggageCarrier() != null) moto.setHasLuggageCarrier(dto.getHasLuggageCarrier());
		} else if (entity instanceof Van van) {
			if (dto.getIsCargo() != null) van.setIsCargo(dto.getIsCargo());
			if (dto.getCargoVolume() != null) van.setCargoVolume(dto.getCargoVolume());
		} else if (entity instanceof Truck truck) {
			if (dto.getLoadCapacity() != null) truck.setLoadCapacity(dto.getLoadCapacity());
			if (dto.getAxles() != null) truck.setAxles(dto.getAxles());
		} else if (entity instanceof Bus bus) {
			if (dto.getNumberOfSeats() != null) bus.setNumberOfSeats(dto.getNumberOfSeats());
			if (dto.getHasAccessibility() != null) bus.setHasAccessibility(dto.getHasAccessibility());
		} else if (entity instanceof Boat boat) {
			if (dto.getLength() != null) boat.setLength(dto.getLength());
			if (dto.getHullMaterial() != null) boat.setHullMaterial(dto.getHullMaterial());
			if (dto.getAutonomy() != null) boat.setAutonomy(dto.getAutonomy());
		} else if (entity instanceof OtherVehicleType other) {
			if (dto.getUsage() != null) other.setUsage(dto.getUsage());
		}
	}
}