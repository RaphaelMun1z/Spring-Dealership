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

import com.merco.dealership.controllers.AppointmentController;
import com.merco.dealership.dto.req.AppointmentRequestDTO;
import com.merco.dealership.dto.res.AppointmentResponseDTO;
import com.merco.dealership.entities.Appointment;
import com.merco.dealership.entities.customerResources.Customer;
import com.merco.dealership.entities.users.Seller;
import com.merco.dealership.mapper.Mapper;
import com.merco.dealership.repositories.AppointmentRepository;
import com.merco.dealership.repositories.CustomerRepository;
import com.merco.dealership.repositories.SellerRepository;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.DatabaseException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class AppointmentService {

	private final AppointmentRepository repository;
	private final CustomerRepository customerRepository;
	private final SellerRepository sellerRepository;
	private final PagedResourcesAssembler<AppointmentResponseDTO> assembler;

	public AppointmentService(AppointmentRepository repository, CustomerRepository customerRepository,
							  SellerRepository sellerRepository, PagedResourcesAssembler<AppointmentResponseDTO> assembler) {
		this.repository = repository;
		this.customerRepository = customerRepository;
		this.sellerRepository = sellerRepository;
		this.assembler = assembler;
	}

	@Transactional(readOnly = true)
	public PagedModel<EntityModel<AppointmentResponseDTO>> findAll(Pageable pageable) {
		Page<Appointment> appointmentPage = repository.findAll(pageable);
		Page<AppointmentResponseDTO> appointmentPageDTO = appointmentPage
				.map(a -> Mapper.modelMapper(a, AppointmentResponseDTO.class));
		appointmentPageDTO
				.map(i -> i.add(linkTo(methodOn(AppointmentController.class).findById(i.getId())).withSelfRel()));
		Link link = linkTo(
				methodOn(AppointmentController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc"))
				.withSelfRel();
		return assembler.toModel(appointmentPageDTO, link);
	}

	public AppointmentResponseDTO findById(String id) {
		Appointment appointment = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		AppointmentResponseDTO appointmentDTO = Mapper.modelMapper(appointment, AppointmentResponseDTO.class);
		appointmentDTO.add(linkTo(methodOn(AppointmentController.class).findById(id)).withSelfRel());
		return appointmentDTO;
	}

	@Transactional
	public AppointmentResponseDTO create(AppointmentRequestDTO obj) {
		try {
			Customer customer = customerRepository.findById(obj.getCustomerId())
					.orElseThrow(() -> new ResourceNotFoundException(obj.getCustomerId()));
			Seller seller = sellerRepository.findById(obj.getSellerId())
					.orElseThrow(() -> new ResourceNotFoundException(obj.getSellerId()));

			Appointment appointment = new Appointment();
			appointment.setDate(obj.getDate());
			appointment.setAppointmentType(obj.getAppointmentType());
			appointment.setAppointmentStatus(obj.getAppointmentStatus());
			appointment.setCustomer(customer);
			appointment.setSeller(seller);

			Appointment saved = repository.save(appointment);
			AppointmentResponseDTO appointmentDTO = Mapper.modelMapper(saved, AppointmentResponseDTO.class);
			appointmentDTO.add(linkTo(methodOn(AppointmentController.class).findById(saved.getId())).withSelfRel());
			return appointmentDTO;
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
	public AppointmentResponseDTO patch(String id, AppointmentRequestDTO obj) {
		try {
			Appointment entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Appointment saved = repository.save(entity);
			return new AppointmentResponseDTO(saved);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Appointment entity, AppointmentRequestDTO obj) {
		if (obj.getDate() != null)
			entity.setDate(obj.getDate());
		if (obj.getAppointmentType() != null)
			entity.setAppointmentType(obj.getAppointmentType());
		if (obj.getAppointmentStatus() != null)
			entity.setAppointmentStatus(obj.getAppointmentStatus());
		if (obj.getCustomerId() != null) {
			Customer customer = customerRepository.findById(obj.getCustomerId())
					.orElseThrow(() -> new ResourceNotFoundException(obj.getCustomerId()));
			entity.setCustomer(customer);
		}
		if (obj.getSellerId() != null) {
			Seller seller = sellerRepository.findById(obj.getSellerId())
					.orElseThrow(() -> new ResourceNotFoundException(obj.getSellerId()));
			entity.setSeller(seller);
		}
	}
}