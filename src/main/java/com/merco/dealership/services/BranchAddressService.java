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

import com.merco.dealership.controllers.BranchAddressController;
import com.merco.dealership.dto.req.BranchAddressRequestDTO;
import com.merco.dealership.dto.res.BranchAddressResponseDTO;
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

	private final BranchAddressRepository repository;
	private final PagedResourcesAssembler<BranchAddressResponseDTO> assembler;

	public BranchAddressService(BranchAddressRepository repository,
								PagedResourcesAssembler<BranchAddressResponseDTO> assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	public PagedModel<EntityModel<BranchAddressResponseDTO>> findAll(Pageable pageable) {
		Page<BranchAddress> branchAddressPage = repository.findAll(pageable);
		Page<BranchAddressResponseDTO> branchAddressPageDTO = branchAddressPage
				.map(p -> Mapper.modelMapper(p, BranchAddressResponseDTO.class));
		branchAddressPageDTO
				.map(i -> i.add(linkTo(methodOn(BranchAddressController.class).findById(i.getId())).withSelfRel()));
		Link link = linkTo(methodOn(BranchAddressController.class).findAll(pageable.getPageNumber(),
				pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(branchAddressPageDTO, link);
	}

	public BranchAddressResponseDTO findById(String id) {
		BranchAddress branchAddress = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		BranchAddressResponseDTO branchAddressDTO = Mapper.modelMapper(branchAddress, BranchAddressResponseDTO.class);
		branchAddressDTO.add(linkTo(methodOn(BranchAddressController.class).findById(id)).withSelfRel());
		return branchAddressDTO;
	}

	@Transactional
	public BranchAddressResponseDTO create(BranchAddressRequestDTO obj) {
		try {
			BranchAddress branchAddress = new BranchAddress();
			branchAddress.setStreet(obj.getStreet());
			branchAddress.setNumber(obj.getNumber());
			branchAddress.setDistrict(obj.getDistrict());
			branchAddress.setCity(obj.getCity());
			branchAddress.setState(obj.getState());
			branchAddress.setCountry(obj.getCountry());
			branchAddress.setCep(obj.getCep());
			branchAddress.setComplement(obj.getComplement());

			BranchAddress saved = repository.save(branchAddress);
			BranchAddressResponseDTO branchAddressDTO = Mapper.modelMapper(saved, BranchAddressResponseDTO.class);
			branchAddressDTO
					.add(linkTo(methodOn(BranchAddressController.class).findById(saved.getId())).withSelfRel());
			return branchAddressDTO;
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
	public BranchAddressResponseDTO patch(String id, BranchAddressRequestDTO obj) {
		try {
			BranchAddress entity = repository.getReferenceById(id);
			updateData(entity, obj);
			BranchAddress saved = repository.save(entity);
			return Mapper.modelMapper(saved, BranchAddressResponseDTO.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(BranchAddress entity, BranchAddressRequestDTO obj) {
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