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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.merco.dealership.entities.Branch;
import com.merco.dealership.repositories.BranchRepository;
import com.merco.dealership.services.BranchService;
import com.merco.dealership.services.exceptions.RequiredObjectIsNullException;
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

	@BeforeEach
	@DisplayName("Configurar mocks antes de cada teste")
	void setUpMocks() {
		input = new MockBranch();
	}

	@Test
	@DisplayName("Deve retornar uma página de filiais com sucesso")
	void testFindAll() {
		// Arrange
		List<Branch> list = input.mockEntityList();
		Page<Branch> page = new PageImpl<>(list); // Transformando a lista mockada numa Page

		when(repository.findAll(any(Pageable.class))).thenReturn(page); // Mockando a chamada com Pageable

		// Act
		var results = service.findAll(PageRequest.of(0, 12)); // Passando um Pageable fake

		// Assert
		assertNotNull(results);
		assertEquals(14, results.getTotalElements());

		// Pegando a lista de DTOs convertidos dentro da página
		var content = results.getContent();

		// Validando a filial de índice 1
		var resultOne = content.get(1);
		assertNotNull(resultOne);
		assertNotNull(resultOne.getId());
		assertNotNull(resultOne.getLinks());
		assertTrue(resultOne.toString().contains("links: [</branches/id1>;rel=\"self\"]"));

		assertNull(resultOne.getAddress());
		assertEquals("PhoneNumber - Testid1", resultOne.getPhoneNumber());
		assertEquals("Email - Testid1", resultOne.getEmail());
		assertEquals("ManagerName - Testid1", resultOne.getManagerName());
		assertEquals("OpeningHours - Testid1", resultOne.getOpeningHours());
		assertEquals("BranchType - Testid1", resultOne.getBranchType());
		assertEquals("Status - Testid1", resultOne.getStatus());
		assertEquals(LocalDate.now(), resultOne.getCreatedAt());
		assertEquals(LocalDate.now(), resultOne.getUpdatedAt());

		// Validando a filial de índice 4
		var resultFour = content.get(4);
		assertNotNull(resultFour);
		assertNotNull(resultFour.getId());
		assertNotNull(resultFour.getLinks());
		assertTrue(resultFour.toString().contains("links: [</branches/id4>;rel=\"self\"]"));

		assertNull(resultFour.getAddress());
		assertEquals("PhoneNumber - Testid4", resultFour.getPhoneNumber());
		assertEquals("Email - Testid4", resultFour.getEmail());
		assertEquals("ManagerName - Testid4", resultFour.getManagerName());
		assertEquals("OpeningHours - Testid4", resultFour.getOpeningHours());
		assertEquals("BranchType - Testid4", resultFour.getBranchType());
		assertEquals("Status - Testid4", resultFour.getStatus());
		assertEquals(LocalDate.now(), resultFour.getCreatedAt());
		assertEquals(LocalDate.now(), resultFour.getUpdatedAt());

		// Validando a filial de índice 7
		var resultSeven = content.get(7);
		assertNotNull(resultSeven);
		assertNotNull(resultSeven.getId());
		assertNotNull(resultSeven.getLinks());
		assertTrue(resultSeven.toString().contains("links: [</branches/id7>;rel=\"self\"]"));

		assertNull(resultSeven.getAddress());
		assertEquals("PhoneNumber - Testid7", resultSeven.getPhoneNumber());
		assertEquals("Email - Testid7", resultSeven.getEmail());
		assertEquals("ManagerName - Testid7", resultSeven.getManagerName());
		assertEquals("OpeningHours - Testid7", resultSeven.getOpeningHours());
		assertEquals("BranchType - Testid7", resultSeven.getBranchType());
		assertEquals("Status - Testid7", resultSeven.getStatus());
		assertEquals(LocalDate.now(), resultSeven.getCreatedAt());
		assertEquals(LocalDate.now(), resultSeven.getUpdatedAt());

		// Verifica se o repositório foi chamado passando um Pageable
		verify(repository, times(1)).findAll(any(Pageable.class));
	}

	@Test
	@DisplayName("Deve encontrar uma filial por ID com sucesso")
	void testFindById() {
		// Arrange
		Branch entity = input.mockEntity("id1");
		entity.setId("id1");
		when(repository.findById("id1")).thenReturn(Optional.of(entity));

		// Act
		var result = service.findById("id1");

		// Assert
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</branches/id1>;rel=\"self\"]"));

		assertNull(result.getAddress());
		assertEquals("PhoneNumber - Testid1", result.getPhoneNumber());
		assertEquals("Email - Testid1", result.getEmail());
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
		// Arrange
		Branch entity = input.mockEntity("id1");
		entity.setId("id1");

		Branch persisted = input.mockEntity("id1");
		persisted.setId("id1");

		when(repository.save(entity)).thenReturn(persisted);

		// Act
		var result = service.create(entity);

		// Assert
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</branches/id1>;rel=\"self\"]"));

		assertNull(result.getAddress());
		assertEquals("PhoneNumber - Testid1", result.getPhoneNumber());
		assertEquals("Email - Testid1", result.getEmail());
		assertEquals("ManagerName - Testid1", result.getManagerName());
		assertEquals("OpeningHours - Testid1", result.getOpeningHours());
		assertEquals("BranchType - Testid1", result.getBranchType());
		assertEquals("Status - Testid1", result.getStatus());
		assertEquals(LocalDate.now(), result.getCreatedAt());
		assertEquals(LocalDate.now(), result.getUpdatedAt());

		verify(repository, times(1)).save(entity);
	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar criar filial com objeto nulo")
	void testCreateWithNullObject() {
		// Act & Assert
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "Required object is null";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	@DisplayName("Deve deletar uma filial com sucesso")
	void testDelete() {
		// Arrange
		when(repository.existsById("id1")).thenReturn(true);

		// Act
		service.delete("id1");

		// Assert
		verify(repository, times(1)).existsById("id1");
	}

	@Test
	@DisplayName("Deve atualizar uma filial parcialmente (Patch) com sucesso")
	void testPatch() {
		// Arrange
		Branch existingEntity = input.mockEntity("id1");
		existingEntity.setId("id1");
		existingEntity.setName("Nome Antigo");

		Branch patchData = new Branch();
		patchData.setName("Nome Atualizado");

		Branch updatedEntity = input.mockEntity("id1");
		updatedEntity.setId("id1");
		updatedEntity.setName("Nome Atualizado");

		// Alterado de findById para getReferenceById de acordo com o seu Service
		when(repository.getReferenceById("id1")).thenReturn(existingEntity);
		when(repository.save(any(Branch.class))).thenReturn(updatedEntity);

		// Act
		var result = service.patch("id1", patchData);

		// Assert
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());

		assertEquals("Nome Atualizado", result.getName());
		assertTrue(result.toString().contains("links: [</branches/id1>;rel=\"self\"]"));

		// Garante que o service buscou a referência e depois salvou
		verify(repository, times(1)).getReferenceById("id1");
		verify(repository, times(1)).save(any(Branch.class));
	}
}