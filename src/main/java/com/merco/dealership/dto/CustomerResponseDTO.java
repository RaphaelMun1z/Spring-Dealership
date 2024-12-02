package com.merco.dealership.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.merco.dealership.entities.Appointment;
import com.merco.dealership.entities.Customer;
import com.merco.dealership.entities.CustomerAddressMapping;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.entities.enums.ClientType;

public class CustomerResponseDTO {
	private String id;
	private String name;
	private String cpf;
	private String email;
	private String phone;
	private LocalDate birthDate;
	private LocalDate registrationDate;
	private ClientType clientType;
	private Boolean validCnh;
	private Set<CustomerAddressMapping> customerAddressesMapping = new HashSet<>();
	private Set<Appointment> appointments = new HashSet<>();
	private Set<Sale> sales = new HashSet<>();

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
		this.customerAddressesMapping = customer.getCustomerAddressesMapping();
		this.appointments = customer.getAppointments();
		this.sales = customer.getSales();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public Boolean getValidCnh() {
		return validCnh;
	}

	public Set<CustomerAddressMapping> getCustomerAddressesMapping() {
		return customerAddressesMapping;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public Set<Sale> getSales() {
		return sales;
	}

}
