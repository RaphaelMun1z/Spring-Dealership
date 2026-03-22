package com.merco.dealership.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.merco.dealership.dto.res.UploadFileResponseDTO;
import com.merco.dealership.entities.vehicles.Vehicle;
import com.merco.dealership.entities.vehicles.details.VehicleImageFile;
import com.merco.dealership.repositories.VehicleImageFileRepository;
import com.merco.dealership.repositories.VehicleRepository;
import com.merco.dealership.services.exceptions.FileStorageException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

	private final Cloudinary cloudinary;
	private final VehicleRepository<Vehicle> vehicleRepository;
	private final VehicleImageFileRepository vehicleImageFileRepository;

	public FileStorageService(
			@Value("${cloudinary.cloud_name}") String cloudName,
			@Value("${cloudinary.api_key}") String apiKey,
			@Value("${cloudinary.api_secret}") String apiSecret,
			VehicleRepository<Vehicle> vehicleRepository,
			VehicleImageFileRepository vehicleImageFileRepository
	) {
		this.cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", cloudName,
				"api_key", apiKey,
				"api_secret", apiSecret
		));
		this.vehicleRepository = vehicleRepository;
		this.vehicleImageFileRepository = vehicleImageFileRepository;
	}

	@Transactional
	public UploadFileResponseDTO uploadVehicleImage(MultipartFile file, String vehicleId) {
		Vehicle vehicle = vehicleRepository.findById(vehicleId)
				.orElseThrow(() -> new ResourceNotFoundException(vehicleId));

		try {
			Map uploadResult = cloudinary.uploader().upload(
					file.getBytes(),
					ObjectUtils.asMap(
							"folder", "dealership/vehicles/" + vehicleId,
							"resource_type", "image"
					)
			);

			String downloadUri = uploadResult.get("secure_url").toString();
			String publicId = uploadResult.get("public_id").toString();

			VehicleImageFile imageFile = new VehicleImageFile();
			imageFile.setName(publicId);
			imageFile.setDownloadUri(downloadUri);
			imageFile.setType(file.getContentType());
			imageFile.setSize(file.getSize());
			imageFile.setVehicle(vehicle);
			vehicleImageFileRepository.save(imageFile);

			return new UploadFileResponseDTO(publicId, downloadUri, file.getContentType(), file.getSize());

		} catch (Exception e) {
			throw new FileStorageException("Erro ao enviar imagem para o Cloudinary.", e);
		}
	}

	@Transactional
	public List<UploadFileResponseDTO> uploadMultipleVehicleImages(MultipartFile[] files, String vehicleId) {
		return Arrays.stream(files)
				.map(file -> uploadVehicleImage(file, vehicleId))
				.collect(Collectors.toList());
	}
}