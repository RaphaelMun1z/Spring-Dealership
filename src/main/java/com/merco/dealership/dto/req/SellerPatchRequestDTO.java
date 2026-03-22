package com.merco.dealership.dto.req;

import java.time.LocalDate;

import com.merco.dealership.entities.validation.constraints.PhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class SellerPatchRequestDTO {

    @Pattern(regexp = "^[A-Z]+(.)*")
    private String name;

    @PhoneNumber(message = "Invalid field value")
    private String phone;

    @Email(message = "Invalid field value")
    private String email;

    private LocalDate hireDate;
    private Double salary;
    private Double commissionRate;
    private String status;

    public SellerPatchRequestDTO() {
    }

    public SellerPatchRequestDTO(String name, String phone, String email, LocalDate hireDate, Double salary,
                                 Double commissionRate, String status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.hireDate = hireDate;
        this.salary = salary;
        this.commissionRate = commissionRate;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}