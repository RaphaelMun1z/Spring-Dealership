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

import com.merco.dealership.controllers.VehicleSpecificDetailController;
import com.merco.dealership.dto.VehicleSpecificDetailResponseDTO;
import com.merco.dealership.entities.VehicleSpecificDetail;
import com.merco.dealership.mapper.Mapper;
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

	@Autowired
	PagedResourcesAssembler<VehicleSpecificDetailResponseDTO> assembler;

	public PagedModel<EntityModel<VehicleSpecificDetailResponseDTO>> findAll(Pageable pageable) {
		Page<VehicleSpecificDetail> vehicleSpecificDetailPage = repository.findAll(pageable);
		Page<VehicleSpecificDetailResponseDTO> vehicleSpecificDetailPageDTO = vehicleSpecificDetailPage
				.map(p -> Mapper.modelMapper(p, VehicleSpecificDetailResponseDTO.class));
		vehicleSpecificDetailPageDTO.map(
				i -> i.add(linkTo(methodOn(VehicleSpecificDetailController.class).findById(i.getId())).withSelfRel()));
		Link link = linkTo(methodOn(VehicleSpecificDetailController.class).findAll(pageable.getPageNumber(),
				pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(vehicleSpecificDetailPageDTO, link);
	}

	public VehicleSpecificDetailResponseDTO findById(String id) {
		VehicleSpecificDetail vehicleSpecificDetail = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		VehicleSpecificDetailResponseDTO vehicleSpecificDetailDTO = Mapper.modelMapper(vehicleSpecificDetail,
				VehicleSpecificDetailResponseDTO.class);
		vehicleSpecificDetailDTO
				.add(linkTo(methodOn(VehicleSpecificDetailController.class).findById(id)).withSelfRel());
		return vehicleSpecificDetailDTO;
	}

	@Transactional
	public VehicleSpecificDetailResponseDTO create(VehicleSpecificDetail obj) {
		try {
			VehicleSpecificDetail vehicleSpecificDetail = repository.save(obj);
			VehicleSpecificDetailResponseDTO vehicleSpecificDetailDTO = Mapper.modelMapper(vehicleSpecificDetail,
					VehicleSpecificDetailResponseDTO.class);
			vehicleSpecificDetailDTO
					.add(linkTo(methodOn(VehicleSpecificDetailController.class).findById(vehicleSpecificDetail.getId()))
							.withSelfRel());
			return vehicleSpecificDetailDTO;
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
