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

import com.merco.dealership.dto.BranchResponseDTO;
import com.merco.dealership.entities.Branch;
import com.merco.dealership.services.BranchService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/branches")
public class BranchController {
	@Autowired
	private BranchService service;

	@GetMapping
	public ResponseEntity<List<BranchResponseDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<BranchResponseDTO> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<BranchResponseDTO> insert(@RequestBody @Valid Branch obj) {
		BranchResponseDTO branchDTO = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(branchDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(branchDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Branch> patch(@PathVariable String id, @RequestBody Branch obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}