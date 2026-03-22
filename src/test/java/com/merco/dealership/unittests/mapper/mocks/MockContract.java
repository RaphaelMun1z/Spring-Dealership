package com.merco.dealership.unittests.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.ContractRequestDTO;
import com.merco.dealership.dto.res.ContractResponseDTO;
import com.merco.dealership.entities.Contract;
import com.merco.dealership.entities.enums.ContractStatus;
import com.merco.dealership.entities.enums.PaymentTerms;

public class MockContract {

    public Contract mockEntity() {
        return mockEntity("id1");
    }

    public Contract mockEntity(String id) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setContractNumber("ContractNumber - Test" + id);
        contract.setContractType("ContractType - Test" + id);
        contract.setContractDate(LocalDate.now());
        contract.setDeliveryDate(LocalDate.now().plusDays(30));
        contract.setTotalAmount(10000.0);
        contract.setPaymentTerms(PaymentTerms.values()[0]);
        contract.setContractStatus(ContractStatus.values()[0]);
        contract.setNotes("Notes - Test" + id);
        contract.setAttachments("Attachments - Test" + id);
        contract.setSale(null);
        return contract;
    }

    public List<Contract> mockEntityList() {
        List<Contract> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public ContractResponseDTO mockDTO() {
        return mockDTO("id1");
    }

    public ContractResponseDTO mockDTO(String id) {
        ContractResponseDTO dto = new ContractResponseDTO();
        dto.setId(id);
        dto.setContractNumber("ContractNumber - Test" + id);
        dto.setContractType("ContractType - Test" + id);
        dto.setContractDate(LocalDate.now());
        dto.setDeliveryDate(LocalDate.now().plusDays(30));
        dto.setTotalAmount(10000.0);
        dto.setPaymentTerms(PaymentTerms.values()[0]);
        dto.setContractStatus(ContractStatus.values()[0]);
        dto.setNotes("Notes - Test" + id);
        dto.setAttachments("Attachments - Test" + id);
        return dto;
    }

    public List<ContractResponseDTO> mockDTOList() {
        List<ContractResponseDTO> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockDTO("id" + i));
        }
        return list;
    }

    public ContractRequestDTO mockRequestDTO() {
        return mockRequestDTO("saleId1");
    }

    public ContractRequestDTO mockRequestDTO(String saleId) {
        ContractRequestDTO dto = new ContractRequestDTO();
        dto.setContractNumber("ContractNumber - Test");
        dto.setContractType("ContractType - Test");
        dto.setContractDate(LocalDate.now());
        dto.setDeliveryDate(LocalDate.now().plusDays(30));
        dto.setTotalAmount(10000.0);
        dto.setPaymentTerms(PaymentTerms.values()[0]);
        dto.setContractStatus(ContractStatus.values()[0]);
        dto.setNotes("Notes - Test");
        dto.setAttachments("Attachments - Test");
        dto.setSaleId(saleId);
        return dto;
    }
}