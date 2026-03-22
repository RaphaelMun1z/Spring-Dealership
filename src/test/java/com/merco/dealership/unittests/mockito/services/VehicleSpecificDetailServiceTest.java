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
import org.springframework.hateoas.Link;

import com.merco.dealership.dto.req.VehicleSpecificDetailRequestDTO;
import com.merco.dealership.dto.res.VehicleSpecificDetailResponseDTO;
import com.merco.dealership.entities.vehicles.details.VehicleSpecificDetail;
import com.merco.dealership.repositories.VehicleSpecificDetailRepository;
import com.merco.dealership.services.VehicleSpecificDetailService;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockVehicleSpecificDetail;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Detalhes Específicos de Veículos")
class VehicleSpecificDetailServiceTest {

    private MockVehicleSpecificDetail input;

    @InjectMocks
    private VehicleSpecificDetailService service;

    @Mock
    private VehicleSpecificDetailRepository repository;

    @Mock
    private PagedResourcesAssembler<VehicleSpecificDetailResponseDTO> assembler;

    @BeforeEach
    void setUpMocks() {
        input = new MockVehicleSpecificDetail();
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar uma página de detalhes com sucesso")
    void testFindAll() {
        // Arrange
        Page<VehicleSpecificDetail> page = new PageImpl<>(input.mockEntityList());

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
    @DisplayName("Deve encontrar um detalhe por ID com sucesso")
    void testFindById() {
        // Arrange
        VehicleSpecificDetail entity = input.mockEntity("id1");
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        // Act
        var result = service.findById("id1");

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Detail - Testid1", result.getDetail());
        verify(repository, times(1)).findById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar detalhe com ID inexistente")
    void testFindByIdNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
    }

    // ─── create ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve criar um detalhe com sucesso")
    void testCreate() {
        // Arrange
        VehicleSpecificDetailRequestDTO dto = input.mockRequestDTO();
        VehicleSpecificDetail persisted = input.mockEntity("id1");

        when(repository.save(any(VehicleSpecificDetail.class))).thenReturn(persisted);

        // Act
        var result = service.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Detail - Testid1", result.getDetail());
        verify(repository, times(1)).save(any(VehicleSpecificDetail.class));
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar um detalhe com sucesso")
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
    @DisplayName("Deve lançar exceção ao deletar detalhe com ID inexistente")
    void testDeleteNotFound() {
        // Arrange
        when(repository.existsById("invalidId")).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete("invalidId"));
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar um detalhe parcialmente com sucesso")
    void testPatch() {
        // Arrange
        VehicleSpecificDetail existing = input.mockEntity("id1");
        VehicleSpecificDetail updated = input.mockEntity("id1");
        updated.setDetail("Detalhe Atualizado");

        VehicleSpecificDetailRequestDTO patchDTO = new VehicleSpecificDetailRequestDTO();
        patchDTO.setDetail("Detalhe Atualizado");

        when(repository.getReferenceById("id1")).thenReturn(existing);
        when(repository.save(any(VehicleSpecificDetail.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Detalhe Atualizado", result.getDetail());
        verify(repository, times(1)).getReferenceById("id1");
        verify(repository, times(1)).save(any(VehicleSpecificDetail.class));
    }
}