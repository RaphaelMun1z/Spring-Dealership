package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	PagedResourcesAssembler<CustomerAddressResponseDTO> assembler;

	public PagedModel<EntityModel<CustomerAddressResponseDTO>> findAll(Pageable pageable) {
		Page<CustomerAddress> customerAddressPage = repository.findAll(pageable);
		Page<CustomerAddressResponseDTO> customerAddressPageDTO = customerAddressPage
				.map(p -> Mapper.modelMapper(p, CustomerAddressResponseDTO.class));
		customerAddressPageDTO
				.map(i -> i.add(linkTo(methodOn(CustomerAddressController.class).findById(i.getId())).withSelfRel()));
		Link link = linkTo(methodOn(CustomerAddressController.class).findAll(pageable.getPageNumber(),
				pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(customerAddressPageDTO, link);
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
