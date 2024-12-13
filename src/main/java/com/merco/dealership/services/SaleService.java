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

import com.merco.dealership.controllers.SaleController;
import com.merco.dealership.dto.SaleResponseDTO;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.SaleRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class SaleService {
	@Autowired
	private SaleRepository repository;

	@Autowired
	PagedResourcesAssembler<SaleResponseDTO> assembler;

	public PagedModel<EntityModel<SaleResponseDTO>> findAll(Pageable pageable) {
		Page<Sale> salePage = repository.findAll(pageable);
		Page<SaleResponseDTO> salePageDTO = salePage.map(p -> Mapper.modelMapper(p, SaleResponseDTO.class));
		salePageDTO.map(i -> i.add(linkTo(methodOn(SaleController.class).findById(i.getId())).withSelfRel()));
		Link link = linkTo(
				methodOn(SaleController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
				.withSelfRel();
		return assembler.toModel(salePageDTO, link);
	}

	public SaleResponseDTO findById(String id) {
		Sale sale = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		SaleResponseDTO saleDTO = Mapper.modelMapper(sale, SaleResponseDTO.class);
		saleDTO.add(linkTo(methodOn(SaleController.class).findById(id)).withSelfRel());
		return saleDTO;
	}

	@Transactional
	public SaleResponseDTO create(Sale obj) {
		try {
			Sale sale = repository.save(obj);
			SaleResponseDTO saleDTO = Mapper.modelMapper(sale, SaleResponseDTO.class);
			saleDTO.add(linkTo(methodOn(SaleController.class).findById(sale.getId())).withSelfRel());
			return saleDTO;
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
	public Sale patch(String id, Sale obj) {
		try {
			Sale entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Sale Sale = repository.save(entity);
			return Sale;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Sale entity, Sale obj) {
//		if (obj.getName() != null)
//			entity.setName(obj.getName());
//		if (obj.getEmail() != null)
//			entity.setEmail(obj.getEmail());
//		if (obj.getPhone() != null)
//			entity.setPhone(obj.getPhone());
	}
}
