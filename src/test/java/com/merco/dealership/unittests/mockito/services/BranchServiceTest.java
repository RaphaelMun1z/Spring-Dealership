package com.merco.dealership.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.merco.dealership.dto.req.BranchRequestDTO;
import com.merco.dealership.dto.res.BranchResponseDTO;
import com.merco.dealership.entities.Branch;
import com.merco.dealership.entities.BranchAddress;
import com.merco.dealership.repositories.BranchAddressRepository;
import com.merco.dealership.repositories.BranchRepository;
import com.merco.dealership.services.BranchService;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockBranch;
import com.merco.dealership.unittests.mapper.mocks.MockBranchAddress;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Filiais")
class BranchServiceTest {

	private MockBranch input;
	private MockBranchAddress inputAddress;

	@InjectMocks
	private BranchService service;

	@Mock
	private BranchRepository repository;

	@Mock
	private BranchAddressRepository branchAddressRepository;

	@BeforeEach
	@DisplayName("Configurar mocks antes de cada teste")
	void setUpMocks() {
		input = new MockBranch();
		inputAddress = new MockBranchAddress();
	}

	// ─── findAll ──────────────────────────────────────────────────────────────────

	@Test
	@DisplayName("Deve retornar uma página de filiais com sucesso")
	void testFindAll() {
		// Arrange
		List<Branch> list = input.mockEntityList();
		Page<Branch> page = new PageImpl<>(list);

		when(repository.findAll(any(Pageable.class))).thenReturn(page);

		// Act
		Page<BranchResponseDTO> result = service.findAll(PageRequest.of(0, 12));

		// Assert
		assertNotNull(result);
		assertEquals(14, result.getTotalElements());
		verify(repository, times(1)).findAll(any(Pageable.class));
	}

	// ─── findById ─────────────────────────────────────────────────────────────────

	@Test
	@DisplayName("Deve encontrar uma filial por ID com sucesso")
	void testFindById() {
		// Arrange
		Branch entity = input.mockEntity("id1");
		when(repository.findById("id1")).thenReturn(Optional.of(entity));

		// Act
		var result = service.findById("id1");

		// Assert
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</branches/id1>;rel=\"self\"]"));

		assertNull(result.getAddress());
		assertEquals("Name - Testid1", result.getName());
		assertEquals("PhoneNumber - Testid1", result.getPhoneNumber());
		assertEquals("emailid1@test.com", result.getEmail());
		assertEquals("ManagerName - Testid1", result.getManagerName());
		assertEquals("OpeningHours - Testid1", result.getOpeningHours());
		assertEquals("BranchType - Testid1", result.getBranchType());
		assertEquals("Status - Testid1", result.getStatus());
		assertEquals(LocalDate.now(), result.getCreatedAt());
		assertEquals(LocalDate.now(), result.getUpdatedAt());

		verify(repository, times(1)).findById("id1");
	}

	@Test
	@DisplayName("Deve lançar exceção ao buscar filial com ID inexistente")
	void testFindByIdNotFound() {
		// Arrange
		when(repository.findById("invalidId")).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
	}

	// ─── create ───────────────────────────────────────────────────────────────────

	@Test
	@DisplayName("Deve criar uma nova filial com sucesso")
	void testCreate() {
		// Arrange
		BranchAddress address = inputAddress.mockEntity("addressId1");
		Branch persisted = input.mockEntity("id1");

		when(branchAddressRepository.findById("addressId1")).thenReturn(Optional.of(address));
		when(repository.save(any(Branch.class))).thenReturn(persisted);

		BranchRequestDTO dto = new BranchRequestDTO();
		dto.setName("Name - Testid1");
		dto.setPhoneNumber("PhoneNumber - Testid1");
		dto.setEmail("emailid1@test.com");
		dto.setManagerName("ManagerName - Testid1");
		dto.setOpeningHours("OpeningHours - Testid1");
		dto.setBranchType("BranchType - Testid1");
		dto.setStatus("Status - Testid1");
		dto.setCreatedAt(LocalDate.now());
		dto.setUpdatedAt(LocalDate.now());
		dto.setBranchAddressId("addressId1");

		// Act
		var result = service.create(dto);

		// Assert
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</branches/id1>;rel=\"self\"]"));

		assertEquals("Name - Testid1", result.getName());
		assertEquals("PhoneNumber - Testid1", result.getPhoneNumber());
		assertEquals("emailid1@test.com", result.getEmail());
		assertEquals("ManagerName - Testid1", result.getManagerName());
		assertEquals("OpeningHours - Testid1", result.getOpeningHours());
		assertEquals("BranchType - Testid1", result.getBranchType());
		assertEquals("Status - Testid1", result.getStatus());
		assertEquals(LocalDate.now(), result.getCreatedAt());
		assertEquals(LocalDate.now(), result.getUpdatedAt());

		verify(branchAddressRepository, times(1)).findById("addressId1");
		verify(repository, times(1)).save(any(Branch.class));
	}

	@Test
	@DisplayName("Deve lançar exceção ao criar filial com endereço inexistente")
	void testCreateAddressNotFound() {
		// Arrange
		BranchRequestDTO dto = new BranchRequestDTO();
		dto.setBranchAddressId("invalidAddressId");

		when(branchAddressRepository.findById("invalidAddressId")).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
	}

	// ─── delete ───────────────────────────────────────────────────────────────────

	@Test
	@DisplayName("Deve deletar uma filial com sucesso")
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
	@DisplayName("Deve lançar exceção ao deletar filial com ID inexistente")
	void testDeleteNotFound() {
		// Arrange
		when(repository.existsById("invalidId")).thenReturn(false);

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> service.delete("invalidId"));
	}

	// ─── patch ────────────────────────────────────────────────────────────────────

	@Test
	@DisplayName("Deve atualizar uma filial parcialmente (Patch) com sucesso")
	void testPatch() {
		// Arrange
		Branch existingEntity = input.mockEntity("id1");
		existingEntity.setName("Nome Antigo");

		Branch updatedEntity = input.mockEntity("id1");
		updatedEntity.setName("Nome Atualizado");

		BranchRequestDTO patchDTO = new BranchRequestDTO();
		patchDTO.setName("Nome Atualizado");

		when(repository.getReferenceById("id1")).thenReturn(existingEntity);
		when(repository.save(any(Branch.class))).thenReturn(updatedEntity);

		// Act
		var result = service.patch("id1", patchDTO);

		// Assert
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals("Nome Atualizado", result.getName());

		verify(repository, times(1)).getReferenceById("id1");
		verify(repository, times(1)).save(any(Branch.class));
	}
}