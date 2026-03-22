package com.merco.dealership.dto.req;

import com.merco.dealership.entities.validation.constraints.CEP;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CustomerAddressRequestDTO {

    @NotNull(message = "Required field")
    @Pattern(regexp = "^[A-Z]+(.)*")
    private String street;

    @NotNull(message = "Required field")
    private Integer number;

    @NotNull(message = "Required field")
    @Pattern(regexp = "^[A-Z]+(.)*")
    private String district;

    @NotNull(message = "Required field")
    @Pattern(regexp = "^[A-Z]+(.)*")
    private String city;

    @NotNull(message = "Required field")
    @Pattern(regexp = "^[A-Z]+(.)*")
    private String state;

    @NotNull(message = "Required field")
    @Pattern(regexp = "^[A-Z]+(.)*")
    private String country;

    @CEP(message = "Invalid field value")
    private String cep;

    private String complement;

    public CustomerAddressRequestDTO() {
    }

    public CustomerAddressRequestDTO(String street, Integer number, String district, String city, String state,
                                     String country, String cep, String complement) {
        this.street = street;
        this.number = number;
        this.district = district;
        this.city = city;
        this.state = state;
        this.country = country;
        this.cep = cep;
        this.complement = complement;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}