package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.merco.dealership.entities.User;

public interface UserRepository<T extends User> extends JpaRepository<T, String> {
	UserDetails findByEmail(String email);
}