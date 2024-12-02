package com.merco.dealership.dto;

import com.merco.dealership.entities.Adm;

public class AdmRegisterRequestDTO {
	private String name;
	private String phone;
	private String email;
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