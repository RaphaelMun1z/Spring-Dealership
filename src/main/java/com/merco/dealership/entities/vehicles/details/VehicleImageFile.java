package com.merco.dealership.entities.vehicles.details;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merco.dealership.entities.abstractEntities.File;
import com.merco.dealership.entities.vehicles.Vehicle;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_vehicle_image_files")
public class VehicleImageFile extends File implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;

	public VehicleImageFile() {
	}

	public VehicleImageFile(String id, String name, String downloadUri, String type, Long size, Vehicle vehicle) {
		super(id, name, downloadUri, type, size);
		this.vehicle = vehicle;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}