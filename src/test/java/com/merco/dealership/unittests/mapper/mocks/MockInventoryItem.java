package com.merco.dealership.unittests.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.InventoryItemRequestDTO;
import com.merco.dealership.entities.InventoryItem;

public class MockInventoryItem {

    public InventoryItem mockEntity() {
        return mockEntity("id1");
    }

    public InventoryItem mockEntity(String id) {
        InventoryItem item = new InventoryItem();
        item.setId(id);
        item.setStockEntryDate(LocalDate.now());
        item.setStockExitDate(null);
        item.setAcquisitionPrice(50000.0);
        item.setProfitMargin(0.15);
        item.setSupplier("Supplier - Test" + id);
        item.setLicensePlate("LicensePlate - Test" + id);
        item.setChassis("Chassis - Test" + id);
        item.setVehicle(null);
        return item;
    }

    public List<InventoryItem> mockEntityList() {
        List<InventoryItem> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public InventoryItemRequestDTO mockRequestDTO() {
        return mockRequestDTO("vehicleId1");
    }

    public InventoryItemRequestDTO mockRequestDTO(String vehicleId) {
        InventoryItemRequestDTO dto = new InventoryItemRequestDTO();
        dto.setStockEntryDate(LocalDate.now());
        dto.setAcquisitionPrice(50000.0);
        dto.setProfitMargin(0.15);
        dto.setSupplier("Supplier - Test");
        dto.setLicensePlate("LicensePlate - Test");
        dto.setChassis("Chassis - Test");
        dto.setVehicleId(vehicleId);
        return dto;
    }
}