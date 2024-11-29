package com.merco.dealership.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merco.dealership.dto.UserResponseDTO;
import com.merco.dealership.entities.User;
import com.merco.dealership.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> findAll() {
		List<User> list = service.findAllCached();
		List<UserResponseDTO> users = new ArrayList<>();

		for (User user : list) {
			users.add(new UserResponseDTO(user));
		}
		return ResponseEntity.ok().body(users);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable String id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
