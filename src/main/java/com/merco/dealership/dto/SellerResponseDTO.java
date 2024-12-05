package com.merco.dealership.dto;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.Seller;

public class SellerResponseDTO  extends RepresentationModel<SellerResponseDTO> {
	private String id;
	private String name;
	private String phone;
	private String email;

	public SellerResponseDTO() {
	}

	public SellerResponseDTO(Seller seller) {
		this.id = seller.getId();
		this.name = seller.getName();
		this.phone = seller.getPhone();
		this.email = seller.getEmail();
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
