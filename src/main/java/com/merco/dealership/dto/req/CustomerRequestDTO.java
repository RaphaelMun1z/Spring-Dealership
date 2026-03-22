package com.merco.dealership.dto.req;

import java.time.LocalDate;

import com.merco.dealership.entities.enums.ClientType;

import jakarta.validation.constraints.NotNull;

public class CustomerRequestDTO {

    @NotNull(message = "Required field")
    private String name;

    @NotNull(message = "Required field")
    private String cpf;

    @NotNull(message = "Required field")
    private String email;

    @NotNull(message = "Required field")
    private String phone;

    @NotNull(message = "Required field")
    private LocalDate birthDate;

    private LocalDate registrationDate;

    @NotNull(message = "Required field")
    private ClientType clientType;

    @NotNull(message = "Required field")
    private Boolean validCnh;

    public CustomerRequestDTO() {
    }

    public CustomerRequestDTO(String name, String cpf, String email, String phone, LocalDate birthDate,
                              LocalDate registrationDate, ClientType clientType, Boolean validCnh) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.clientType = clientType;
        this.validCnh = validCnh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Boolean getValidCnh() {
        return validCnh;
    }

    public void setValidCnh(Boolean validCnh) {
        this.validCnh = validCnh;
    }
}