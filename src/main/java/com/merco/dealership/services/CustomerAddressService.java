package com.merco.dealership.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.entities.CustomerAddress;
import com.merco.dealership.repositories.CustomerAddressRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class CustomerAddressService {
	@Autowired
	private CustomerAddressRepository repository;

	public List<CustomerAddress> findAllCached() {
		return findAll();
	}

	public List<CustomerAddress> findAll() {
		return repository.findAll();
	}

	public CustomerAddress findById(String id) {
		Optional<CustomerAddress> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public CustomerAddress create(CustomerAddress obj) {
		try {
			CustomerAddress CustomerAddress = repository.save(obj);
			return CustomerAddress;
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
	public CustomerAddress patch(String id, CustomerAddress obj) {
		try {
			CustomerAddress entity = repository.getReferenceById(id);
			updateData(entity, obj);
			CustomerAddress CustomerAddress = repository.save(entity);
			return CustomerAddress;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(CustomerAddress entity, CustomerAddress obj) {
//		if (obj.getName() != null)
//			entity.setName(obj.getName());
//		if (obj.getEmail() != null)
//			entity.setEmail(obj.getEmail());
//		if (obj.getPhone() != null)
//			entity.setPhone(obj.getPhone());
	}
}
