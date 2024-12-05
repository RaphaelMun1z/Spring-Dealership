package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.controllers.BranchAddressController;
import com.merco.dealership.dto.BranchAddressResponseDTO;
import com.merco.dealership.entities.BranchAddress;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.BranchAddressRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class BranchAddressService {
	@Autowired
	private BranchAddressRepository repository;

	public List<BranchAddressResponseDTO> findAll() {
		List<BranchAddressResponseDTO> branchesAdressDTO = Mapper.modelMapperList(repository.findAll(),
				BranchAddressResponseDTO.class);
		branchesAdressDTO.stream()
				.forEach(i -> i.add(linkTo(methodOn(BranchAddressController.class).findById(i.getResourceId())).withSelfRel()));
		return branchesAdressDTO;
	}

	public BranchAddressResponseDTO findById(String id) {
		BranchAddress branchAddress = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		BranchAddressResponseDTO branchAddressDTO = Mapper.modelMapper(branchAddress, BranchAddressResponseDTO.class);
		branchAddressDTO.add(linkTo(methodOn(BranchAddressController.class).findById(id)).withSelfRel());
		return branchAddressDTO;
	}

	@Transactional
	public BranchAddress create(BranchAddress obj) {
		try {
			BranchAddress BranchAddress = repository.save(obj);
			return BranchAddress;
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
	public BranchAddress patch(String id, BranchAddress obj) {
		try {
			BranchAddress entity = repository.getReferenceById(id);
			updateData(entity, obj);
			BranchAddress BranchAddress = repository.save(entity);
			return BranchAddress;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(BranchAddress entity, BranchAddress obj) {
//		if (obj.getName() != null)
//			entity.setName(obj.getName());
//		if (obj.getEmail() != null)
//			entity.setEmail(obj.getEmail());
//		if (obj.getPhone() != null)
//			entity.setPhone(obj.getPhone());
	}
}
