package com.merco.dealership.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.BranchAddressRequestDTO;
import com.merco.dealership.dto.res.BranchAddressResponseDTO;
import com.merco.dealership.entities.BranchAddress;

public class MockBranchAddress {

    public BranchAddress mockEntity() {
        return mockEntity("id1");
    }

    public BranchAddress mockEntity(String id) {
        BranchAddress address = new BranchAddress();
        address.setId(id);
        address.setStreet("Street - Test" + id);
        address.setNumber(100);
        address.setDistrict("District - Test" + id);
        address.setCity("City - Test" + id);
        address.setState("State - Test" + id);
        address.setCountry("Country - Test" + id);
        address.setCep("12345-678");
        address.setComplement("Complement - Test" + id);
        return address;
    }

    public List<BranchAddress> mockEntityList() {
        List<BranchAddress> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public BranchAddressResponseDTO mockDTO() {
        return mockDTO("id1");
    }

    public BranchAddressResponseDTO mockDTO(String id) {
        BranchAddressResponseDTO dto = new BranchAddressResponseDTO();
        dto.setId(id);
        dto.setStreet("Street - Test" + id);
        dto.setNumber(100);
        dto.setDistrict("District - Test" + id);
        dto.setCity("City - Test" + id);
        dto.setState("State - Test" + id);
        dto.setCountry("Country - Test" + id);
        dto.setCep("12345-678");
        dto.setComplement("Complement - Test" + id);
        return dto;
    }

    public List<BranchAddressResponseDTO> mockDTOList() {
        List<BranchAddressResponseDTO> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockDTO("id" + i));
        }
        return list;
    }

    public BranchAddressRequestDTO mockRequestDTO() {
        BranchAddressRequestDTO dto = new BranchAddressRequestDTO();
        dto.setStreet("Street - Test");
        dto.setNumber(100);
        dto.setDistrict("District - Test");
        dto.setCity("City - Test");
        dto.setState("State - Test");
        dto.setCountry("Country - Test");
        dto.setCep("12345-678");
        dto.setComplement("Complement - Test");
        return dto;
    }
}