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

import com.merco.dealership.dto.SaleResponseDTO;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.services.SaleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {
	@Autowired
	private SaleService service;

	@GetMapping
	public ResponseEntity<List<SaleResponseDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleResponseDTO> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<SaleResponseDTO> insert(@RequestBody @Valid Sale obj) {
		obj = service.create(obj);
		SaleResponseDTO Sale = new SaleResponseDTO(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Sale.getResourceId()).toUri();
		return ResponseEntity.created(uri).body(Sale);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Sale> patch(@PathVariable String id, @RequestBody Sale obj) {
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