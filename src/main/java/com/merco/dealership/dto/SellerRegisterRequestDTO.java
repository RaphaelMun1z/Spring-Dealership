package com.merco.dealership.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.Seller;
import com.merco.dealership.entities.validation.constraints.PhoneNumber;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class SellerRegisterRequestDTO extends RepresentationModel<SellerResponseDTO> {
	@NotNull(message = "Required field")
	@Pattern(regexp = "^[A-Z]+(.)*")
	private String name;

	@PhoneNumber(message = "Invalid field value")
	@Column(unique = true)
	private String phone;

	@NotNull(message = "Required field")
	@Email(message = "Invalid field value")
	@Column(unique = true)
	private String email;

	@NotNull(message = "Required field")
	private String password;

	@NotNull(message = "Required field")
	private LocalDate hireDate;

	@NotNull(message = "Required field")
	private Double salary;

	@NotNull(message = "Required field")
	private Double commissionRate;

	private String status;

	public SellerRegisterRequestDTO() {
	}

	public SellerRegisterRequestDTO(Seller seller) {
		this.name = seller.getName();
		this.phone = seller.getPhone();
		this.email = seller.getEmail();
		this.password = seller.getPassword();
		this.hireDate = seller.getHireDate();
		this.salary = seller.getSalary();
		this.commissionRate = seller.getCommissionRate();
		this.status = seller.getStatus();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}