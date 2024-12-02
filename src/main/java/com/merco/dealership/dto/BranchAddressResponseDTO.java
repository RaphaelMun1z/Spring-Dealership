package com.merco.dealership.dto;

import com.merco.dealership.entities.BranchAddress;

public class BranchAddressResponseDTO {
	private String id;
	private String street;
	private int number;
	private String district;
	private String city;
	private String state;
	private String country;
	private String cep;
	private String complement;

	public BranchAddressResponseDTO() {
	}

	public BranchAddressResponseDTO(BranchAddress branchAddress) {
		this.id = branchAddress.getId();
		this.street = branchAddress.getStreet();
		this.number = branchAddress.getNumber();
		this.district = branchAddress.getDistrict();
		this.city = branchAddress.getCity();
		this.state = branchAddress.getState();
		this.country = branchAddress.getCountry();
		this.cep = branchAddress.getCep();
		this.complement = branchAddress.getComplement();
	}

	public String getId() {
		return id;
	}

	public String getStreet() {
		return street;
	}

	public int getNumber() {
		return number;
	}

	public String getDistrict() {
		return district;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getCep() {
		return cep;
	}

	public String getComplement() {
		return complement;
	}

}
