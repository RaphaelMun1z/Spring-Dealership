package com.merco.dealership.unittests.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.req.AppointmentRequestDTO;
import com.merco.dealership.dto.res.AppointmentResponseDTO;
import com.merco.dealership.entities.Appointment;
import com.merco.dealership.entities.enums.AppointmentStatus;
import com.merco.dealership.entities.enums.AppointmentType;

public class MockAppointment {

    public Appointment mockEntity() {
        return mockEntity("id1");
    }

    public Appointment mockEntity(String id) {
        Appointment appointment = new Appointment();
        appointment.setId(id);
        appointment.setDate(LocalDate.now());
        appointment.setAppointmentType(AppointmentType.values()[0]);
        appointment.setAppointmentStatus(AppointmentStatus.values()[0]);
        appointment.setCustomer(null);
        appointment.setSeller(null);
        return appointment;
    }

    public List<Appointment> mockEntityList() {
        List<Appointment> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity("id" + i));
        }
        return list;
    }

    public AppointmentResponseDTO mockDTO() {
        return mockDTO("id1");
    }

    public AppointmentResponseDTO mockDTO(String id) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(id);
        dto.setDate(LocalDate.now());
        dto.setAppointmentType(AppointmentType.values()[0]);
        dto.setAppointmentStatus(AppointmentStatus.values()[0]);
        dto.setCustomer(null);
        dto.setSeller(null);
        return dto;
    }

    public List<AppointmentResponseDTO> mockDTOList() {
        List<AppointmentResponseDTO> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(mockDTO("id" + i));
        }
        return list;
    }

    public AppointmentRequestDTO mockRequestDTO() {
        return mockRequestDTO("customerId1", "sellerId1");
    }

    public AppointmentRequestDTO mockRequestDTO(String customerId, String sellerId) {
        AppointmentRequestDTO dto = new AppointmentRequestDTO();
        dto.setDate(LocalDate.now());
        dto.setAppointmentType(AppointmentType.values()[0]);
        dto.setAppointmentStatus(AppointmentStatus.values()[0]);
        dto.setCustomerId(customerId);
        dto.setSellerId(sellerId);
        return dto;
    }
}