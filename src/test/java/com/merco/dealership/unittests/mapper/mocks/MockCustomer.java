package com.merco.dealership.unittests.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.CustomerRequestDTO;
import com.merco.dealership.dto.res.CustomerResponseDTO;
import com.merco.dealership.entities.customerResources.Customer;
import com.merco.dealership.entities.enums.ClientType;

public class MockCustomer {

    public Customer mockEntity() {
        return mockEntity("id1");
    }

    public Customer mockEntity(String id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Name - Test" + id);
        customer.setCpf("000.000.000-0" + id.replace("id", ""));
        customer.setEmail("customer" + id + "@test.com");
        customer.setPhone("(11) 91234-567" + id.replace("id", ""));
        customer.setBirthDate(LocalDate.of(1990, 1, 1));
        customer.setRegistrationDate(LocalDate.now());
        customer.setClientType(ClientType.values()[0]);
        customer.setValidCnh(true);
        return customer;
    }

    public List<Customer> mockEntityList() {
        List<Customer> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public CustomerResponseDTO mockDTO() {
        return mockDTO("id1");
    }

    public CustomerResponseDTO mockDTO(String id) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(id);
        dto.setName("Name - Test" + id);
        dto.setCpf("000.000.000-0" + id.replace("id", ""));
        dto.setEmail("customer" + id + "@test.com");
        dto.setPhone("(11) 91234-567" + id.replace("id", ""));
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setRegistrationDate(LocalDate.now());
        dto.setClientType(ClientType.values()[0]);
        dto.setValidCnh(true);
        return dto;
    }

    public List<CustomerResponseDTO> mockDTOList() {
        List<CustomerResponseDTO> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockDTO("id" + i));
        }
        return list;
    }

    public CustomerRequestDTO mockRequestDTO() {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName("Name - Test");
        dto.setCpf("000.000.000-01");
        dto.setEmail("customer@test.com");
        dto.setPhone("(11) 91234-5671");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setRegistrationDate(LocalDate.now());
        dto.setClientType(ClientType.values()[0]);
        dto.setValidCnh(true);
        return dto;
    }
}