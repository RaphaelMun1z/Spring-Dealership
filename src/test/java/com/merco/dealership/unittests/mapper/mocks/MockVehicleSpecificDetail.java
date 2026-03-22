package com.merco.dealership.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.VehicleSpecificDetailRequestDTO;
import com.merco.dealership.dto.res.VehicleSpecificDetailResponseDTO;
import com.merco.dealership.entities.vehicles.details.VehicleSpecificDetail;

public class MockVehicleSpecificDetail {

    public VehicleSpecificDetail mockEntity() {
        return mockEntity("id1");
    }

    public VehicleSpecificDetail mockEntity(String id) {
        VehicleSpecificDetail detail = new VehicleSpecificDetail();
        detail.setId(id);
        detail.setDetail("Detail - Test" + id);
        return detail;
    }

    public List<VehicleSpecificDetail> mockEntityList() {
        List<VehicleSpecificDetail> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public VehicleSpecificDetailResponseDTO mockDTO() {
        return mockDTO("id1");
    }

    public VehicleSpecificDetailResponseDTO mockDTO(String id) {
        VehicleSpecificDetailResponseDTO dto = new VehicleSpecificDetailResponseDTO();
        dto.setId(id);
        dto.setDetail("Detail - Test" + id);
        return dto;
    }

    public List<VehicleSpecificDetailResponseDTO> mockDTOList() {
        List<VehicleSpecificDetailResponseDTO> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockDTO("id" + i));
        }
        return list;
    }

    public VehicleSpecificDetailRequestDTO mockRequestDTO() {
        VehicleSpecificDetailRequestDTO dto = new VehicleSpecificDetailRequestDTO();
        dto.setDetail("Detail - Test");
        return dto;
    }
}