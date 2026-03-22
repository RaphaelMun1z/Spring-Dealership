package com.merco.dealership.dto.req;

import jakarta.validation.constraints.NotNull;

public class VehicleSpecificDetailRequestDTO {

    @NotNull(message = "Required field")
    private String detail;

    public VehicleSpecificDetailRequestDTO() {
    }

    public VehicleSpecificDetailRequestDTO(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}