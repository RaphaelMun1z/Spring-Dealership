package com.merco.dealership.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.AdmPatchRequestDTO;
import com.merco.dealership.dto.res.AdmResponseDTO;
import com.merco.dealership.entities.users.Adm;

public class MockAdm {

    public Adm mockEntity() {
        return mockEntity("id1");
    }

    public Adm mockEntity(String id) {
        Adm adm = new Adm();
        adm.setId(id);
        adm.setName("Name - Test" + id);
        adm.setPhone("(11) 91234-5678");
        adm.setEmail("adm" + id + "@test.com");
        adm.setPassword("encryptedPassword" + id);
        return adm;
    }

    public List<Adm> mockEntityList() {
        List<Adm> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public AdmResponseDTO mockDTO() {
        return mockDTO("id1");
    }

    public AdmResponseDTO mockDTO(String id) {
        AdmResponseDTO dto = new AdmResponseDTO();
        dto.setId(id);
        dto.setName("Name - Test" + id);
        dto.setPhone("(11) 91234-5678");
        dto.setEmail("adm" + id + "@test.com");
        return dto;
    }

    public List<AdmResponseDTO> mockDTOList() {
        List<AdmResponseDTO> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockDTO("id" + i));
        }
        return list;
    }

    public AdmPatchRequestDTO mockPatchRequestDTO() {
        AdmPatchRequestDTO dto = new AdmPatchRequestDTO();
        dto.setName("Name - Updated");
        dto.setPhone("(11) 99999-9999");
        dto.setEmail("adm.updated@test.com");
        return dto;
    }
}