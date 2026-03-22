package com.merco.dealership.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import com.merco.dealership.dto.req.ContractRequestDTO;
import com.merco.dealership.dto.res.ContractResponseDTO;
import com.merco.dealership.entities.Contract;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.repositories.ContractRepository;
import com.merco.dealership.repositories.SaleRepository;
import com.merco.dealership.services.ContractService;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockContract;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Contratos")
class ContractServiceTest {

    private MockContract input;

    @InjectMocks
    private ContractService service;

    @Mock
    private ContractRepository repository;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private PagedResourcesAssembler<ContractResponseDTO> assembler;

    @BeforeEach
    void setUpMocks() {
        input = new MockContract();
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar uma página de contratos com sucesso")
    void testFindAll() {
        // Arrange
        Page<Contract> page = new PageImpl<>(input.mockEntityList());
        PagedModel<EntityModel<ContractResponseDTO>> pagedModel = PagedModel.empty();

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
    @DisplayName("Deve encontrar um contrato por ID com sucesso")
    void testFindById() {
        // Arrange
        Contract entity = input.mockEntity("id1");
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        // Act
        var result = service.findById("id1");

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("ContractNumber - Testid1", result.getContractNumber());
        assertEquals(10000.0, result.getTotalAmount());
        verify(repository, times(1)).findById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar contrato com ID inexistente")
    void testFindByIdNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
    }

    // ─── create ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve criar um contrato com sucesso")
    void testCreate() {
        // Arrange
        ContractRequestDTO dto = input.mockRequestDTO("saleId1");
        Contract persisted = input.mockEntity("id1");

        when(saleRepository.findById("saleId1")).thenReturn(Optional.of(new Sale()));
        when(repository.save(any(Contract.class))).thenReturn(persisted);

        // Act
        var result = service.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("ContractNumber - Testid1", result.getContractNumber());
        verify(repository, times(1)).save(any(Contract.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar contrato com sale inexistente")
    void testCreateSaleNotFound() {
        // Arrange
        ContractRequestDTO dto = input.mockRequestDTO("invalidSale");
        when(saleRepository.findById("invalidSale")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar um contrato com sucesso")
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
    @DisplayName("Deve lançar exceção ao deletar contrato com ID inexistente")
    void testDeleteNotFound() {
        // Arrange
        when(repository.existsById("invalidId")).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete("invalidId"));
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar um contrato parcialmente com sucesso")
    void testPatch() {
        // Arrange
        Contract existing = input.mockEntity("id1");
        Contract updated = input.mockEntity("id1");
        updated.setTotalAmount(20000.0);

        ContractRequestDTO patchDTO = new ContractRequestDTO();
        patchDTO.setTotalAmount(20000.0);

        when(repository.getReferenceById("id1")).thenReturn(existing);
        when(repository.save(any(Contract.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals(20000.0, result.getTotalAmount());
        verify(repository, times(1)).getReferenceById("id1");
        verify(repository, times(1)).save(any(Contract.class));
    }
}