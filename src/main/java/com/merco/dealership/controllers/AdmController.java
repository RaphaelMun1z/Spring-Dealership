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

import com.merco.dealership.dto.AdmRegisterRequestDTO;
import com.merco.dealership.dto.AdmResponseDTO;
import com.merco.dealership.entities.Adm;
import com.merco.dealership.services.AdmService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/adm")
public class AdmController {
	@Autowired
	private AdmService service;

	@GetMapping
	public ResponseEntity<List<AdmResponseDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AdmResponseDTO> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<AdmResponseDTO> insert(@RequestBody @Valid AdmRegisterRequestDTO obj) {
		AdmResponseDTO admDTO = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(admDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(admDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Adm> patch(@PathVariable String id, @RequestBody Adm obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}