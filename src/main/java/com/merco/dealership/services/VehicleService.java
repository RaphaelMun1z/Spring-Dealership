package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.controllers.VehicleController;
import com.merco.dealership.dto.VehicleResponseDTO;
import com.merco.dealership.entities.Vehicle;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.VehicleRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class VehicleService {
	@Autowired
	private VehicleRepository repository;

	public List<VehicleResponseDTO> findAll() {
		List<VehicleResponseDTO> vehiclesDTO = Mapper.modelMapperList(repository.findAll(), VehicleResponseDTO.class);
		vehiclesDTO.stream().forEach(
				i -> i.add(linkTo(methodOn(VehicleController.class).findById(i.getId())).withSelfRel()));
		return vehiclesDTO;
	}

	public VehicleResponseDTO findById(String id) {
		Vehicle vehicle = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		VehicleResponseDTO vehicleDTO = Mapper.modelMapper(vehicle, VehicleResponseDTO.class);
		vehicleDTO.add(linkTo(methodOn(VehicleController.class).findById(id)).withSelfRel());
		return vehicleDTO;
	}

	@Transactional
	public Vehicle create(Vehicle obj) {
		try {
			Vehicle Vehicle = repository.save(obj);
			return Vehicle;
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
	public Vehicle patch(String id, Vehicle obj) {
		try {
			Vehicle entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Vehicle Vehicle = repository.save(entity);
			return Vehicle;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Vehicle entity, Vehicle obj) {
//		if (obj.getName() != null)
//			entity.setName(obj.getName());
//		if (obj.getEmail() != null)
//			entity.setEmail(obj.getEmail());
//		if (obj.getPhone() != null)
//			entity.setPhone(obj.getPhone());
	}
}
