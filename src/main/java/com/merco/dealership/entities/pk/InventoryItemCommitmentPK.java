package com.merco.dealership.entities.pk;

import java.io.Serializable;
import java.util.Objects;

import com.merco.dealership.entities.Appointment;
import com.merco.dealership.entities.InventoryItem;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class InventoryItemCommitmentPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "appointment_id")
	private Appointment appointment;

	@ManyToOne
	@JoinColumn(name = "inventory_item_id")
	private InventoryItem inventoryItem;

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public InventoryItem getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(InventoryItem inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(appointment, inventoryItem);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryItemCommitmentPK other = (InventoryItemCommitmentPK) obj;
		return Objects.equals(appointment, other.appointment) && Objects.equals(inventoryItem, other.inventoryItem);
	}

}
