package com.merco.dealership.dto;

import com.merco.dealership.entities.Seller;

public class SellerResponseDTO {
	private String id;
	private String name;
	private String phone;
	private String email;

	public SellerResponseDTO() {
	}

	public SellerResponseDTO(Seller Seller) {
		super();
		this.id = Seller.getId();
		this.name = Seller.getName();
		this.phone = Seller.getPhone();
		this.email = Seller.getEmail();
	}

	public String getId() {
		return id;
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
