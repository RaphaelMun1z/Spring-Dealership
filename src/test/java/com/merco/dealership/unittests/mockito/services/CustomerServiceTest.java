package com.merco.dealership.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.merco.dealership.entities.customerResources.Customer;
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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import com.merco.dealership.dto.req.CustomerRequestDTO;
import com.merco.dealership.dto.res.CustomerResponseDTO;
import com.merco.dealership.repositories.CustomerRepository;
import com.merco.dealership.services.CustomerService;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockCustomer;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Clientes")
class CustomerServiceTest {

    private MockCustomer input;

    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    @Mock
    private PagedResourcesAssembler<CustomerResponseDTO> assembler;

    @BeforeEach
    void setUpMocks() {
        input = new MockCustomer();
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar uma página de clientes com sucesso")
    void testFindAll() {
        // Arrange
        Page<Customer> page = new PageImpl<>(input.mockEntityList());
        PagedModel<EntityModel<CustomerResponseDTO>> pagedModel = PagedModel.empty();

        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(assembler.toModel(any(Page.class), any(Link.class))).thenReturn(null);

        // Act
        var result = service.findAll(PageRequest.of(0, 12));

        // Assert
        verify(repository, times(1)).findAll(any(Pageable.class));
        verify(assembler, times(1)).toModel(any(Page.class), any(Link.class));
    }

    // ─── findById ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve encontrar um cliente por ID com sucesso")
    void testFindById() {
        // Arrange
        Customer entity = input.mockEntity("id1");
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        // Act
        var result = service.findById("id1");

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Name - Testid1", result.getName());
        assertEquals("customerid1@test.com", result.getEmail());
        verify(repository, times(1)).findById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente com ID inexistente")
    void testFindByIdNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
    }

    // ─── create ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve criar um cliente com sucesso")
    void testCreate() {
        // Arrange
        CustomerRequestDTO dto = input.mockRequestDTO();
        Customer persisted = input.mockEntity("id1");

        when(repository.save(any(Customer.class))).thenReturn(persisted);

        // Act
        var result = service.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Name - Testid1", result.getName());
        verify(repository, times(1)).save(any(Customer.class));
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar um cliente com sucesso")
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
    @DisplayName("Deve lançar exceção ao deletar cliente com ID inexistente")
    void testDeleteNotFound() {
        // Arrange
        when(repository.existsById("invalidId")).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete("invalidId"));
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar um cliente parcialmente com sucesso")
    void testPatch() {
        // Arrange
        Customer existing = input.mockEntity("id1");
        Customer updated = input.mockEntity("id1");
        updated.setName("Nome Atualizado");
        updated.setEmail("updated@test.com");

        CustomerRequestDTO patchDTO = new CustomerRequestDTO();
        patchDTO.setName("Nome Atualizado");
        patchDTO.setEmail("updated@test.com");

        when(repository.getReferenceById("id1")).thenReturn(existing);
        when(repository.save(any(Customer.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Nome Atualizado", result.getName());
        assertEquals("updated@test.com", result.getEmail());
        verify(repository, times(1)).getReferenceById("id1");
        verify(repository, times(1)).save(any(Customer.class));
    }
}