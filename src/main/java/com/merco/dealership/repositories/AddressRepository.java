package com.merco.dealership.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.Address;

public interface AddressRepository<T extends Address> extends JpaRepository<T, String> {
	Set<Address> findByStreet(String street);
}