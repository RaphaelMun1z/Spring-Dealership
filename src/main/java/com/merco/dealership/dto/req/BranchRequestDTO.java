package com.merco.dealership.dto.req;

import java.time.LocalDate;

import com.merco.dealership.entities.validation.constraints.PhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class BranchRequestDTO {

    @NotNull(message = "Required field")
    private String name;

    @PhoneNumber(message = "Invalid field value")
    private String phoneNumber;

    @NotNull(message = "Required field")
    @Email(message = "Invalid field value")
    private String email;

    @NotNull(message = "Required field")
    private String managerName;

    @NotNull(message = "Required field")
    private String openingHours;

    @NotNull(message = "Required field")
    private String branchType;

    private String status;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @NotNull(message = "Required field")
    private String branchAddressId;

    public BranchRequestDTO() {
    }

    public BranchRequestDTO(String name, String phoneNumber, String email, String managerName, String openingHours,
                            String branchType, String status, LocalDate createdAt, LocalDate updatedAt, String branchAddressId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.managerName = managerName;
        this.openingHours = openingHours;
        this.branchType = branchType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.branchAddressId = branchAddressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBranchAddressId() {
        return branchAddressId;
    }

    public void setBranchAddressId(String branchAddressId) {
        this.branchAddressId = branchAddressId;
    }
}