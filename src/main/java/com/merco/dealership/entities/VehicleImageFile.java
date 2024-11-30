package com.merco.dealership.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class VehicleImageFile extends File implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;

	public VehicleImageFile() {
	}

	public VehicleImageFile(String id, String name, String path, String type, Long size, Vehicle vehicle) {
		super(id, name, path, type, size);
		this.vehicle = vehicle;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

}