package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.controllers.BranchController;
import com.merco.dealership.dto.req.BranchRequestDTO;
import com.merco.dealership.dto.res.BranchResponseDTO;
import com.merco.dealership.entities.Branch;
import com.merco.dealership.entities.BranchAddress;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.BranchAddressRepository;
import com.merco.dealership.repositories.BranchRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class BranchService {

	private final BranchRepository repository;
	private final BranchAddressRepository branchAddressRepository;

	public BranchService(BranchRepository repository, BranchAddressRepository branchAddressRepository) {
		this.repository = repository;
		this.branchAddressRepository = branchAddressRepository;
	}

	@Transactional(readOnly = true)
	public Page<BranchResponseDTO> findAll(Pageable pageable) {
		Page<Branch> branchPage = repository.findAll(pageable);
		Page<BranchResponseDTO> branchPageDTO = branchPage.map(p -> Mapper.modelMapper(p, BranchResponseDTO.class));
		branchPageDTO.map(i -> i.add(linkTo(methodOn(BranchController.class).findById(i.getId())).withSelfRel()));
		return branchPageDTO;
	}

	public BranchResponseDTO findById(String id) {
		Branch branch = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		BranchResponseDTO branchDTO = Mapper.modelMapper(branch, BranchResponseDTO.class);
		branchDTO.add(linkTo(methodOn(BranchController.class).findById(id)).withSelfRel());
		return branchDTO;
	}

	@Transactional
	public BranchResponseDTO create(BranchRequestDTO obj) {
		try {
			BranchAddress address = branchAddressRepository.findById(obj.getBranchAddressId())
					.orElseThrow(() -> new ResourceNotFoundException(obj.getBranchAddressId()));

			Branch branch = new Branch();
			branch.setName(obj.getName());
			branch.setPhoneNumber(obj.getPhoneNumber());
			branch.setEmail(obj.getEmail());
			branch.setManagerName(obj.getManagerName());
			branch.setOpeningHours(obj.getOpeningHours());
			branch.setBranchType(obj.getBranchType());
			branch.setStatus(obj.getStatus());
			branch.setCreatedAt(obj.getCreatedAt());
			branch.setUpdatedAt(obj.getUpdatedAt());
			branch.setAddress(address);

			Branch saved = repository.save(branch);
			BranchResponseDTO branchDTO = Mapper.modelMapper(saved, BranchResponseDTO.class);
			branchDTO.add(linkTo(methodOn(BranchController.class).findById(saved.getId())).withSelfRel());
			return branchDTO;
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
	public BranchResponseDTO patch(String id, BranchRequestDTO obj) {
		try {
			Branch entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Branch saved = repository.save(entity);
			return Mapper.modelMapper(saved, BranchResponseDTO.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Branch entity, BranchRequestDTO obj) {
		if (obj.getName() != null)
			entity.setName(obj.getName());
		if (obj.getPhoneNumber() != null)
			entity.setPhoneNumber(obj.getPhoneNumber());
		if (obj.getEmail() != null)
			entity.setEmail(obj.getEmail());
		if (obj.getManagerName() != null)
			entity.setManagerName(obj.getManagerName());
		if (obj.getOpeningHours() != null)
			entity.setOpeningHours(obj.getOpeningHours());
		if (obj.getBranchType() != null)
			entity.setBranchType(obj.getBranchType());
		if (obj.getStatus() != null)
			entity.setStatus(obj.getStatus());
		if (obj.getUpdatedAt() != null)
			entity.setUpdatedAt(obj.getUpdatedAt());
		if (obj.getBranchAddressId() != null) {
			BranchAddress address = branchAddressRepository.findById(obj.getBranchAddressId())
					.orElseThrow(() -> new ResourceNotFoundException(obj.getBranchAddressId()));
			entity.setAddress(address);
		}
	}
}