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

import com.merco.dealership.controllers.InventoryItemController;
import com.merco.dealership.dto.req.InventoryItemRequestDTO;
import com.merco.dealership.dto.res.InventoryItemResponseDTO;
import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.entities.vehicles.Vehicle;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.InventoryItemRepository;
import com.merco.dealership.repositories.VehicleRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class InventoryItemService {

	private final InventoryItemRepository repository;
	private final VehicleRepository<?> vehicleRepository;
	private final PagedResourcesAssembler<InventoryItemResponseDTO> assembler;

	public InventoryItemService(InventoryItemRepository repository, VehicleRepository vehicleRepository,
								PagedResourcesAssembler<InventoryItemResponseDTO> assembler) {
		this.repository = repository;
		this.vehicleRepository = vehicleRepository;
		this.assembler = assembler;
	}

	@Transactional(readOnly = true)
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
	public InventoryItemResponseDTO create(InventoryItemRequestDTO obj) {
		try {
			Vehicle vehicle = vehicleRepository.findById(obj.getVehicleId())
					.orElseThrow(() -> new ResourceNotFoundException(obj.getVehicleId()));

			InventoryItem inventoryItem = new InventoryItem();
			inventoryItem.setVehicle(vehicle);
			inventoryItem.setStockEntryDate(obj.getStockEntryDate());
			inventoryItem.setStockExitDate(obj.getStockExitDate());
			inventoryItem.setAcquisitionPrice(obj.getAcquisitionPrice());
			inventoryItem.setProfitMargin(obj.getProfitMargin());
			inventoryItem.setSupplier(obj.getSupplier());
			inventoryItem.setLicensePlate(obj.getLicensePlate());
			inventoryItem.setChassis(obj.getChassis());

			InventoryItem saved = repository.save(inventoryItem);
			InventoryItemResponseDTO inventoryItemDTO = Mapper.modelMapper(saved, InventoryItemResponseDTO.class);
			inventoryItemDTO.add(linkTo(methodOn(InventoryItemController.class).findById(saved.getId())).withSelfRel());
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
	public InventoryItemResponseDTO patch(String id, InventoryItemRequestDTO obj) {
		try {
			InventoryItem entity = repository.getReferenceById(id);
			updateData(entity, obj);
			InventoryItem saved = repository.save(entity);
			return Mapper.modelMapper(saved, InventoryItemResponseDTO.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(InventoryItem entity, InventoryItemRequestDTO obj) {
		if (obj.getStockEntryDate() != null)
			entity.setStockEntryDate(obj.getStockEntryDate());
		if (obj.getStockExitDate() != null)
			entity.setStockExitDate(obj.getStockExitDate());
		if (obj.getAcquisitionPrice() != null)
			entity.setAcquisitionPrice(obj.getAcquisitionPrice());
		if (obj.getProfitMargin() != null)
			entity.setProfitMargin(obj.getProfitMargin());
		if (obj.getSupplier() != null)
			entity.setSupplier(obj.getSupplier());
		if (obj.getLicensePlate() != null)
			entity.setLicensePlate(obj.getLicensePlate());
		if (obj.getChassis() != null)
			entity.setChassis(obj.getChassis());
		if (obj.getVehicleId() != null) {
			Vehicle vehicle = vehicleRepository.findById(obj.getVehicleId())
					.orElseThrow(() -> new ResourceNotFoundException(obj.getVehicleId()));
			entity.setVehicle(vehicle);
		}
	}
}