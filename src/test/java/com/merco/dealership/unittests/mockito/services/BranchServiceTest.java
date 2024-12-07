package com.merco.dealership.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.merco.dealership.entities.Branch;
import com.merco.dealership.repositories.BranchRepository;
import com.merco.dealership.services.BranchService;
import com.merco.dealership.services.exceptions.RequiredObjectIsNullException;
import com.merco.dealership.unittests.mapper.mocks.MockBranch;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

	MockBranch input;

	@InjectMocks
	private BranchService service;

	@Mock
	BranchRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBranch();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		List<Branch> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var results = service.findAll();

		assertNotNull(results);
		assertEquals(14, results.size());

		var resultOne = results.get(1);

		assertNotNull(resultOne);
		assertNotNull(resultOne.getId());
		assertNotNull(resultOne.getLinks());

		assertTrue(resultOne.toString().contains("links: [</branches/id1>;rel=\"self\"]"));
		assertEquals(null, resultOne.getAddress());
		assertEquals("PhoneNumber - Testid1", resultOne.getPhoneNumber());
		assertEquals("Email - Testid1", resultOne.getEmail());
		assertEquals("ManagerName - Testid1", resultOne.getManagerName());
		assertEquals("OpeningHours - Testid1", resultOne.getOpeningHours());
		assertEquals("BranchType - Testid1", resultOne.getBranchType());
		assertEquals("Status - Testid1", resultOne.getStatus());
		assertEquals(LocalDate.now(), resultOne.getCreatedAt());
		assertEquals(LocalDate.now(), resultOne.getUpdatedAt());

		var resultFour = results.get(4);

		assertNotNull(resultFour);
		assertNotNull(resultFour.getId());
		assertNotNull(resultFour.getLinks());

		assertTrue(resultFour.toString().contains("links: [</branches/id4>;rel=\"self\"]"));
		assertEquals(null, resultFour.getAddress());
		assertEquals("PhoneNumber - Testid4", resultFour.getPhoneNumber());
		assertEquals("Email - Testid4", resultFour.getEmail());
		assertEquals("ManagerName - Testid4", resultFour.getManagerName());
		assertEquals("OpeningHours - Testid4", resultFour.getOpeningHours());
		assertEquals("BranchType - Testid4", resultFour.getBranchType());
		assertEquals("Status - Testid4", resultFour.getStatus());
		assertEquals(LocalDate.now(), resultFour.getCreatedAt());
		assertEquals(LocalDate.now(), resultFour.getUpdatedAt());

		var resultSeven = results.get(7);

		assertNotNull(resultSeven);
		assertNotNull(resultSeven.getId());
		assertNotNull(resultSeven.getLinks());

		assertTrue(resultSeven.toString().contains("links: [</branches/id7>;rel=\"self\"]"));
		assertEquals(null, resultSeven.getAddress());
		assertEquals("PhoneNumber - Testid7", resultSeven.getPhoneNumber());
		assertEquals("Email - Testid7", resultSeven.getEmail());
		assertEquals("ManagerName - Testid7", resultSeven.getManagerName());
		assertEquals("OpeningHours - Testid7", resultSeven.getOpeningHours());
		assertEquals("BranchType - Testid7", resultSeven.getBranchType());
		assertEquals("Status - Testid7", resultSeven.getStatus());
		assertEquals(LocalDate.now(), resultSeven.getCreatedAt());
		assertEquals(LocalDate.now(), resultSeven.getUpdatedAt());
	}

	@Test
	void testFindById() {
		Branch entity = input.mockEntity("id1");
		entity.setId("id1");

		when(repository.findById("id1")).thenReturn(Optional.of(entity));

		var result = service.findById("id1");

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</branches/id1>;rel=\"self\"]"));
		assertEquals(null, result.getAddress());
		assertEquals("PhoneNumber - Testid1", result.getPhoneNumber());
		assertEquals("Email - Testid1", result.getEmail());
		assertEquals("ManagerName - Testid1", result.getManagerName());
		assertEquals("OpeningHours - Testid1", result.getOpeningHours());
		assertEquals("BranchType - Testid1", result.getBranchType());
		assertEquals("Status - Testid1", result.getStatus());
		assertEquals(LocalDate.now(), result.getCreatedAt());
		assertEquals(LocalDate.now(), result.getUpdatedAt());
	}

	@Test
	void testCreate() {
		Branch entity = input.mockEntity("id1");
		entity.setId("id1");

		Branch persisted = entity;
		persisted.setId("id1");

		when(repository.save(entity)).thenReturn(persisted);

		var result = service.create(entity);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</branches/id1>;rel=\"self\"]"));
		assertEquals(null, result.getAddress());
		assertEquals("PhoneNumber - Testid1", result.getPhoneNumber());
		assertEquals("Email - Testid1", result.getEmail());
		assertEquals("ManagerName - Testid1", result.getManagerName());
		assertEquals("OpeningHours - Testid1", result.getOpeningHours());
		assertEquals("BranchType - Testid1", result.getBranchType());
		assertEquals("Status - Testid1", result.getStatus());
		assertEquals(LocalDate.now(), result.getCreatedAt());
		assertEquals(LocalDate.now(), result.getUpdatedAt());
	}

	@Test
	void testCreateWithNullObject() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "Required object is null.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() {
		Branch entity = input.mockEntity("id1");
		entity.setId("id1");

		when(repository.existsById("id1")).thenReturn(true);

		service.delete("id1");
	}

	@Test
	void testPatch() {

	}

}
