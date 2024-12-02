package com.merco.dealership.dto;

import com.merco.dealership.entities.CustomerAddress;

public class CustomerAddressResponseDTO {
	private String id;
	private String street;
	private int number;
	private String district;
	private String city;
	private String state;
	private String country;
	private String cep;
	private String complement;

	public CustomerAddressResponseDTO() {
	}

	public CustomerAddressResponseDTO(CustomerAddress customerAddress) {
		this.id = customerAddress.getId();
		this.street = customerAddress.getStreet();
		this.number = customerAddress.getNumber();
		this.district = customerAddress.getDistrict();
		this.city = customerAddress.getCity();
		this.state = customerAddress.getState();
		this.country = customerAddress.getCountry();
		this.cep = customerAddress.getCep();
		this.complement = customerAddress.getComplement();
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
