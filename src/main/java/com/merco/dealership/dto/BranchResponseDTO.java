package com.merco.dealership.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.Branch;
import com.merco.dealership.mapper.Mapper;

public class BranchResponseDTO extends RepresentationModel<BranchResponseDTO> {
	private String id;
	private String name;
	private String phoneNumber;
	private String email;
	private String managerName;
	private String openingHours;
	private String branchType;
	private String status;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	private BranchAddressResponseDTO address;

	public BranchResponseDTO() {
	}

	public BranchResponseDTO(Branch branch) {
		this.id = branch.getId();
		this.name = branch.getName();
		this.phoneNumber = branch.getPhoneNumber();
		this.email = branch.getEmail();
		this.managerName = branch.getManagerName();
		this.openingHours = branch.getOpeningHours();
		this.branchType = branch.getBranchType();
		this.status = branch.getStatus();
		this.createdAt = branch.getCreatedAt();
		this.updatedAt = branch.getUpdatedAt();
		this.address = Mapper.modelMapper(branch.getAddress(), BranchAddressResponseDTO.class);
	}

	public String getResourceId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public BranchAddressResponseDTO getAddress() {
		return address;
	}

	public void setAddress(BranchAddressResponseDTO address) {
		this.address = address;
	}

}
