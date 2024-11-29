package com.merco.dealership.entities.enums;

public enum UserRole {
	CUSTOMER("customer"), SELLER("seller"), ADM("adm");

	private String role;

	UserRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
