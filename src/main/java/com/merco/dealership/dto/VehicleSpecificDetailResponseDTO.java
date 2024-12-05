package com.merco.dealership.dto;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.VehicleSpecificDetail;

public class VehicleSpecificDetailResponseDTO extends RepresentationModel<VehicleSpecificDetailResponseDTO> {
	private String id;
	private String detail;

	public VehicleSpecificDetailResponseDTO() {
	}

	public VehicleSpecificDetailResponseDTO(VehicleSpecificDetail vehicleSpecificDetail) {
		this.id = vehicleSpecificDetail.getId();
		this.detail = vehicleSpecificDetail.getDetail();
	}

	public String getResourceId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
