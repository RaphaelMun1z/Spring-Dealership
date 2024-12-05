package com.merco.dealership.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
		fail("Not yet implemented");
	}

	@Test
	void testFindById() {
		Branch branch = input.mockEntity("_id_teste");
		branch.setId("id_teste");

		when(repository.findById("id_teste")).thenReturn(Optional.of(branch));

		var result = service.findById("id_teste");
		assertNotNull(result);
		assertNotNull(result.getResourceId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</branches/id_teste>;rel=\"self\"]"));
		assertEquals(null, result.getAddress());
		assertEquals("PhoneNumber - Test_id_teste", result.getPhoneNumber());
		assertEquals("Email - Test_id_teste", result.getEmail());
		assertEquals("ManagerName - Test_id_teste", result.getManagerName());
		assertEquals("OpeningHours - Test_id_teste", result.getOpeningHours());
		assertEquals("BranchType - Test_id_teste", result.getBranchType());
		assertEquals("Status - Test_id_teste", result.getStatus());
		assertEquals(LocalDate.now(), result.getCreatedAt());
		assertEquals(LocalDate.now(), result.getUpdatedAt());
	}

	@Test
	void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testPatch() {
		fail("Not yet implemented");
	}

}
