package com.merco.dealership.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.entities.VehicleSpecificDetail;
import com.merco.dealership.repositories.VehicleSpecificDetailRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class VehicleSpecificDetailService {
	@Autowired
	private VehicleSpecificDetailRepository repository;

	public List<VehicleSpecificDetail> findAllCached() {
		return findAll();
	}

	public List<VehicleSpecificDetail> findAll() {
		return repository.findAll();
	}

	public VehicleSpecificDetail findById(String id) {
		Optional<VehicleSpecificDetail> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public VehicleSpecificDetail create(VehicleSpecificDetail obj) {
		try {
			VehicleSpecificDetail VehicleSpecificDetail = repository.save(obj);
			return VehicleSpecificDetail;
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
	public VehicleSpecificDetail patch(String id, VehicleSpecificDetail obj) {
		try {
			VehicleSpecificDetail entity = repository.getReferenceById(id);
			updateData(entity, obj);
			VehicleSpecificDetail VehicleSpecificDetail = repository.save(entity);
			return VehicleSpecificDetail;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(VehicleSpecificDetail entity, VehicleSpecificDetail obj) {
//		if (obj.getName() != null)
//			entity.setName(obj.getName());
//		if (obj.getEmail() != null)
//			entity.setEmail(obj.getEmail());
//		if (obj.getPhone() != null)
//			entity.setPhone(obj.getPhone());
	}
}
