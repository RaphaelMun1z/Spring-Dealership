package com.merco.dealership.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.Customer;
import com.merco.dealership.entities.enums.ClientType;

public class CustomerResponseDTO  extends RepresentationModel<CustomerResponseDTO> {
	private String id;
	private String name;
	private String cpf;
	private String email;
	private String phone;
	private LocalDate birthDate;
	private LocalDate registrationDate;
	private ClientType clientType;
	private Boolean validCnh;

	public CustomerResponseDTO() {
	}

	public CustomerResponseDTO(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.cpf = customer.getCpf();
		this.email = customer.getEmail();
		this.phone = customer.getPhone();
		this.birthDate = customer.getBirthDate();
		this.registrationDate = customer.getRegistrationDate();
		this.clientType = customer.getClientType();
		this.validCnh = customer.getValidCnh();
	}

	public String getId() {
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public Boolean getValidCnh() {
		return validCnh;
	}

	public void setValidCnh(Boolean validCnh) {
		this.validCnh = validCnh;
	}

}
