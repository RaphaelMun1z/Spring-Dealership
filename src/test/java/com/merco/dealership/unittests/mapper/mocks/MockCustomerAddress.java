package com.merco.dealership.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.CustomerAddressRequestDTO;
import com.merco.dealership.dto.res.CustomerAddressResponseDTO;
import com.merco.dealership.entities.customerResources.CustomerAddress;

public class MockCustomerAddress {

    public CustomerAddress mockEntity() {
        return mockEntity("id1");
    }

    public CustomerAddress mockEntity(String id) {
        CustomerAddress address = new CustomerAddress();
        address.setId(id);
        address.setStreet("Street - Test" + id);
        address.setNumber(200);
        address.setDistrict("District - Test" + id);
        address.setCity("City - Test" + id);
        address.setState("State - Test" + id);
        address.setCountry("Country - Test" + id);
        address.setCep("98765-432");
        address.setComplement("Complement - Test" + id);
        return address;
    }

    public List<CustomerAddress> mockEntityList() {
        List<CustomerAddress> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public CustomerAddressResponseDTO mockDTO() {
        return mockDTO("id1");
    }

    public CustomerAddressResponseDTO mockDTO(String id) {
        CustomerAddressResponseDTO dto = new CustomerAddressResponseDTO();
        dto.setId(id);
        dto.setStreet("Street - Test" + id);
        dto.setNumber(200);
        dto.setDistrict("District - Test" + id);
        dto.setCity("City - Test" + id);
        dto.setState("State - Test" + id);
        dto.setCountry("Country - Test" + id);
        dto.setCep("98765-432");
        dto.setComplement("Complement - Test" + id);
        return dto;
    }

    public List<CustomerAddressResponseDTO> mockDTOList() {
        List<CustomerAddressResponseDTO> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockDTO("id" + i));
        }
        return list;
    }

    public CustomerAddressRequestDTO mockRequestDTO() {
        CustomerAddressRequestDTO dto = new CustomerAddressRequestDTO();
        dto.setStreet("Street - Test");
        dto.setNumber(200);
        dto.setDistrict("District - Test");
        dto.setCity("City - Test");
        dto.setState("State - Test");
        dto.setCountry("Country - Test");
        dto.setCep("98765-432");
        dto.setComplement("Complement - Test");
        return dto;
    }
}