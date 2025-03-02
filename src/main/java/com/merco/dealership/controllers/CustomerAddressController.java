package com.merco.dealership.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<PagedModel<EntityModel<CustomerAddressResponseDTO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "id"));
		return ResponseEntity.ok().body(service.findAll(pageable));
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