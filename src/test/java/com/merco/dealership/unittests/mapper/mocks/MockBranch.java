package com.merco.dealership.unittests.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.BranchResponseDTO;
import com.merco.dealership.entities.Branch;

public class MockBranch {
	public Branch mockEntity() {
		return mockEntity("_id_teste");
	}

	public BranchResponseDTO mockDTO() {
		return mockDTO(0);
	}

	public List<Branch> mockEntityList() {
		List<Branch> branches = new ArrayList<Branch>();
		for (int i = 0; i < 14; i++) {
			branches.add(mockEntity("_id_teste"));
		}
		return branches;
	}

	public List<BranchResponseDTO> mockDTOList() {
		List<BranchResponseDTO> branches = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			branches.add(mockDTO(i));
		}
		return branches;
	}

	public Branch mockEntity(String id) {
		Branch branch = new Branch();
		branch.setId("Id - Test" + id);
		branch.setAddress(null);
		branch.setPhoneNumber("PhoneNumber - Test" + id);
		branch.setEmail("Email - Test" + id);
		branch.setManagerName("ManagerName - Test" + id);
		branch.setOpeningHours("OpeningHours - Test" + id);
		branch.setBranchType("BranchType - Test" + id);
		branch.setStatus("Status - Test" + id);
		branch.setCreatedAt(LocalDate.now());
		branch.setUpdatedAt(LocalDate.now());
		return branch;
	}

	public BranchResponseDTO mockDTO(Integer number) {
		BranchResponseDTO branch = new BranchResponseDTO();
		branch.setId("ID - Test" + number);
		branch.setAddress(null);
		branch.setPhoneNumber("PhoneNumber - Test" + number);
		branch.setEmail("Email - Test" + number);
		branch.setManagerName("ManagerName - Test" + number);
		branch.setOpeningHours("OpeningHours - Test" + number);
		branch.setBranchType("BranchType - Test" + number);
		branch.setStatus("Status - Test" + number);
		branch.setCreatedAt(LocalDate.now());
		branch.setUpdatedAt(LocalDate.now());
		return branch;
	}
}
