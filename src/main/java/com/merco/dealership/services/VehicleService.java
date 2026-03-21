package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.merco.dealership.entities.*;
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
import com.merco.dealership.entities.enums.VehicleAvailability;
import com.merco.dealership.entities.enums.VehicleCategory;
import com.merco.dealership.entities.enums.VehicleStatus;
import com.merco.dealership.entities.enums.VehicleType;
import com.merco.dealership.mapper.Mapper;
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
		Page<VehicleResponseDTO> vehiclePageDTO = vehiclePage.map(p -> Mapper.modelMapper(p, VehicleResponseDTO.class));
		vehiclePageDTO.map(i -> i.add(linkTo(methodOn(VehicleController.class).findById(i.getId())).withSelfRel()));
		Link link = linkTo(
				methodOn(VehicleController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
				.withSelfRel();
		return assembler.toModel(vehiclePageDTO, link);
	}

	public VehicleResponseDTO findById(String id) {
		Vehicle vehicle = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		VehicleResponseDTO vehicleDTO = Mapper.modelMapper(vehicle, VehicleResponseDTO.class);
		vehicleDTO.add(linkTo(methodOn(VehicleController.class).findById(id)).withSelfRel());
		return vehicleDTO;
	}

	@Transactional
	public VehicleResponseDTO create(VehicleRequestDTO dto) {
		try {
			Vehicle entity = instantiateVehicleByType(dto.getType());
			updateData(entity, dto);
			Vehicle vehicle = repository.save(entity);
			VehicleResponseDTO vehicleDTO = Mapper.modelMapper(vehicle, VehicleResponseDTO.class);
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
			Vehicle entity = repository.getReferenceById(id);
			updateData(entity, dto);
			Vehicle vehicle = repository.save(entity);
			VehicleResponseDTO responseDTO = Mapper.modelMapper(vehicle, VehicleResponseDTO.class);
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
		if (dto.getManufactureYear() != null) entity.setManufactureYear(dto.getManufactureYear());
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
		if (dto.getStatus() != null) entity.setStatus(VehicleStatus.valueOf(dto.getStatus()));
		if (dto.getAvailability() != null) entity.setAvailability(VehicleAvailability.valueOf(dto.getAvailability()));
	}
}