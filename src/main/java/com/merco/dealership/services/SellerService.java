package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.controllers.SellerController;
import com.merco.dealership.dto.SellerResponseDTO;
import com.merco.dealership.entities.Seller;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.SellerRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class SellerService {
	@Autowired
	private SellerRepository repository;

	public List<SellerResponseDTO> findAll() {
		List<SellerResponseDTO> sellersDTO = Mapper.modelMapperList(repository.findAll(), SellerResponseDTO.class);
		sellersDTO.stream()
				.forEach(i -> i.add(linkTo(methodOn(SellerController.class).findById(i.getResourceId())).withSelfRel()));
		return sellersDTO;
	}

	public SellerResponseDTO findById(String id) {
		Seller seller = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		SellerResponseDTO sellerDTO = Mapper.modelMapper(seller, SellerResponseDTO.class);
		sellerDTO.add(linkTo(methodOn(SellerController.class).findById(id)).withSelfRel());
		return sellerDTO;
	}

	@Transactional
	public Seller create(Seller obj) {
		try {
			String encryptedPassword = new BCryptPasswordEncoder().encode(obj.getPassword());
			Seller seller = new Seller(null, "CHARLIE WILLIAMS", "(51) 98765-4321", "charlie.williams@example.com",
					encryptedPassword, LocalDate.of(2019, 2, 17), 4800.0, 0.08, "Active");
			repository.save(seller);
			return seller;
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
	public Seller patch(String id, Seller obj) {
		try {
			Seller entity = repository.getReferenceById(id);
			patchData(entity, obj);
			Seller s = repository.save(entity);
			return s;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void patchData(Seller entity, Seller obj) {
		if (obj.getName() != null)
			entity.setName(obj.getName());
		if (obj.getEmail() != null)
			entity.setEmail(obj.getEmail());
		if (obj.getPhone() != null)
			entity.setPhone(obj.getPhone());
	}
}
