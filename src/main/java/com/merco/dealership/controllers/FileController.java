package com.merco.dealership.controllers;

import com.merco.dealership.dto.res.UploadFileResponseDTO;
import com.merco.dealership.services.FileStorageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

	private final FileStorageService service;

	public FileController(FileStorageService service) {
		this.service = service;
	}

	@PostMapping("/upload-vehicle-image/{vehicleId}")
	public ResponseEntity<UploadFileResponseDTO> uploadVehicleImage(
			@RequestParam("file") MultipartFile file,
			@PathVariable String vehicleId) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.uploadVehicleImage(file, vehicleId));
	}

	@PostMapping("/upload-multiple-vehicle-images/{vehicleId}")
	public ResponseEntity<List<UploadFileResponseDTO>> uploadMultipleVehicleImages(
			@RequestParam("files") MultipartFile[] files,
			@PathVariable String vehicleId) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.uploadMultipleVehicleImages(files, vehicleId));
	}
}