package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

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
import com.merco.dealership.dto.req.SaleRequestDTO;
import com.merco.dealership.dto.res.SaleResponseDTO;
import com.merco.dealership.entities.customerResources.Customer;
import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.entities.users.Seller;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.CustomerRepository;
import com.merco.dealership.repositories.InventoryItemRepository;
import com.merco.dealership.repositories.SaleRepository;
import com.merco.dealership.repositories.SellerRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class SaleService {

	private final SaleRepository repository;

	private final InventoryItemRepository inventoryRepository;

	private final CustomerRepository customerRepository;

	private final SellerRepository sellerRepository;

	private final PagedResourcesAssembler<SaleResponseDTO> assembler;

    public SaleService(SaleRepository repository, InventoryItemRepository inventoryRepository, CustomerRepository customerRepository, SellerRepository sellerRepository, PagedResourcesAssembler<SaleResponseDTO> assembler) {
        this.repository = repository;
        this.inventoryRepository = inventoryRepository;
        this.customerRepository = customerRepository;
        this.sellerRepository = sellerRepository;
        this.assembler = assembler;
    }

    @Transactional(readOnly = true)
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
	public SaleResponseDTO create(SaleRequestDTO dto) {
		try {
			InventoryItem inventoryItem = inventoryRepository.findById(dto.getInventoryId())
					.orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado no estoque"));

			if (inventoryItem.getStockExitDate() != null) {
				throw new DataViolationException("Este veículo já foi vendido.");
			}

			inventoryItem.setStockExitDate(dto.getSaleDate() != null ? dto.getSaleDate() : LocalDate.now());
			inventoryRepository.save(inventoryItem);

			Sale sale = new Sale();
			updateData(sale, dto);

			sale = repository.save(sale);

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
			Sale sale = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

			if (sale.getInventoryItem() != null) {
				InventoryItem inventoryItem = inventoryRepository.findById(sale.getInventoryItem().getId()).orElse(null);
				if (inventoryItem != null) {
					inventoryItem.setStockExitDate(null);
					inventoryRepository.save(inventoryItem);
				}
			}

			repository.delete(sale);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Transactional
	public SaleResponseDTO patch(String id, SaleRequestDTO dto) {
		try {
			Sale entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
			updateData(entity, dto);
			entity = repository.save(entity);

			SaleResponseDTO responseDTO = Mapper.modelMapper(entity, SaleResponseDTO.class);
			responseDTO.add(linkTo(methodOn(SaleController.class).findById(entity.getId())).withSelfRel());
			return responseDTO;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Sale entity, SaleRequestDTO dto) {
		if (dto.getReceipt() != null) entity.setReceipt(dto.getReceipt());
		if (dto.getPaymentMethod() != null) entity.setPaymentMethod(dto.getPaymentMethod());
		if (dto.getGrossAmount() != null) entity.setGrossAmount(dto.getGrossAmount());
		if (dto.getNetAmount() != null) entity.setNetAmount(dto.getNetAmount());
		if (dto.getAppliedDiscount() != null) entity.setAppliedDiscount(dto.getAppliedDiscount());
		if (dto.getInstallmentsNumber() != null) entity.setInstallmentsNumber(dto.getInstallmentsNumber());
		if (dto.getSaleDate() != null) entity.setSaleDate(dto.getSaleDate());

		if (dto.getCustomerId() != null && !dto.getCustomerId().isBlank()) {
			Customer customer = customerRepository.findById(dto.getCustomerId())
					.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
			entity.setCustomer(customer);
		}

		if (dto.getSellerId() != null && !dto.getSellerId().isBlank()) {
			Seller seller = sellerRepository.findById(dto.getSellerId())
					.orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));
			entity.setSeller(seller);
		}

		if (dto.getInventoryId() != null && !dto.getInventoryId().isBlank()) {
			InventoryItem item = inventoryRepository.findById(dto.getInventoryId())
					.orElseThrow(() -> new ResourceNotFoundException("Item de estoque não encontrado"));
			entity.setInventoryItem(item);
		}
	}
}