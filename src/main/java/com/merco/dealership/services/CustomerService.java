package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import com.merco.dealership.controllers.CustomerController;
import com.merco.dealership.dto.req.CustomerRequestDTO;
import com.merco.dealership.dto.res.CustomerResponseDTO;
import com.merco.dealership.entities.customerResources.Customer;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.CustomerRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class CustomerService {

	private final CustomerRepository repository;
	private final PagedResourcesAssembler<CustomerResponseDTO> assembler;

	public CustomerService(CustomerRepository repository, PagedResourcesAssembler<CustomerResponseDTO> assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	public PagedModel<EntityModel<CustomerResponseDTO>> findAll(Pageable pageable) {
		Page<Customer> customerPage = repository.findAll(pageable);
		Page<CustomerResponseDTO> customerPageDTO = customerPage
				.map(p -> Mapper.modelMapper(p, CustomerResponseDTO.class));
		customerPageDTO.map(i -> i.add(linkTo(methodOn(CustomerController.class).findById(i.getId())).withSelfRel()));
		Link link = linkTo(
				methodOn(CustomerController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
				.withSelfRel();
		return assembler.toModel(customerPageDTO, link);
	}

	public CustomerResponseDTO findById(String id) {
		Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		CustomerResponseDTO customerDTO = Mapper.modelMapper(customer, CustomerResponseDTO.class);
		customerDTO.add(linkTo(methodOn(CustomerController.class).findById(id)).withSelfRel());
		return customerDTO;
	}

	@Transactional
	public CustomerResponseDTO create(CustomerRequestDTO obj) {
		try {
			Customer customer = new Customer();
			customer.setName(obj.getName());
			customer.setCpf(obj.getCpf());
			customer.setEmail(obj.getEmail());
			customer.setPhone(obj.getPhone());
			customer.setBirthDate(obj.getBirthDate());
			customer.setRegistrationDate(obj.getRegistrationDate());
			customer.setClientType(obj.getClientType());
			customer.setValidCnh(obj.getValidCnh());

			Customer saved = repository.save(customer);
			CustomerResponseDTO customerDTO = Mapper.modelMapper(saved, CustomerResponseDTO.class);
			customerDTO.add(linkTo(methodOn(CustomerController.class).findById(saved.getId())).withSelfRel());
			return customerDTO;
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
	public CustomerResponseDTO patch(String id, CustomerRequestDTO obj) {
		try {
			Customer entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Customer saved = repository.save(entity);
			return Mapper.modelMapper(saved, CustomerResponseDTO.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Customer entity, CustomerRequestDTO obj) {
		if (obj.getName() != null)
			entity.setName(obj.getName());
		if (obj.getCpf() != null)
			entity.setCpf(obj.getCpf());
		if (obj.getEmail() != null)
			entity.setEmail(obj.getEmail());
		if (obj.getPhone() != null)
			entity.setPhone(obj.getPhone());
		if (obj.getBirthDate() != null)
			entity.setBirthDate(obj.getBirthDate());
		if (obj.getRegistrationDate() != null)
			entity.setRegistrationDate(obj.getRegistrationDate());
		if (obj.getClientType() != null)
			entity.setClientType(obj.getClientType());
		if (obj.getValidCnh() != null)
			entity.setValidCnh(obj.getValidCnh());
	}
}