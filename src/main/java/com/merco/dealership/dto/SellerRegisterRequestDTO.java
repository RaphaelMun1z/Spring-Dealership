package com.merco.dealership.dto;

import com.merco.dealership.entities.Seller;

public class SellerRegisterRequestDTO {
	private String name;
	private String phone;
	private String email;
	private String password;

	public SellerRegisterRequestDTO() {
	}

	public SellerRegisterRequestDTO(Seller seller) {
		this.name = seller.getName();
		this.phone = seller.getPhone();
		this.email = seller.getEmail();
		this.password = seller.getPassword();
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