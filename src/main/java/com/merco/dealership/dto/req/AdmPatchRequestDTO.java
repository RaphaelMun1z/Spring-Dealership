package com.merco.dealership.dto.req;

import com.merco.dealership.entities.validation.constraints.PhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class AdmPatchRequestDTO {

    @Pattern(regexp = "^[A-Z]+(.)*")
    private String name;

    @PhoneNumber(message = "Invalid field value")
    private String phone;

    @Email(message = "Invalid field value")
    private String email;

    public AdmPatchRequestDTO() {
    }

    public AdmPatchRequestDTO(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
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
}