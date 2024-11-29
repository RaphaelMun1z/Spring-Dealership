package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merco.dealership.entities.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "tb_sellers")
public class Seller extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Required field")
	private LocalDate hireDate;

	@NotNull(message = "Required field")
	private Double salary;

	@NotNull(message = "Required field")
	private Double commissionRate;

	private String status;

	@JsonIgnore
	@OneToMany(mappedBy = "seller")
	private Set<Sale> sales = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "seller")
	private Set<Appointment> appointments = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "seller")
	private Set<Contract> contracts = new HashSet<>();

	public Seller() {

	}

	public Seller(String id, @NotNull(message = "Required field") @Pattern(regexp = "^[A-Z]+(.)*") String name,
			String phone, @NotNull(message = "Required field") @Email(message = "Invalid field value") String email,
			@NotNull(message = "Required field") String password, LocalDate hireDate, Double salary,
			Double commissionRate, String status) {
		super(id, name, phone, email, password, UserRole.SELLER);
		this.hireDate = hireDate;
		this.salary = salary;
		this.commissionRate = commissionRate;
		this.status = status;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Sale> getSales() {
		return sales;
	}

}
