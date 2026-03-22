package com.merco.dealership.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;

import com.merco.dealership.dto.req.InventoryItemRequestDTO;
import com.merco.dealership.dto.res.InventoryItemResponseDTO;
import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.entities.vehicles.OtherVehicleType;
import com.merco.dealership.entities.vehicles.Vehicle;
import com.merco.dealership.entities.vehicles.landVehicle.Bus;
import com.merco.dealership.entities.vehicles.landVehicle.Car;
import com.merco.dealership.entities.vehicles.landVehicle.Motorcycle;
import com.merco.dealership.entities.vehicles.landVehicle.Truck;
import com.merco.dealership.entities.vehicles.landVehicle.Van;
import com.merco.dealership.entities.vehicles.waterVehicle.Boat;
import com.merco.dealership.repositories.InventoryItemRepository;
import com.merco.dealership.repositories.VehicleRepository;
import com.merco.dealership.services.InventoryItemService;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockInventoryItem;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Itens de Inventário")
class InventoryItemServiceTest {

    private MockInventoryItem input;

    @InjectMocks
    private InventoryItemService service;

    @Mock
    private InventoryItemRepository repository;

    @Mock
    private VehicleRepository<Vehicle> vehicleRepository;

    @Mock
    private PagedResourcesAssembler<InventoryItemResponseDTO> assembler;

    @BeforeEach
    void setUpMocks() {
        input = new MockInventoryItem();
    }

    // ─── Fonte de dados: todos os tipos concretos de Vehicle ──────────────────────

    static Stream<Vehicle> allVehicleTypes() {
        return Stream.of(
                new Car(),
                new Bus(),
                new Motorcycle(),
                new Truck(),
                new Van(),
                new Boat(),
                new OtherVehicleType()
        );
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar uma página de itens de inventário com sucesso")
    void testFindAll() {
        // Arrange
        Page<InventoryItem> page = new PageImpl<>(input.mockEntityList());

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
    @DisplayName("Deve encontrar um item de inventário por ID com sucesso")
    void testFindById() {
        // Arrange
        InventoryItem entity = input.mockEntity("id1");
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        // Act
        var result = service.findById("id1");

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Supplier - Testid1", result.getSupplier());
        assertEquals(50000.0, result.getAcquisitionPrice());
        verify(repository, times(1)).findById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar item com ID inexistente")
    void testFindByIdNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
    }

    // ─── create (parametrizado por tipo de veículo) ───────────────────────────────

    @ParameterizedTest(name = "Deve criar item de inventário com veículo do tipo: {0}")
    @MethodSource("allVehicleTypes")
    @DisplayName("Deve criar um item de inventário para cada tipo de veículo")
    void testCreateWithAllVehicleTypes(Vehicle vehicle) {
        // Arrange
        InventoryItemRequestDTO dto = input.mockRequestDTO("vehicleId1");
        InventoryItem persisted = input.mockEntity("id1");

        when(vehicleRepository.findById("vehicleId1")).thenReturn(Optional.of(vehicle));
        when(repository.save(any(InventoryItem.class))).thenReturn(persisted);

        // Act
        var result = service.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Supplier - Testid1", result.getSupplier());
        assertEquals(50000.0, result.getAcquisitionPrice());
        verify(repository, times(1)).save(any(InventoryItem.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar item com vehicle inexistente")
    void testCreateVehicleNotFound() {
        // Arrange
        InventoryItemRequestDTO dto = input.mockRequestDTO("invalidVehicle");
        when(vehicleRepository.findById("invalidVehicle")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar um item de inventário com sucesso")
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
    @DisplayName("Deve lançar exceção ao deletar item com ID inexistente")
    void testDeleteNotFound() {
        // Arrange
        when(repository.existsById("invalidId")).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete("invalidId"));
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar um item de inventário parcialmente com sucesso")
    void testPatch() {
        // Arrange
        InventoryItem existing = input.mockEntity("id1");
        InventoryItem updated = input.mockEntity("id1");
        updated.setSupplier("Novo Fornecedor");

        InventoryItemRequestDTO patchDTO = new InventoryItemRequestDTO();
        patchDTO.setSupplier("Novo Fornecedor");

        when(repository.getReferenceById("id1")).thenReturn(existing);
        when(repository.save(any(InventoryItem.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Novo Fornecedor", result.getSupplier());
        verify(repository, times(1)).getReferenceById("id1");
        verify(repository, times(1)).save(any(InventoryItem.class));
    }

    // ─── patch com vehicle (parametrizado por tipo de veículo) ───────────────────

    @ParameterizedTest(name = "Deve atualizar vehicle do item para tipo: {0}")
    @MethodSource("allVehicleTypes")
    @DisplayName("Deve atualizar o vehicle de um item de inventário para cada tipo de veículo")
    void testPatchVehicleWithAllTypes(Vehicle vehicle) {
        // Arrange
        InventoryItem existing = input.mockEntity("id1");
        InventoryItem updated = input.mockEntity("id1");

        InventoryItemRequestDTO patchDTO = new InventoryItemRequestDTO();
        patchDTO.setVehicleId("vehicleId1");

        when(repository.getReferenceById("id1")).thenReturn(existing);
        when(vehicleRepository.findById("vehicleId1")).thenReturn(Optional.of(vehicle));
        when(repository.save(any(InventoryItem.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        verify(vehicleRepository, times(1)).findById("vehicleId1");
        verify(repository, times(1)).save(any(InventoryItem.class));
    }
}