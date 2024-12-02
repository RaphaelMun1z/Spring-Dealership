package com.merco.dealership.entities;

import java.time.LocalDate;
import java.util.Objects;

import com.merco.dealership.entities.validation.constraints.PhoneNumber;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_branches")
public class Branch {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	private String name;

	@PhoneNumber(message = "Invalid field value")
	private String phoneNumber;

	@NotNull(message = "Required field")
	@Email(message = "Invalid field value")
	private String email;

	@NotNull(message = "Required field")
	private String managerName;

	@NotNull(message = "Required field")
	private String openingHours;

	@NotNull(message = "Required field")
	private String branchType;

	private String status;

	private LocalDate createdAt;

	private LocalDate updatedAt;

	@NotNull(message = "Required field")
	@OneToOne
	@JoinColumn(name = "branch_address_id")
	private BranchAddress address;

	public Branch() {

	}

	public Branch(String id, @NotNull(message = "Required field") String name,
			@NotNull(message = "Required field") BranchAddress address, String phoneNumber,
			@NotNull(message = "Required field") @Email(message = "Invalid field value") String email,
			@NotNull(message = "Required field") String managerName,
			@NotNull(message = "Required field") String openingHours,
			@NotNull(message = "Required field") String branchType, String status, LocalDate createdAt,
			LocalDate updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.managerName = managerName;
		this.openingHours = openingHours;
		this.branchType = branchType;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public String getId() {
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

	public BranchAddress getAddress() {
		return address;
	}

	public void setAddress(BranchAddress address) {
		this.address = address;
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Branch other = (Branch) obj;
		return Objects.equals(id, other.id);
	}

}
