package com.merco.dealership.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public List<SaleResponseDTO> findAll() {
		return Mapper.modelMapperList(repository.findAll(), SaleResponseDTO.class);
	}

	public SaleResponseDTO findById(String id) {
		Sale sale = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return Mapper.modelMapper(sale, SaleResponseDTO.class);
	}

	@Transactional
	public Sale create(Sale obj) {
		try {
			Sale Sale = repository.save(obj);
			return Sale;
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
