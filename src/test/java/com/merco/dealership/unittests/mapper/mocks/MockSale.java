package com.merco.dealership.unittests.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.SaleRequestDTO;
import com.merco.dealership.entities.Sale;

public class MockSale {

    public Sale mockEntity() {
        return mockEntity("id1");
    }

    public Sale mockEntity(String id) {
        Sale sale = new Sale();
        sale.setId(id);
        sale.setSaleDate(LocalDate.now());
        sale.setGrossAmount(50000.0);
        sale.setNetAmount(48000.0);
        sale.setAppliedDiscount(2000.0);
        sale.setPaymentMethod("CREDIT_CARD");
        sale.setInstallmentsNumber(12);
        sale.setReceipt("Receipt - Test" + id);
        sale.setSeller(null);
        sale.setCustomer(null);
        sale.setInventoryItem(null);
        return sale;
    }

    public List<Sale> mockEntityList() {
        List<Sale> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public SaleRequestDTO mockRequestDTO() {
        return mockRequestDTO("customerId1", "sellerId1", "inventoryId1");
    }

    public SaleRequestDTO mockRequestDTO(String customerId, String sellerId, String inventoryId) {
        SaleRequestDTO dto = new SaleRequestDTO();
        dto.setSaleDate(LocalDate.now());
        dto.setGrossAmount(50000.0);
        dto.setNetAmount(48000.0);
        dto.setAppliedDiscount(2000.0);
        dto.setPaymentMethod("CREDIT_CARD");
        dto.setInstallmentsNumber(12);
        dto.setReceipt("Receipt - Test");
        dto.setCustomerId(customerId);
        dto.setSellerId(sellerId);
        dto.setInventoryId(inventoryId);
        return dto;
    }
}