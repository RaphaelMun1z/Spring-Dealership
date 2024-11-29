package com.merco.dealership.entities.pk;

import java.io.Serializable;
import java.util.Objects;

import com.merco.dealership.entities.Vehicle;
import com.merco.dealership.entities.VehicleSpecificDetail;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class VehicleConfigurationPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;

	@ManyToOne
	@JoinColumn(name = "vehicle_specific_detail_id")
	private VehicleSpecificDetail vehicleSpecificDetail;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public VehicleSpecificDetail getVehicleSpecificDetail() {
		return vehicleSpecificDetail;
	}

	public void setVehicleSpecificDetail(VehicleSpecificDetail vehicleSpecificDetail) {
		this.vehicleSpecificDetail = vehicleSpecificDetail;
	}

	@Override
	public int hashCode() {
		return Objects.hash(vehicle, vehicleSpecificDetail);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleConfigurationPK other = (VehicleConfigurationPK) obj;
		return Objects.equals(vehicle, other.vehicle)
				&& Objects.equals(vehicleSpecificDetail, other.vehicleSpecificDetail);
	}

}
