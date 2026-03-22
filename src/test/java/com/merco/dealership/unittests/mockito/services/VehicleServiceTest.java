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

import com.merco.dealership.dto.req.VehicleRequestDTO;
import com.merco.dealership.dto.res.VehicleResponseDTO;
import com.merco.dealership.entities.Branch;
import com.merco.dealership.entities.vehicles.Vehicle;
import com.merco.dealership.repositories.BranchRepository;
import com.merco.dealership.repositories.VehicleRepository;
import com.merco.dealership.services.VehicleService;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockVehicle;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Veículos")
class VehicleServiceTest {

    private MockVehicle input;

    @InjectMocks
    private VehicleService service;

    @Mock
    private VehicleRepository<Vehicle> repository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private PagedResourcesAssembler<VehicleResponseDTO> assembler;

    @BeforeEach
    void setUpMocks() {
        input = new MockVehicle();
    }

    // ─── Fonte de dados: todos os tipos com seus nomes corretos nos enums ─────────

    static Stream<String> allVehicleTypeStrings() {
        return Stream.of(
                "CAR",
                "MOTORCYCLE",
                "VAN",
                "TRUCK",
                "BUS",
                "BOAT",
                "OTHER_VEHICLE_TYPE"
        );
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar uma página de veículos com sucesso")
    void testFindAll() {
        // Arrange
        Page<Vehicle> page = new PageImpl<>(input.mockEntityList());

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
    @DisplayName("Deve encontrar um veículo por ID com sucesso")
    void testFindById() {
        // Arrange
        Vehicle entity = input.mockEntity("id1");
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        // Act
        var result = service.findById("id1");

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Brand - Testid1", result.getBrand());
        assertEquals("Model - Testid1", result.getModel());
        verify(repository, times(1)).findById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar veículo com ID inexistente")
    void testFindByIdNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
    }

    // ─── create (parametrizado por tipo de veículo) ───────────────────────────────

    @ParameterizedTest(name = "Deve criar veículo do tipo: {0}")
    @MethodSource("allVehicleTypeStrings")
    @DisplayName("Deve criar um veículo para cada tipo disponível")
    void testCreateWithAllVehicleTypes(String vehicleType) {
        // Arrange
        VehicleRequestDTO dto = input.mockRequestDTO("branchId1");
        dto.setType(vehicleType);

        Vehicle persisted = input.mockEntity("id1");
        Branch branch = new Branch();

        when(branchRepository.findById("branchId1")).thenReturn(Optional.of(branch));
        when(repository.save(any(Vehicle.class))).thenReturn(persisted);

        // Act
        var result = service.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals("Brand - Testid1", result.getBrand());
        verify(repository, times(1)).save(any(Vehicle.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar veículo com branch inexistente")
    void testCreateBranchNotFound() {
        // Arrange
        VehicleRequestDTO dto = input.mockRequestDTO("invalidBranch");
        when(branchRepository.findById("invalidBranch")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar um veículo com sucesso")
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
    @DisplayName("Deve lançar exceção ao deletar veículo com ID inexistente")
    void testDeleteNotFound() {
        // Arrange
        when(repository.existsById("invalidId")).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete("invalidId"));
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar um veículo parcialmente com sucesso")
    void testPatch() {
        // Arrange
        Vehicle existing = input.mockEntity("id1");
        Vehicle updated = input.mockEntity("id1");
        updated.setColor("Azul");
        updated.setSalePrice(90000.0);

        VehicleRequestDTO patchDTO = new VehicleRequestDTO();
        patchDTO.setColor("Azul");
        patchDTO.setSalePrice(90000.0);

        when(repository.findById("id1")).thenReturn(Optional.of(existing));
        when(repository.save(any(Vehicle.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Azul", result.getColor());
        assertEquals(90000.0, result.getSalePrice());
        verify(repository, times(1)).findById("id1");
        verify(repository, times(1)).save(any(Vehicle.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar veículo com ID inexistente")
    void testPatchNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> service.patch("invalidId", new VehicleRequestDTO()));
    }

    // ─── patch com branch (parametrizado por tipo de veículo) ────────────────────

    @ParameterizedTest(name = "Deve atualizar branch do veículo do tipo: {0}")
    @MethodSource("allVehicleTypeStrings")
    @DisplayName("Deve atualizar a branch de cada tipo de veículo")
    void testPatchBranchWithAllTypes(String vehicleType) {
        // Arrange — usa mockEntity que já tem type setado, evitando NPE no VehicleResponseDTO
        Vehicle existing = input.mockEntity("id1");
        Vehicle updated = input.mockEntity("id1");
        Branch newBranch = new Branch();

        VehicleRequestDTO patchDTO = new VehicleRequestDTO();
        patchDTO.setBranchId("branchId2");

        when(repository.findById("id1")).thenReturn(Optional.of(existing));
        when(branchRepository.findById("branchId2")).thenReturn(Optional.of(newBranch));
        when(repository.save(any(Vehicle.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        verify(branchRepository, times(1)).findById("branchId2");
        verify(repository, times(1)).save(any(Vehicle.class));
    }
}