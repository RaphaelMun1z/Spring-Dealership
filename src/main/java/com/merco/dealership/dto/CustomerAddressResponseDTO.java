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

	public void setId(String id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

}
