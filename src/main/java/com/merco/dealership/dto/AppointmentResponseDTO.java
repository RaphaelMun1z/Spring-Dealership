package com.merco.dealership.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.merco.dealership.entities.Appointment;
import com.merco.dealership.entities.Customer;
import com.merco.dealership.entities.InventoryItemCommitment;
import com.merco.dealership.entities.Seller;
import com.merco.dealership.entities.enums.AppointmentStatus;
import com.merco.dealership.entities.enums.AppointmentType;

public class AppointmentResponseDTO {
	private String id;
	private LocalDate date;
	private AppointmentType appointmentType;
	private AppointmentStatus appointmentStatus;
	private Customer customer;
	private Seller seller;
	private Set<InventoryItemCommitment> inventoryItemCommitments = new HashSet<>();

	public AppointmentResponseDTO() {
	}

	public AppointmentResponseDTO(Appointment appointment) {
		this.id = appointment.getId();
		this.date = appointment.getDate();
		this.appointmentType = appointment.getAppointmentType();
		this.appointmentStatus = appointment.getAppointmentStatus();
		this.customer = appointment.getCustomer();
		this.seller = appointment.getSeller();
		this.inventoryItemCommitments = appointment.getInventoryItemCommitments();
	}

	public String getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Seller getSeller() {
		return seller;
	}

	public Set<InventoryItemCommitment> getInventoryItemCommitments() {
		return inventoryItemCommitments;
	}

}
