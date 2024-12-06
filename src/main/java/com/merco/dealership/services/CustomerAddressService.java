package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.controllers.CustomerAddressController;
import com.merco.dealership.dto.CustomerAddressResponseDTO;
import com.merco.dealership.entities.CustomerAddress;
import com.merco.dealership.mapper.Mapper;
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

	public List<CustomerAddressResponseDTO> findAll() {
		List<CustomerAddressResponseDTO> customersAddressDTO = Mapper.modelMapperList(repository.findAll(),
				CustomerAddressResponseDTO.class);
		customersAddressDTO.stream().forEach(i -> i
				.add(linkTo(methodOn(CustomerAddressController.class).findById(i.getId())).withSelfRel()));
		return customersAddressDTO;
	}

	public CustomerAddressResponseDTO findById(String id) {
		CustomerAddress customerAddress = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		CustomerAddressResponseDTO customerAddressDTO = Mapper.modelMapper(customerAddress,
				CustomerAddressResponseDTO.class);
		customerAddressDTO.add(linkTo(methodOn(CustomerAddressController.class).findById(id)).withSelfRel());
		return customerAddressDTO;
	}

	@Transactional
	public CustomerAddressResponseDTO create(CustomerAddress obj) {
		try {
			CustomerAddress customerAddress = repository.save(obj);
			CustomerAddressResponseDTO customerAddressDTO = Mapper.modelMapper(customerAddress,
					CustomerAddressResponseDTO.class);
			customerAddressDTO.add(
					linkTo(methodOn(CustomerAddressController.class).findById(customerAddress.getId())).withSelfRel());
			return customerAddressDTO;
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
