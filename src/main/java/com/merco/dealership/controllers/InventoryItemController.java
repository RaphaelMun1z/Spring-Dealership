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

import com.merco.dealership.dto.InventoryItemResponseDTO;
import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.services.InventoryItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/inventory-items")
public class InventoryItemController {
	@Autowired
	private InventoryItemService service;

	@GetMapping
	public ResponseEntity<List<InventoryItemResponseDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<InventoryItemResponseDTO> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<InventoryItemResponseDTO> insert(@RequestBody @Valid InventoryItem obj) {
		InventoryItemResponseDTO inventoryItemDTO = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(inventoryItemDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(inventoryItemDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<InventoryItem> patch(@PathVariable String id, @RequestBody InventoryItem obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}