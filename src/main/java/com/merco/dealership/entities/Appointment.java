package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_appointments")
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	private Customer customer;

	@NotNull(message = "Required field")
	private Vehicle vehicle;

	@NotNull(message = "Required field")
	private LocalDate date;

	@NotNull(message = "Required field")
	private Seller responsibleSeller;

	@NotNull(message = "Required field")
	private String appointmentType;

	private String appointmentStatus;

	public Appointment() {
	}

	public Appointment(String id, @NotNull(message = "Required field") Customer customer,
			@NotNull(message = "Required field") Vehicle vehicle, @NotNull(message = "Required field") LocalDate date,
			@NotNull(message = "Required field") Seller responsibleSeller,
			@NotNull(message = "Required field") String appointmentType, String appointmentStatus) {
		super();
		this.id = id;
		this.customer = customer;
		this.vehicle = vehicle;
		this.date = date;
		this.responsibleSeller = responsibleSeller;
		this.appointmentType = appointmentType;
		this.appointmentStatus = appointmentStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Seller getResponsibleSeller() {
		return responsibleSeller;
	}

	public void setResponsibleSeller(Seller responsibleSeller) {
		this.responsibleSeller = responsibleSeller;
	}

	public String getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		return Objects.equals(id, other.id);
	}

}
