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

import com.merco.dealership.controllers.InventoryItemController;
import com.merco.dealership.dto.InventoryItemResponseDTO;
import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.InventoryItemRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class InventoryItemService {
	@Autowired
	private InventoryItemRepository repository;

	@Autowired
	PagedResourcesAssembler<InventoryItemResponseDTO> assembler;

	public PagedModel<EntityModel<InventoryItemResponseDTO>> findAll(Pageable pageable) {
		Page<InventoryItem> inventoryItemPage = repository.findAll(pageable);
		Page<InventoryItemResponseDTO> inventoryItemPageDTO = inventoryItemPage
				.map(p -> Mapper.modelMapper(p, InventoryItemResponseDTO.class));
		inventoryItemPageDTO
				.map(i -> i.add(linkTo(methodOn(InventoryItemController.class).findById(i.getId())).withSelfRel()));
		Link link = linkTo(methodOn(InventoryItemController.class).findAll(pageable.getPageNumber(),
				pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(inventoryItemPageDTO, link);
	}

	public InventoryItemResponseDTO findById(String id) {
		InventoryItem inventoryItem = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		InventoryItemResponseDTO inventoryItemDTO = Mapper.modelMapper(inventoryItem, InventoryItemResponseDTO.class);
		inventoryItemDTO.add(linkTo(methodOn(InventoryItemController.class).findById(id)).withSelfRel());
		return inventoryItemDTO;
	}

	@Transactional
	public InventoryItemResponseDTO create(InventoryItem obj) {
		try {
			InventoryItem inventoryItem = repository.save(obj);
			InventoryItemResponseDTO inventoryItemDTO = Mapper.modelMapper(inventoryItem,
					InventoryItemResponseDTO.class);
			inventoryItemDTO
					.add(linkTo(methodOn(InventoryItemController.class).findById(inventoryItem.getId())).withSelfRel());
			return inventoryItemDTO;
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
	public InventoryItem patch(String id, InventoryItem obj) {
		try {
			InventoryItem entity = repository.getReferenceById(id);
			updateData(entity, obj);
			InventoryItem InventoryItem = repository.save(entity);
			return InventoryItem;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(InventoryItem entity, InventoryItem obj) {
//		if (obj.getName() != null)
//			entity.setName(obj.getName());
//		if (obj.getEmail() != null)
//			entity.setEmail(obj.getEmail());
//		if (obj.getPhone() != null)
//			entity.setPhone(obj.getPhone());
	}
}
