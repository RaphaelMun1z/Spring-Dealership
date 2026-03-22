package com.merco.dealership.dto.req;

import java.time.LocalDate;

import com.merco.dealership.entities.enums.AppointmentStatus;
import com.merco.dealership.entities.enums.AppointmentType;

import jakarta.validation.constraints.NotNull;

public class AppointmentRequestDTO {

    @NotNull(message = "Required field")
    private LocalDate date;

    @NotNull(message = "Required field")
    private AppointmentType appointmentType;

    private AppointmentStatus appointmentStatus;

    @NotNull(message = "Required field")
    private String customerId;

    @NotNull(message = "Required field")
    private String sellerId;

    public AppointmentRequestDTO() {
    }

    public AppointmentRequestDTO(LocalDate date, AppointmentType appointmentType, AppointmentStatus appointmentStatus,
                                 String customerId, String sellerId) {
        this.date = date;
        this.appointmentType = appointmentType;
        this.appointmentStatus = appointmentStatus;
        this.customerId = customerId;
        this.sellerId = sellerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}