package com.merco.dealership.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;

import com.merco.dealership.dto.req.AppointmentRequestDTO;
import com.merco.dealership.dto.res.AppointmentResponseDTO;
import com.merco.dealership.entities.Appointment;
import com.merco.dealership.entities.customerResources.Customer;
import com.merco.dealership.entities.users.Seller;
import com.merco.dealership.repositories.AppointmentRepository;
import com.merco.dealership.repositories.CustomerRepository;
import com.merco.dealership.repositories.SellerRepository;
import com.merco.dealership.services.AppointmentService;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockAppointment;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Agendamentos")
class AppointmentServiceTest {

    private MockAppointment input;

    @InjectMocks
    private AppointmentService service;

    @Mock
    private AppointmentRepository repository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private PagedResourcesAssembler<AppointmentResponseDTO> assembler;

    @BeforeEach
    void setUpMocks() {
        input = new MockAppointment();
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar uma página de agendamentos com sucesso")
    void testFindAll() {
        // Arrange
        List<Appointment> list = input.mockEntityList();
        Page<Appointment> page = new PageImpl<>(list);

        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(assembler.toModel(any(Page.class), any(Link.class))).thenReturn(null);

        // Act
        service.findAll(PageRequest.of(0, 12));

        // Assert
        verify(repository, times(1)).findAll(any(Pageable.class));
        verify(assembler, times(1)).toModel(any(Page.class), any(Link.class));
    }

    // ─── findById ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve encontrar um agendamento por ID com sucesso")
    void testFindById() {
        // Arrange
        Appointment entity = input.mockEntity("id1");
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        // Act
        var result = service.findById("id1");

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals(LocalDate.now(), result.getDate());
        verify(repository, times(1)).findById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar agendamento com ID inexistente")
    void testFindByIdNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
        verify(repository, times(1)).findById("invalidId");
    }

    // ─── create ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve criar um agendamento com sucesso")
    void testCreate() {
        // Arrange
        AppointmentRequestDTO dto = input.mockRequestDTO("customerId1", "sellerId1");
        Appointment persisted = input.mockEntity("id1");

        when(customerRepository.findById("customerId1")).thenReturn(Optional.of(new Customer()));
        when(sellerRepository.findById("sellerId1")).thenReturn(Optional.of(new Seller()));
        when(repository.save(any(Appointment.class))).thenReturn(persisted);

        // Act
        var result = service.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        verify(repository, times(1)).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar agendamento com customer inexistente")
    void testCreateCustomerNotFound() {
        // Arrange
        AppointmentRequestDTO dto = input.mockRequestDTO("invalidCustomer", "sellerId1");
        when(customerRepository.findById("invalidCustomer")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar agendamento com seller inexistente")
    void testCreateSellerNotFound() {
        // Arrange
        AppointmentRequestDTO dto = input.mockRequestDTO("customerId1", "invalidSeller");
        when(customerRepository.findById("customerId1")).thenReturn(Optional.of(new Customer()));
        when(sellerRepository.findById("invalidSeller")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar um agendamento com sucesso")
    void testDelete() {
        // Arrange
        when(repository.existsById("id1")).thenReturn(true);

        // Act
        service.delete("id1");

        // Assert
        verify(repository, times(1)).existsById("id1");
        verify(repository, times(1)).deleteById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar agendamento com ID inexistente")
    void testDeleteNotFound() {
        // Arrange
        when(repository.existsById("invalidId")).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete("invalidId"));
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar um agendamento parcialmente com sucesso")
    void testPatch() {
        // Arrange
        Appointment existing = input.mockEntity("id1");
        Appointment updated = input.mockEntity("id1");
        AppointmentRequestDTO patchDTO = input.mockRequestDTO();

        when(repository.getReferenceById("id1")).thenReturn(existing);
        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer()));
        when(sellerRepository.findById(any())).thenReturn(Optional.of(new Seller()));
        when(repository.save(any(Appointment.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).getReferenceById("id1");
        verify(repository, times(1)).save(any(Appointment.class));
    }
}