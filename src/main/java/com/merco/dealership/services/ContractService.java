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

import com.merco.dealership.controllers.ContractController;
import com.merco.dealership.dto.req.ContractRequestDTO;
import com.merco.dealership.dto.res.ContractResponseDTO;
import com.merco.dealership.entities.Contract;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.ContractRepository;
import com.merco.dealership.repositories.SaleRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class ContractService {

	private final ContractRepository repository;
	private final SaleRepository saleRepository;
	private final PagedResourcesAssembler<ContractResponseDTO> assembler;

	public ContractService(ContractRepository repository, SaleRepository saleRepository,
						   PagedResourcesAssembler<ContractResponseDTO> assembler) {
		this.repository = repository;
		this.saleRepository = saleRepository;
		this.assembler = assembler;
	}

	@Transactional(readOnly = true)
	public PagedModel<EntityModel<ContractResponseDTO>> findAll(Pageable pageable) {
		Page<Contract> contractPage = repository.findAll(pageable);
		Page<ContractResponseDTO> contractPageDTO = contractPage
				.map(p -> Mapper.modelMapper(p, ContractResponseDTO.class));
		contractPageDTO.map(i -> i.add(linkTo(methodOn(ContractController.class).findById(i.getId())).withSelfRel()));
		Link link = linkTo(
				methodOn(ContractController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
				.withSelfRel();
		return assembler.toModel(contractPageDTO, link);
	}

	public ContractResponseDTO findById(String id) {
		Contract contract = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		ContractResponseDTO contractDTO = Mapper.modelMapper(contract, ContractResponseDTO.class);
		contractDTO.add(linkTo(methodOn(ContractController.class).findById(id)).withSelfRel());
		return contractDTO;
	}

	@Transactional
	public ContractResponseDTO create(ContractRequestDTO obj) {
		try {
			Sale sale = saleRepository.findById(obj.getSaleId())
					.orElseThrow(() -> new ResourceNotFoundException(obj.getSaleId()));

			Contract contract = new Contract();
			contract.setContractNumber(obj.getContractNumber());
			contract.setContractType(obj.getContractType());
			contract.setContractDate(obj.getContractDate());
			contract.setDeliveryDate(obj.getDeliveryDate());
			contract.setTotalAmount(obj.getTotalAmount());
			contract.setPaymentTerms(obj.getPaymentTerms());
			contract.setContractStatus(obj.getContractStatus());
			contract.setNotes(obj.getNotes());
			contract.setAttachments(obj.getAttachments());
			contract.setSale(sale);

			Contract saved = repository.save(contract);
			ContractResponseDTO contractDTO = Mapper.modelMapper(saved, ContractResponseDTO.class);
			contractDTO.add(linkTo(methodOn(ContractController.class).findById(saved.getId())).withSelfRel());
			return contractDTO;
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
	public ContractResponseDTO patch(String id, ContractRequestDTO obj) {
		try {
			Contract entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Contract saved = repository.save(entity);
			return Mapper.modelMapper(saved, ContractResponseDTO.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Contract entity, ContractRequestDTO obj) {
		if (obj.getContractNumber() != null)
			entity.setContractNumber(obj.getContractNumber());
		if (obj.getContractType() != null)
			entity.setContractType(obj.getContractType());
		if (obj.getContractDate() != null)
			entity.setContractDate(obj.getContractDate());
		if (obj.getDeliveryDate() != null)
			entity.setDeliveryDate(obj.getDeliveryDate());
		if (obj.getTotalAmount() != null)
			entity.setTotalAmount(obj.getTotalAmount());
		if (obj.getPaymentTerms() != null)
			entity.setPaymentTerms(obj.getPaymentTerms());
		if (obj.getContractStatus() != null)
			entity.setContractStatus(obj.getContractStatus());
		if (obj.getNotes() != null)
			entity.setNotes(obj.getNotes());
		if (obj.getAttachments() != null)
			entity.setAttachments(obj.getAttachments());
		if (obj.getSaleId() != null) {
			Sale sale = saleRepository.findById(obj.getSaleId())
					.orElseThrow(() -> new ResourceNotFoundException(obj.getSaleId()));
			entity.setSale(sale);
		}
	}
}