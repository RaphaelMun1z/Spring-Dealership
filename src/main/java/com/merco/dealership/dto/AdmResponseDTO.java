package com.merco.dealership.dto;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.Adm;

public class AdmResponseDTO extends RepresentationModel<AdmResponseDTO> {
	private String id;
	private String name;
	private String phone;
	private String email;

	public AdmResponseDTO() {
	}

	public AdmResponseDTO(Adm adm) {
		this.id = adm.getId();
		this.name = adm.getName();
		this.phone = adm.getPhone();
		this.email = adm.getEmail();
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

}
