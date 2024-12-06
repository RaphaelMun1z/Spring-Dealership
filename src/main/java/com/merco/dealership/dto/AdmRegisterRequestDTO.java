package com.merco.dealership.dto;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.Adm;
import com.merco.dealership.entities.validation.constraints.PhoneNumber;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AdmRegisterRequestDTO extends RepresentationModel<AdmResponseDTO> {
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

	public AdmRegisterRequestDTO() {
	}

	public AdmRegisterRequestDTO(Adm adm) {
		this.name = adm.getName();
		this.phone = adm.getPhone();
		this.email = adm.getEmail();
		this.password = adm.getPassword();
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}