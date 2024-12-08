package com.merco.dealership.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.mapper.Mapper;

public class InventoryItemResponseDTO extends RepresentationModel<InventoryItemResponseDTO> {
	private String id;
	private LocalDate stockEntryDate;
	private LocalDate stockExitDate;
	private Double acquisitionPrice;
	private Double profitMargin;
	private String supplier;
	private String licensePlate;
	private String chassis;
	private VehicleResponseDTO vehicle;

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
		this.vehicle = Mapper.modelMapper(inventoryItem.getVehicle(), VehicleResponseDTO.class);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getStockEntryDate() {
		return stockEntryDate;
	}

	public void setStockEntryDate(LocalDate stockEntryDate) {
		this.stockEntryDate = stockEntryDate;
	}

	public LocalDate getStockExitDate() {
		return stockExitDate;
	}

	public void setStockExitDate(LocalDate stockExitDate) {
		this.stockExitDate = stockExitDate;
	}

	public Double getAcquisitionPrice() {
		return acquisitionPrice;
	}

	public void setAcquisitionPrice(Double acquisitionPrice) {
		this.acquisitionPrice = acquisitionPrice;
	}

	public Double getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(Double profitMargin) {
		this.profitMargin = profitMargin;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getChassis() {
		return chassis;
	}

	public void setChassis(String chassis) {
		this.chassis = chassis;
	}

	public VehicleResponseDTO getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleResponseDTO vehicle) {
		this.vehicle = vehicle;
	}

}
