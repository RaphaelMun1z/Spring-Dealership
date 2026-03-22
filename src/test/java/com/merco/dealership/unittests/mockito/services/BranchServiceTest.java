package com.merco.dealership.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.merco.dealership.entities.Branch;
import com.merco.dealership.repositories.BranchAddressRepository;
import com.merco.dealership.repositories.BranchRepository;
import com.merco.dealership.services.BranchService;
import com.merco.dealership.unittests.mapper.mocks.MockBranch;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Filiais")
class BranchServiceTest {

	private MockBranch input;

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
	}

	@Test
	@DisplayName("Deve retornar uma página de filiais com sucesso")
	void testFindAll() {
		List<Branch> list = input.mockEntityList();
		Page<Branch> page = new PageImpl<>(list);

		when(repository.findAll(any(Pageable.class))).thenReturn(page);

		var results = service.findAll(PageRequest.of(0, 12));

		assertNotNull(results);
		assertEquals(14, results.getTotalElements());

		var content = results.getContent();

		var resultOne = content.get(1);
		assertNotNull(resultOne);
		assertNotNull(resultOne.getId());
		assertNotNull(resultOne.getLinks());
		assertTrue(resultOne.toString().contains("links: [</branches/id1>;rel=\"self\"]"));

		assertNull(resultOne.getAddress());
		assertEquals("Name - Testid1", resultOne.getName());
		assertEquals("PhoneNumber - Testid1", resultOne.getPhoneNumber());
		assertEquals("emailid1@test.com", resultOne.getEmail());
		assertEquals("ManagerName - Testid1", resultOne.getManagerName());
		assertEquals("OpeningHours - Testid1", resultOne.getOpeningHours());
		assertEquals("BranchType - Testid1", resultOne.getBranchType());
		assertEquals("Status - Testid1", resultOne.getStatus());
		assertEquals(LocalDate.now(), resultOne.getCreatedAt());
		assertEquals(LocalDate.now(), resultOne.getUpdatedAt());

		var resultFour = content.get(4);
		assertNotNull(resultFour);
		assertNotNull(resultFour.getId());
		assertNotNull(resultFour.getLinks());
		assertTrue(resultFour.toString().contains("links: [</branches/id4>;rel=\"self\"]"));

		assertNull(resultFour.getAddress());
		assertEquals("Name - Testid4", resultFour.getName());
		assertEquals("PhoneNumber - Testid4", resultFour.getPhoneNumber());
		assertEquals("emailid4@test.com", resultFour.getEmail());
		assertEquals("ManagerName - Testid4", resultFour.getManagerName());
		assertEquals("OpeningHours - Testid4", resultFour.getOpeningHours());
		assertEquals("BranchType - Testid4", resultFour.getBranchType());
		assertEquals("Status - Testid4", resultFour.getStatus());
		assertEquals(LocalDate.now(), resultFour.getCreatedAt());
		assertEquals(LocalDate.now(), resultFour.getUpdatedAt());

		var resultSeven = content.get(7);
		assertNotNull(resultSeven);
		assertNotNull(resultSeven.getId());
		assertNotNull(resultSeven.getLinks());
		assertTrue(resultSeven.toString().contains("links: [</branches/id7>;rel=\"self\"]"));

		assertNull(resultSeven.getAddress());
		assertEquals("Name - Testid7", resultSeven.getName());
		assertEquals("PhoneNumber - Testid7", resultSeven.getPhoneNumber());
		assertEquals("emailid7@test.com", resultSeven.getEmail());
		assertEquals("ManagerName - Testid7", resultSeven.getManagerName());
		assertEquals("OpeningHours - Testid7", resultSeven.getOpeningHours());
		assertEquals("BranchType - Testid7", resultSeven.getBranchType());
		assertEquals("Status - Testid7", resultSeven.getStatus());
		assertEquals(LocalDate.now(), resultSeven.getCreatedAt());
		assertEquals(LocalDate.now(), resultSeven.getUpdatedAt());

		verify(repository, times(1)).findAll(any(Pageable.class));
	}

	@Test
	@DisplayName("Deve encontrar uma filial por ID com sucesso")
	void testFindById() {
		Branch entity = input.mockEntity("id1");
		when(repository.findById("id1")).thenReturn(Optional.of(entity));

		var result = service.findById("id1");

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
	@DisplayName("Deve criar uma nova filial com sucesso")
	void testCreate() {
		Branch persisted = input.mockEntity("id1");
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

		var result = service.create(dto);

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

		verify(repository, times(1)).save(any(Branch.class));
	}

	@Test
	@DisplayName("Deve deletar uma filial com sucesso")
	void testDelete() {
		when(repository.existsById("id1")).thenReturn(true);

		service.delete("id1");

		verify(repository, times(1)).existsById("id1");
		verify(repository, times(1)).deleteById("id1");
	}

	@Test
	@DisplayName("Deve atualizar uma filial parcialmente (Patch) com sucesso")
	void testPatch() {
		Branch existingEntity = input.mockEntity("id1");
		existingEntity.setName("Nome Antigo");

		Branch updatedEntity = input.mockEntity("id1");
		updatedEntity.setName("Nome Atualizado");

		when(repository.getReferenceById("id1")).thenReturn(existingEntity);
		when(repository.save(any(Branch.class))).thenReturn(updatedEntity);

		BranchRequestDTO patchDTO = new BranchRequestDTO();
		patchDTO.setName("Nome Atualizado");

		var result = service.patch("id1", patchDTO);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertEquals("Nome Atualizado", result.getName());
		assertTrue(result.toString().contains("links: [</branches/id1>;rel=\"self\"]"));

		verify(repository, times(1)).getReferenceById("id1");
		verify(repository, times(1)).save(any(Branch.class));
	}
}