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
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<BranchAddressResponseDTO> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<BranchAddressResponseDTO> insert(@RequestBody @Valid BranchAddress obj) {
		BranchAddressResponseDTO branchAddressDTO = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(branchAddressDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(branchAddressDTO);
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
}