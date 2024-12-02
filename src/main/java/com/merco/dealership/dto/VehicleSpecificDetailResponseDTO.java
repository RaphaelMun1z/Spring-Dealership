package com.merco.dealership.dto;

import com.merco.dealership.entities.VehicleSpecificDetail;

public class VehicleSpecificDetailResponseDTO {
	private String id;
	private String detail;

	public VehicleSpecificDetailResponseDTO() {
	}

	public VehicleSpecificDetailResponseDTO(VehicleSpecificDetail vehicleSpecificDetail) {
		this.id = vehicleSpecificDetail.getId();
		this.detail = vehicleSpecificDetail.getDetail();
	}

	public String getId() {
		return id;
	}

	public String getDetail() {
		return detail;
	}

}
