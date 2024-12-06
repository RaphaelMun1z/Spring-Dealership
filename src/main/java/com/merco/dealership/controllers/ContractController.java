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

import com.merco.dealership.dto.ContractResponseDTO;
import com.merco.dealership.entities.Contract;
import com.merco.dealership.services.ContractService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/contracts")
public class ContractController {
	@Autowired
	private ContractService service;

	@GetMapping
	public ResponseEntity<List<ContractResponseDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ContractResponseDTO> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<ContractResponseDTO> insert(@RequestBody @Valid Contract obj) {
		ContractResponseDTO contractDTO = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(contractDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(contractDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Contract> patch(@PathVariable String id, @RequestBody Contract obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}