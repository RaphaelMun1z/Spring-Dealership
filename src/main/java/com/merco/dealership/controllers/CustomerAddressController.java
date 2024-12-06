package com.merco.dealership.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.merco.dealership.dto.CustomerAddressResponseDTO;
import com.merco.dealership.entities.CustomerAddress;
import com.merco.dealership.services.CustomerAddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/customers-address")
public class CustomerAddressController {
	@Autowired
	private CustomerAddressService service;

	@GetMapping
	public ResponseEntity<List<CustomerAddressResponseDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CustomerAddressResponseDTO> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<CustomerAddressResponseDTO> insert(@RequestBody @Valid CustomerAddress obj) {
		CustomerAddressResponseDTO customerAddressDTO = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(customerAddressDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(customerAddressDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<CustomerAddress> patch(@PathVariable String id, @RequestBody CustomerAddress obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}