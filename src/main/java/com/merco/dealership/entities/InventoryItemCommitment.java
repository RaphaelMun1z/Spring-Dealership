package com.merco.dealership.entities;

import java.io.Serializable;

import com.merco.dealership.entities.pk.InventoryItemCommitmentPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_inventory_item_commitments")
public class InventoryItemCommitment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InventoryItemCommitmentPK id = new InventoryItemCommitmentPK();

	public InventoryItemCommitment() {

	}

	public InventoryItemCommitment(Appointment appointment, InventoryItem item) {
		super();
		this.id.setAppointment(appointment);
		this.id.setInventoryItem(item);
	}

	public InventoryItemCommitmentPK getId() {
		return id;
	}

	public Appointment getAppointment() {
		return id.getAppointment();
	}

	public void setAppointment(Appointment appointment) {
		id.setAppointment(appointment);
	}

	public InventoryItem getInventoryItem() {
		return id.getInventoryItem();
	}

	public void setInventoryItem(InventoryItem inventoryItem) {
		id.setInventoryItem(inventoryItem);
	}
}
