package com.merco.dealership.dto;

import com.merco.dealership.entities.Seller;

public class SellerRegisterRequestDTO {
	private String name;
	private String phone;
	private String email;

	public SellerRegisterRequestDTO() {
	}

	public SellerRegisterRequestDTO(Seller seller) {
		this.name = seller.getName();
		this.phone = seller.getPhone();
		this.email = seller.getEmail();
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