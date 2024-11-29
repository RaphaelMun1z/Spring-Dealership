package com.merco.dealership.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_customers_address", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "street", "number", "district", "city", "state", "postalCode", "country" }) })
public class CustomerAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	private String street;

	@NotNull(message = "Required field")
	private int number;

	private String complement;

	@NotNull(message = "Required field")
	private String district;

	@NotNull(message = "Required field")
	private String city;

	@NotNull(message = "Required field")
	private String state;

	@NotNull(message = "Required field")
	private String postalCode;

	@NotNull(message = "Required field")
	private String country;

	@JsonIgnore
	@OneToMany(mappedBy = "address")
	private Set<Customer> customers = new HashSet<>();

	public CustomerAddress() {

	}

	public CustomerAddress(String id, @NotNull(message = "Required field") String street,
			@NotNull(message = "Required field") int number, String complement,
			@NotNull(message = "Required field") String district, @NotNull(message = "Required field") String city,
			@NotNull(message = "Required field") String state, @NotNull(message = "Required field") String postalCode,
			@NotNull(message = "Required field") String country) {
		super();
		this.id = id;
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.district = district;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
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

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Set<Customer> getCustomers() {
		return customers;
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
		CustomerAddress other = (CustomerAddress) obj;
		return Objects.equals(id, other.id);
	}

}
