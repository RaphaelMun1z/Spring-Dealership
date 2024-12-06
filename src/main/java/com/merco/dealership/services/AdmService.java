package com.merco.dealership.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.controllers.AdmController;
import com.merco.dealership.dto.AdmRegisterRequestDTO;
import com.merco.dealership.dto.AdmResponseDTO;
import com.merco.dealership.entities.Adm;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.AdmRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class AdmService {
	@Autowired
	private AdmRepository repository;

	public List<AdmResponseDTO> findAll() {
		List<AdmResponseDTO> admsDTO = Mapper.modelMapperList(repository.findAll(), AdmResponseDTO.class);
		admsDTO.stream().forEach(i -> i.add(linkTo(methodOn(AdmController.class).findById(i.getId())).withSelfRel()));
		return admsDTO;
	}

	public AdmResponseDTO findById(String id) {
		Adm adm = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		AdmResponseDTO admResponse = Mapper.modelMapper(adm, AdmResponseDTO.class);
		admResponse.add(linkTo(methodOn(AdmController.class).findById(id)).withSelfRel());
		return admResponse;
	}

	@Transactional
	public AdmResponseDTO create(AdmRegisterRequestDTO obj) {
		try {
			Adm adm = Mapper.modelMapper(obj, Adm.class);
			String encryptedPassword = new BCryptPasswordEncoder().encode(obj.getPassword());
			adm.setPassword(encryptedPassword);
			repository.save(adm);
			AdmResponseDTO admResponse = Mapper.modelMapper(adm, AdmResponseDTO.class);
			admResponse.add(linkTo(methodOn(AdmController.class).findById(admResponse.getId())).withSelfRel());
			return admResponse;
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
	public Adm patch(String id, Adm obj) {
		try {
			Adm entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Adm adm = repository.save(entity);
			return adm;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Adm entity, Adm obj) {
		if (obj.getName() != null)
			entity.setName(obj.getName());
		if (obj.getEmail() != null)
			entity.setEmail(obj.getEmail());
		if (obj.getPhone() != null)
			entity.setPhone(obj.getPhone());
	}
}
