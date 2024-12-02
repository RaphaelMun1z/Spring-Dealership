package com.merco.dealership.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_branches_address", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "street", "number", "district", "city", "state", "cep", "country" }) })
public class BranchAddress extends Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@OneToOne(mappedBy = "address")
	private Branch branch;

	public BranchAddress() {

	}

	public BranchAddress(String id, String street, int number, String district, String city, String state,
			String country, String cep, String complement) {
		super(id, street, number, district, city, state, country, cep, complement);
	}

	public Branch getBranch() {
		return branch;
	}

}
