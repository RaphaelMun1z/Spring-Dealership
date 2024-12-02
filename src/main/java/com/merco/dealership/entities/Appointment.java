package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.merco.dealership.entities.enums.AppointmentStatus;
import com.merco.dealership.entities.enums.AppointmentType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private LocalDate date;

	@NotNull(message = "Required field")
	private Integer appointmentType;

	private Integer appointmentStatus;

	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;

	@OneToMany(mappedBy = "id.appointment", orphanRemoval = true)
	private Set<InventoryItemCommitment> inventoryItemCommitments = new HashSet<>();

	public Appointment() {
	}

	public Appointment(String id, @NotNull(message = "Required field") LocalDate date,
			@NotNull(message = "Required field") AppointmentType appointmentType, AppointmentStatus appointmentStatus,
			@NotNull(message = "Required field") Customer customer,
			@NotNull(message = "Required field") Seller seller) {
		super();
		this.id = id;
		this.date = date;
		setAppointmentType(appointmentType);
		setAppointmentStatus(appointmentStatus);
		this.customer = customer;
		this.seller = seller;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public AppointmentType getAppointmentType() {
		return AppointmentType.valueOf(appointmentType);
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		if (appointmentType != null) {
			this.appointmentType = appointmentType.getCode();
		}
	}

	public AppointmentStatus getAppointmentStatus() {
		return AppointmentStatus.valueOf(appointmentStatus);
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		if (appointmentStatus != null) {
			this.appointmentStatus = appointmentStatus.getCode();
		}
	}

	public Set<InventoryItemCommitment> getInventoryItemCommitments() {
		return inventoryItemCommitments;
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
