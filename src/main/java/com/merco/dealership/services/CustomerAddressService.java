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

import com.merco.dealership.controllers.CustomerAddressController;
import com.merco.dealership.dto.req.CustomerAddressRequestDTO;
import com.merco.dealership.dto.res.CustomerAddressResponseDTO;
import com.merco.dealership.entities.customerResources.CustomerAddress;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.CustomerAddressRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class CustomerAddressService {

	private final CustomerAddressRepository repository;
	private final PagedResourcesAssembler<CustomerAddressResponseDTO> assembler;

	public CustomerAddressService(CustomerAddressRepository repository,
								  PagedResourcesAssembler<CustomerAddressResponseDTO> assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

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
	public CustomerAddressResponseDTO create(CustomerAddressRequestDTO obj) {
		try {
			CustomerAddress customerAddress = new CustomerAddress();
			customerAddress.setStreet(obj.getStreet());
			customerAddress.setNumber(obj.getNumber());
			customerAddress.setDistrict(obj.getDistrict());
			customerAddress.setCity(obj.getCity());
			customerAddress.setState(obj.getState());
			customerAddress.setCountry(obj.getCountry());
			customerAddress.setCep(obj.getCep());
			customerAddress.setComplement(obj.getComplement());

			CustomerAddress saved = repository.save(customerAddress);
			CustomerAddressResponseDTO customerAddressDTO = Mapper.modelMapper(saved, CustomerAddressResponseDTO.class);
			customerAddressDTO
					.add(linkTo(methodOn(CustomerAddressController.class).findById(saved.getId())).withSelfRel());
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
	public CustomerAddressResponseDTO patch(String id, CustomerAddressRequestDTO obj) {
		try {
			CustomerAddress entity = repository.getReferenceById(id);
			updateData(entity, obj);
			CustomerAddress saved = repository.save(entity);
			return Mapper.modelMapper(saved, CustomerAddressResponseDTO.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(CustomerAddress entity, CustomerAddressRequestDTO obj) {
		if (obj.getStreet() != null)
			entity.setStreet(obj.getStreet());
		if (obj.getNumber() != null)
			entity.setNumber(obj.getNumber());
		if (obj.getDistrict() != null)
			entity.setDistrict(obj.getDistrict());
		if (obj.getCity() != null)
			entity.setCity(obj.getCity());
		if (obj.getState() != null)
			entity.setState(obj.getState());
		if (obj.getCountry() != null)
			entity.setCountry(obj.getCountry());
		if (obj.getCep() != null)
			entity.setCep(obj.getCep());
		if (obj.getComplement() != null)
			entity.setComplement(obj.getComplement());
	}
}