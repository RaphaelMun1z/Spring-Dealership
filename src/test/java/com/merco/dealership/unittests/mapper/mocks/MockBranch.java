package com.merco.dealership.unittests.mapper.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.merco.dealership.dto.BranchResponseDTO;
import com.merco.dealership.entities.Branch;

public class MockBranch {
	public Branch mockEntity() {
		return mockEntity("id1");
	}

	public Branch mockEntity(String id) {
		Branch branch = new Branch();
		branch.setId(id);
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

	public List<Branch> mockEntityList() {
		List<Branch> branches = new ArrayList<Branch>();
		for (int i = 0; i < 14; i++) {
			branches.add(mockEntity(String.valueOf("id" + i)));
		}
		return branches;
	}

	public BranchResponseDTO mockDTO() {
		return mockDTO("id1");
	}

	public BranchResponseDTO mockDTO(String id) {
		BranchResponseDTO branch = new BranchResponseDTO();
		branch.setId(id);
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

	public List<BranchResponseDTO> mockDTOList() {
		List<BranchResponseDTO> branches = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			branches.add(mockDTO(String.valueOf("id" + i)));
		}
		return branches;
	}

}
