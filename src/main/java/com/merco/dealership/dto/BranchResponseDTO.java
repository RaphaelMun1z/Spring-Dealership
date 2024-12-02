package com.merco.dealership.dto;

import java.time.LocalDate;

import com.merco.dealership.entities.Branch;
import com.merco.dealership.entities.BranchAddress;

public class BranchResponseDTO {
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
	private BranchAddress address;

	public BranchResponseDTO() {
	}

	public BranchResponseDTO(Branch Branch) {
		this.id = Branch.getId();
		this.name = Branch.getName();
		this.phoneNumber = Branch.getPhoneNumber();
		this.email = Branch.getEmail();
		this.managerName = Branch.getManagerName();
		this.openingHours = Branch.getOpeningHours();
		this.branchType = Branch.getBranchType();
		this.status = Branch.getStatus();
		this.createdAt = Branch.getCreatedAt();
		this.updatedAt = Branch.getUpdatedAt();
		this.address = Branch.getAddress();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public String getManagerName() {
		return managerName;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public String getBranchType() {
		return branchType;
	}

	public String getStatus() {
		return status;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public BranchAddress getAddress() {
		return address;
	}

}
