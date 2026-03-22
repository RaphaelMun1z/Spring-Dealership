package com.merco.dealership.unittests.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.SellerPatchRequestDTO;
import com.merco.dealership.entities.users.Seller;

public class MockSeller {

    public Seller mockEntity() {
        return mockEntity("id1");
    }

    public Seller mockEntity(String id) {
        Seller seller = new Seller();
        seller.setId(id);
        seller.setName("Name - Test" + id);
        seller.setPhone("(11) 91234-5678");
        seller.setEmail("seller" + id + "@test.com");
        seller.setPassword("encryptedPassword" + id);
        seller.setHireDate(LocalDate.of(2020, 1, 1));
        seller.setSalary(5000.0);
        seller.setCommissionRate(0.05);
        seller.setStatus("Active");
        return seller;
    }

    public List<Seller> mockEntityList() {
        List<Seller> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public SellerPatchRequestDTO mockPatchRequestDTO() {
        SellerPatchRequestDTO dto = new SellerPatchRequestDTO();
        dto.setName("Name - Updated");
        dto.setPhone("(11) 99999-9999");
        dto.setEmail("seller.updated@test.com");
        dto.setHireDate(LocalDate.of(2021, 6, 15));
        dto.setSalary(6000.0);
        dto.setCommissionRate(0.07);
        dto.setStatus("Inactive");
        return dto;
    }
}