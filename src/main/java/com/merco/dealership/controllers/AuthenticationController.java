package com.merco.dealership.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merco.dealership.dto.req.LoginRequestDTO;
import com.merco.dealership.dto.res.LoginResponseDTO;
import com.merco.dealership.services.AuthorizationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	private final AuthorizationService service;

    public AuthenticationController(AuthorizationService service) {
        this.service = service;
    }

    @PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
		return ResponseEntity.ok(service.login(data));
	}
}
