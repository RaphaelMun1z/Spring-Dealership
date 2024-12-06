package com.merco.dealership.entities;

import java.io.Serializable;
import java.util.Objects;

import com.merco.dealership.entities.validation.constraints.CEP;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_addresses")
public abstract class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	@Pattern(regexp = "^[A-Z]+(.)*")
	private String street;

	@NotNull(message = "Required field")
	private Integer number;

	@NotNull(message = "Required field")
	@Pattern(regexp = "^[A-Z]+(.)*")
	private String district;

	@NotNull(message = "Required field")
	@Pattern(regexp = "^[A-Z]+(.)*")
	private String city;

	@NotNull(message = "Required field")
	@Pattern(regexp = "^[A-Z]+(.)*")
	private String state;

	@NotNull(message = "Required field")
	@Pattern(regexp = "^[A-Z]+(.)*")
	private String country;

	@CEP(message = "Invalid field value")
	private String cep;

	private String complement;

	protected Address() {

	}

	protected Address(String id, String street, Integer number, String district, String city, String state,
			String country, String cep, String complement) {
		this.id = id;
		this.street = street;
		this.number = number;
		this.district = district;
		this.city = city;
		this.state = state;
		this.country = country;
		this.cep = cep;
		this.complement = complement;
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
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
		Address other = (Address) obj;
		return Objects.equals(id, other.id);
	}
}