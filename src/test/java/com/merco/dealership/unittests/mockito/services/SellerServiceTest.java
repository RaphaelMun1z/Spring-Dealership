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

import com.merco.dealership.dto.req.SellerPatchRequestDTO;
import com.merco.dealership.dto.res.SellerResponseDTO;
import com.merco.dealership.entities.users.Seller;
import com.merco.dealership.repositories.SellerRepository;
import com.merco.dealership.services.SellerService;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockSeller;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Vendedores")
class SellerServiceTest {

    private MockSeller input;

    @InjectMocks
    private SellerService service;

    @Mock
    private SellerRepository repository;

    @Mock
    private PagedResourcesAssembler<SellerResponseDTO> assembler;

    @BeforeEach
    void setUpMocks() {
        input = new MockSeller();
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar uma página de vendedores com sucesso")
    void testFindAll() {
        // Arrange
        Page<Seller> page = new PageImpl<>(input.mockEntityList());

        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(assembler.toModel(any(Page.class), any(Link.class))).thenReturn(null);

        // Act
        service.findAll(PageRequest.of(0, 12));

        // Assert — o assembler é responsável pelo PagedModel, basta garantir que foi chamado
        verify(repository, times(1)).findAll(any(Pageable.class));
        verify(assembler, times(1)).toModel(any(Page.class), any(Link.class));
    }

    // ─── findById ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve encontrar um vendedor por ID com sucesso")
    void testFindById() {
        // Arrange
        Seller entity = input.mockEntity("id1");
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        // Act
        var result = service.findById("id1");

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Name - Testid1", result.getName());
        assertEquals("sellerid1@test.com", result.getEmail());
        verify(repository, times(1)).findById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar vendedor com ID inexistente")
    void testFindByIdNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar um vendedor com sucesso")
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
    @DisplayName("Deve lançar exceção ao deletar vendedor com ID inexistente")
    void testDeleteNotFound() {
        // Arrange
        when(repository.existsById("invalidId")).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete("invalidId"));
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar nome e email do vendedor com sucesso")
    void testPatch() {
        // Arrange
        Seller existing = input.mockEntity("id1");
        Seller updated = input.mockEntity("id1");
        updated.setName("Nome Atualizado");
        updated.setEmail("seller.updated@test.com");

        SellerPatchRequestDTO patchDTO = input.mockPatchRequestDTO();

        when(repository.getReferenceById("id1")).thenReturn(existing);
        when(repository.save(any(Seller.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Nome Atualizado", result.getName());
        assertEquals("seller.updated@test.com", result.getEmail());
        verify(repository, times(1)).getReferenceById("id1");
        verify(repository, times(1)).save(any(Seller.class));
    }

    @Test
    @DisplayName("Deve atualizar apenas o nome do vendedor")
    void testPatchOnlyName() {
        // Arrange
        Seller existing = input.mockEntity("id1");
        Seller updated = input.mockEntity("id1");
        updated.setName("Apenas Nome Atualizado");

        SellerPatchRequestDTO patchDTO = new SellerPatchRequestDTO();
        patchDTO.setName("Apenas Nome Atualizado");

        when(repository.getReferenceById("id1")).thenReturn(existing);
        when(repository.save(any(Seller.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Apenas Nome Atualizado", result.getName());
        verify(repository, times(1)).save(any(Seller.class));
    }
}