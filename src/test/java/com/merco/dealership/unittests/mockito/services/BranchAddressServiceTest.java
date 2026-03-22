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

import com.merco.dealership.dto.req.BranchAddressRequestDTO;
import com.merco.dealership.dto.res.BranchAddressResponseDTO;
import com.merco.dealership.entities.BranchAddress;
import com.merco.dealership.repositories.BranchAddressRepository;
import com.merco.dealership.services.BranchAddressService;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockBranchAddress;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Endereços de Filiais")
class BranchAddressServiceTest {

    private MockBranchAddress input;

    @InjectMocks
    private BranchAddressService service;

    @Mock
    private BranchAddressRepository repository;

    @Mock
    private PagedResourcesAssembler<BranchAddressResponseDTO> assembler;

    @BeforeEach
    void setUpMocks() {
        input = new MockBranchAddress();
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar uma página de endereços de filiais com sucesso")
    void testFindAll() {
        // Arrange
        Page<BranchAddress> page = new PageImpl<>(input.mockEntityList());
        PagedModel<EntityModel<BranchAddressResponseDTO>> pagedModel = PagedModel.empty();

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
    @DisplayName("Deve encontrar um endereço de filial por ID com sucesso")
    void testFindById() {
        // Arrange
        BranchAddress entity = input.mockEntity("id1");
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        // Act
        var result = service.findById("id1");

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Street - Testid1", result.getStreet());
        assertEquals("City - Testid1", result.getCity());
        verify(repository, times(1)).findById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar endereço com ID inexistente")
    void testFindByIdNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
    }

    // ─── create ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve criar um endereço de filial com sucesso")
    void testCreate() {
        // Arrange
        BranchAddressRequestDTO dto = input.mockRequestDTO();
        BranchAddress persisted = input.mockEntity("id1");

        when(repository.save(any(BranchAddress.class))).thenReturn(persisted);

        // Act
        var result = service.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Street - Testid1", result.getStreet());
        verify(repository, times(1)).save(any(BranchAddress.class));
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar um endereço de filial com sucesso")
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
    @DisplayName("Deve lançar exceção ao deletar endereço com ID inexistente")
    void testDeleteNotFound() {
        // Arrange
        when(repository.existsById("invalidId")).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete("invalidId"));
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar um endereço de filial parcialmente com sucesso")
    void testPatch() {
        // Arrange
        BranchAddress existing = input.mockEntity("id1");
        BranchAddress updated = input.mockEntity("id1");
        updated.setCity("New City");

        BranchAddressRequestDTO patchDTO = new BranchAddressRequestDTO();
        patchDTO.setCity("New City");

        when(repository.getReferenceById("id1")).thenReturn(existing);
        when(repository.save(any(BranchAddress.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals("New City", result.getCity());
        verify(repository, times(1)).getReferenceById("id1");
        verify(repository, times(1)).save(any(BranchAddress.class));
    }
}