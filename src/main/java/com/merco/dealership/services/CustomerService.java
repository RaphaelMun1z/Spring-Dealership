package com.merco.dealership.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.dto.CustomerResponseDTO;
import com.merco.dealership.entities.Customer;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.CustomerRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository repository;

	public List<CustomerResponseDTO> findAll() {
		return Mapper.modelMapperList(repository.findAll(), CustomerResponseDTO.class);
	}

	public CustomerResponseDTO findById(String id) {
		Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return Mapper.modelMapper(customer, CustomerResponseDTO.class);
	}

	@Transactional
	public Customer create(Customer obj) {
		try {
			Customer Customer = repository.save(obj);
			return Customer;
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
	public Customer patch(String id, Customer obj) {
		try {
			Customer entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Customer Customer = repository.save(entity);
			return Customer;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Customer entity, Customer obj) {
//		if (obj.getName() != null)
//			entity.setName(obj.getName());
//		if (obj.getEmail() != null)
//			entity.setEmail(obj.getEmail());
//		if (obj.getPhone() != null)
//			entity.setPhone(obj.getPhone());
	}
}
