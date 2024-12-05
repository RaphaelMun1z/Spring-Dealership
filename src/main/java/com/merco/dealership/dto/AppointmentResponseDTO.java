package com.merco.dealership.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.Appointment;
import com.merco.dealership.entities.enums.AppointmentStatus;
import com.merco.dealership.entities.enums.AppointmentType;
import com.merco.dealership.mapper.Mapper;

public class AppointmentResponseDTO extends RepresentationModel<AppointmentResponseDTO> {
	private String id;
	private LocalDate date;
	private AppointmentType appointmentType;
	private AppointmentStatus appointmentStatus;
	private CustomerResponseDTO customer;
	private SellerResponseDTO seller;
	private List<InventoryItemResponseDTO> inventoryItemCommitments = new ArrayList<>();

	public AppointmentResponseDTO() {
	}

	public AppointmentResponseDTO(Appointment appointment) {
		this.id = appointment.getId();
		this.date = appointment.getDate();
		this.appointmentType = appointment.getAppointmentType();
		this.appointmentStatus = appointment.getAppointmentStatus();
		this.customer = Mapper.modelMapper(appointment.getCustomer(), CustomerResponseDTO.class);
		this.seller = Mapper.modelMapper(appointment.getSeller(), SellerResponseDTO.class);
		this.inventoryItemCommitments = Mapper.modelMapperList(appointment.getInventoryItemCommitments(),
				InventoryItemResponseDTO.class);
	}

	public String getResourceId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public CustomerResponseDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerResponseDTO customer) {
		this.customer = customer;
	}

	public SellerResponseDTO getSeller() {
		return seller;
	}

	public void setSeller(SellerResponseDTO seller) {
		this.seller = seller;
	}

	public List<InventoryItemResponseDTO> getInventoryItemCommitments() {
		return inventoryItemCommitments;
	}

	public void setInventoryItemCommitments(List<InventoryItemResponseDTO> inventoryItemCommitments) {
		this.inventoryItemCommitments = inventoryItemCommitments;
	}

}
