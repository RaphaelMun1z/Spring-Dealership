package com.merco.dealership.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.merco.dealership.dto.AppointmentResponseDTO;
import com.merco.dealership.entities.Appointment;
import com.merco.dealership.services.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/appointments")
public class AppointmentController {
	@Autowired
	private AppointmentService service;

	@GetMapping
	public ResponseEntity<List<AppointmentResponseDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<AppointmentResponseDTO> insert(@RequestBody @Valid Appointment obj) {
		obj = service.create(obj);
		AppointmentResponseDTO Appointment = new AppointmentResponseDTO(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Appointment.getResourceId())
				.toUri();
		return ResponseEntity.created(uri).body(Appointment);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Appointment> patch(@PathVariable String id, @RequestBody Appointment obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> validationExceptionHandler(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((err) -> {
			String fieldName = ((FieldError) err).getField();
			String errorMessage = err.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}