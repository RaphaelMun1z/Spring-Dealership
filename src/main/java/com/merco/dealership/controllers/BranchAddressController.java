package com.merco.dealership.controllers;

import java.net.URI;
import java.util.ArrayList;
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

import com.merco.dealership.dto.BranchAddressResponseDTO;
import com.merco.dealership.entities.BranchAddress;
import com.merco.dealership.services.BranchAddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/branches-address")
public class BranchAddressController {
	@Autowired
	private BranchAddressService service;

	@GetMapping
	public ResponseEntity<List<BranchAddressResponseDTO>> findAll() {
		List<BranchAddress> list = service.findAllCached();
		List<BranchAddressResponseDTO> BranchAddresss = new ArrayList<>();

		for (BranchAddress BranchAddress : list) {
			BranchAddresss.add(new BranchAddressResponseDTO(BranchAddress));
		}
		return ResponseEntity.ok().body(BranchAddresss);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<BranchAddress> findById(@PathVariable String id) {
		BranchAddress obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<BranchAddressResponseDTO> insert(@RequestBody @Valid BranchAddress obj) {
		obj = service.create(obj);
		BranchAddressResponseDTO BranchAddress = new BranchAddressResponseDTO(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(BranchAddress.getId())
				.toUri();
		return ResponseEntity.created(uri).body(BranchAddress);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<BranchAddress> patch(@PathVariable String id, @RequestBody BranchAddress obj) {
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