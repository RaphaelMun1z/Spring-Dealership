package com.merco.dealership.dto.req;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class InventoryItemRequestDTO {

    private LocalDate stockEntryDate;
    private LocalDate stockExitDate;
    private Double acquisitionPrice;
    private Double profitMargin;
    private String supplier;
    private String licensePlate;
    private String chassis;

    @NotNull(message = "Required field")
    private String vehicleId;

    public InventoryItemRequestDTO() {
    }

    public InventoryItemRequestDTO(LocalDate stockEntryDate, LocalDate stockExitDate, Double acquisitionPrice,
                                   Double profitMargin, String supplier, String licensePlate, String chassis, String vehicleId) {
        this.stockEntryDate = stockEntryDate;
        this.stockExitDate = stockExitDate;
        this.acquisitionPrice = acquisitionPrice;
        this.profitMargin = profitMargin;
        this.supplier = supplier;
        this.licensePlate = licensePlate;
        this.chassis = chassis;
        this.vehicleId = vehicleId;
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

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}