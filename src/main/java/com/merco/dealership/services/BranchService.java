package com.merco.dealership.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.dto.BranchResponseDTO;
import com.merco.dealership.entities.Branch;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.BranchRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class BranchService {
	@Autowired
	private BranchRepository repository;

	public List<BranchResponseDTO> findAll() {
		return Mapper.modelMapperList(repository.findAll(), BranchResponseDTO.class);
	}

	public BranchResponseDTO findById(String id) {
		Branch branch = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return Mapper.modelMapper(branch, BranchResponseDTO.class);
	}

	@Transactional
	public Branch create(Branch obj) {
		try {
			Branch Branch = repository.save(obj);
			return Branch;
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
	public Branch patch(String id, Branch obj) {
		try {
			Branch entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Branch Branch = repository.save(entity);
			return Branch;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Branch entity, Branch obj) {
//		if (obj.getName() != null)
//			entity.setName(obj.getName());
//		if (obj.getEmail() != null)
//			entity.setEmail(obj.getEmail());
//		if (obj.getPhone() != null)
//			entity.setPhone(obj.getPhone());
	}
}
