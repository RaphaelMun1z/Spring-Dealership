package com.merco.dealership.entities;

import java.io.Serializable;
import java.util.Date;

public class Seller extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date hireDate;
	private Double salary;
	private Double commissionRate;
	private String status;

	public Seller() {

	}

	public Seller(Date hireDate, Double salary, Double commissionRate, String status) {
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
