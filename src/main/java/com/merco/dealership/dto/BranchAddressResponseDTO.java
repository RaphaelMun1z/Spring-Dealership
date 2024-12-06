package com.merco.dealership.dto;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.BranchAddress;
import com.merco.dealership.mapper.Mapper;

public class BranchAddressResponseDTO extends RepresentationModel<BranchAddressResponseDTO> {
	private String id;
	private String street;
	private int number;
	private String district;
	private String city;
	private String state;
	private String country;
	private String cep;
	private String complement;
	private BranchResponseDTO branch;

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
		this.branch = Mapper.modelMapper(branchAddress.getBranch(), BranchResponseDTO.class);
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

	public BranchResponseDTO getBranch() {
		return branch;
	}

	public void setBranch(BranchResponseDTO branch) {
		this.branch = branch;
	}

}
