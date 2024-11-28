package com.merco.dealership.entities;

import java.io.Serializable;

import com.merco.dealership.entities.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_adms")
public class Adm extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	public Adm() {
		super();
	}

	public Adm(String id, String name, String phone, String email, String password) {
		super(id, name, phone, email, password, UserRole.ADM);
	}
}
