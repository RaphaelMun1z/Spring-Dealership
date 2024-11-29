package com.merco.dealership.entities;

import java.io.Serializable;

import com.merco.dealership.entities.pk.VehicleConfigurationPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_vehicle_configurations")
public class VehicleConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VehicleConfigurationPK id = new VehicleConfigurationPK();

	public VehicleConfiguration() {

	}

	public VehicleConfiguration(Vehicle vehicle, VehicleSpecificDetail vehicleSpecificDetail) {
		super();
		this.id.setVehicle(vehicle);
		this.id.setVehicleSpecificDetail(vehicleSpecificDetail);
	}

	public VehicleConfigurationPK getId() {
		return id;
	}

	public Vehicle getVehicle() {
		return id.getVehicle();
	}

	public void setVehicle(Vehicle vehicle) {
		id.setVehicle(vehicle);
	}

	public VehicleSpecificDetail getVehicleSpecificDetail() {
		return id.getVehicleSpecificDetail();
	}

	public void setVehicleSpecificDetail(VehicleSpecificDetail vehicleSpecificDetail) {
		id.setVehicleSpecificDetail(vehicleSpecificDetail);
	}

}
