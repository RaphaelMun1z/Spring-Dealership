package com.merco.dealership.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.merco.dealership.entities.Contract;
import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.entities.InventoryItemCommitment;
import com.merco.dealership.entities.Vehicle;

public class InventoryItemResponseDTO {
	private String id;
	private LocalDate stockEntryDate;
	private LocalDate stockExitDate;
	private Double acquisitionPrice;
	private Double profitMargin;
	private String supplier;
	private String licensePlate;
	private String chassis;
	private Vehicle vehicle;
	private Set<InventoryItemCommitment> inventoryItemCommitments = new HashSet<>();
	private Set<Contract> contracts = new HashSet<>();

	public InventoryItemResponseDTO() {
	}

	public InventoryItemResponseDTO(InventoryItem inventoryItem) {
		this.id = inventoryItem.getId();
		this.stockEntryDate = inventoryItem.getStockEntryDate();
		this.stockExitDate = inventoryItem.getStockExitDate();
		this.acquisitionPrice = inventoryItem.getAcquisitionPrice();
		this.profitMargin = inventoryItem.getProfitMargin();
		this.supplier = inventoryItem.getSupplier();
		this.licensePlate = inventoryItem.getLicensePlate();
		this.chassis = inventoryItem.getChassis();
		this.vehicle = inventoryItem.getVehicle();
		this.inventoryItemCommitments = inventoryItem.getInventoryItemCommitments();
		this.contracts = inventoryItem.getContracts();
	}

	public String getId() {
		return id;
	}

	public LocalDate getStockEntryDate() {
		return stockEntryDate;
	}

	public LocalDate getStockExitDate() {
		return stockExitDate;
	}

	public Double getAcquisitionPrice() {
		return acquisitionPrice;
	}

	public Double getProfitMargin() {
		return profitMargin;
	}

	public String getSupplier() {
		return supplier;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public String getChassis() {
		return chassis;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Set<InventoryItemCommitment> getInventoryItemCommitments() {
		return inventoryItemCommitments;
	}

	public Set<Contract> getContracts() {
		return contracts;
	}

}
