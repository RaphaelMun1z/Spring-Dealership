package com.merco.dealership.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_sellers")
public class Seller extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Required field")
	private Date hireDate;

	@NotNull(message = "Required field")
	private Double salary;

	@NotNull(message = "Required field")
	private Double commissionRate;

	private String status;

	public Seller() {

	}

	public Seller(@NotNull(message = "Required field") Date hireDate,
			@NotNull(message = "Required field") Double salary,
			@NotNull(message = "Required field") Double commissionRate, String status) {
		super();
		this.hireDate = hireDate;
		this.salary = salary;
		this.commissionRate = commissionRate;
		this.status = status;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
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

}
