package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merco.dealership.entities.enums.ClientType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_customers")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	private String name;

	@NotNull(message = "Required field")
	private String cpf;

	@NotNull(message = "Required field")
	private String email;

	@NotNull(message = "Required field")
	private String phone;

	@NotNull(message = "Required field")
	private LocalDate birthDate;

	private LocalDate registrationDate;

	@NotNull(message = "Required field")
	private Integer clientType;

	@NotNull(message = "Required field")
	private Boolean validCnh;

	@OneToMany(mappedBy = "id.customer", orphanRemoval = true)
	private Set<CustomerAddressMapping> customerAddressesMapping = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private Set<Appointment> appointments = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private Set<Sale> sales = new HashSet<>();

	public Customer() {
		super();
	}

	public Customer(String id, @NotNull(message = "Required field") String name,
			@NotNull(message = "Required field") String cpf, @NotNull(message = "Required field") String email,
			@NotNull(message = "Required field") String phone, @NotNull(message = "Required field") LocalDate birthDate,
			LocalDate registrationDate, @NotNull(message = "Required field") ClientType clientType,
			@NotNull(message = "Required field") Boolean validCnh) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.phone = phone;
		this.birthDate = birthDate;
		this.registrationDate = registrationDate;
		setClientType(clientType);
		this.validCnh = validCnh;
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
		return ClientType.valueOf(clientType);
	}

	public void setClientType(ClientType clientType) {
		if (clientType != null) {
			this.clientType = clientType.getCode();
		}
	}

	public Boolean getValidCnh() {
		return validCnh;
	}

	public void setValidCnh(Boolean validCnh) {
		this.validCnh = validCnh;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public Set<Sale> getSales() {
		return sales;
	}

	public Set<CustomerAddressMapping> getCustomerAddressesMapping() {
		return customerAddressesMapping;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(id, other.id);
	}

}
