package com.merco.dealership.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.merco.dealership.dto.req.AdmPatchRequestDTO;
import com.merco.dealership.entities.users.Adm;
import com.merco.dealership.repositories.AdmRepository;
import com.merco.dealership.services.AdmService;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockAdm;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Administradores")
class AdmServiceTest {

    private MockAdm input;

    @InjectMocks
    private AdmService service;

    @Mock
    private AdmRepository repository;

    @BeforeEach
    void setUpMocks() {
        input = new MockAdm();
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar uma lista de administradores com sucesso")
    void testFindAll() {
        // Arrange
        List<Adm> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);

        // Act
        var result = service.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(14, result.size());
        assertEquals("Name - Testid1", result.get(1).getName());
        assertEquals("admid1@test.com", result.get(1).getEmail());
        verify(repository, times(1)).findAll();
    }

    // ─── findById ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve encontrar um administrador por ID com sucesso")
    void testFindById() {
        // Arrange
        Adm entity = input.mockEntity("id1");
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        // Act
        var result = service.findById("id1");

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Name - Testid1", result.getName());
        assertEquals("admid1@test.com", result.getEmail());
        verify(repository, times(1)).findById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar administrador com ID inexistente")
    void testFindByIdNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar um administrador parcialmente com sucesso")
    void testPatch() {
        // Arrange
        Adm existing = input.mockEntity("id1");
        Adm updated = input.mockEntity("id1");
        updated.setName("Nome Atualizado");
        updated.setEmail("adm.updated@test.com");

        AdmPatchRequestDTO patchDTO = input.mockPatchRequestDTO();

        when(repository.getReferenceById("id1")).thenReturn(existing);
        when(repository.save(any(Adm.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Nome Atualizado", result.getName());
        assertEquals("adm.updated@test.com", result.getEmail());
        verify(repository, times(1)).getReferenceById("id1");
        verify(repository, times(1)).save(any(Adm.class));
    }
}