package com.merco.dealership.dto;

import com.merco.dealership.entities.Seller;

public class SellerResponseDTO {
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

	public String getId() {
		return id;
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
}
