package com.merco.dealership.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merco.dealership.dto.LoginRequestDTO;
import com.merco.dealership.dto.LoginResponseDTO;
import com.merco.dealership.dto.TokenDTO;
import com.merco.dealership.entities.User;
import com.merco.dealership.infra.security.TokenService;
import com.merco.dealership.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@Autowired
	UserRepository<User> repository;

	@Transactional
	public LoginResponseDTO login(LoginRequestDTO data) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
			var auth = this.authenticationManager.authenticate(usernamePassword);
			TokenDTO token = tokenService.generateToken((User) auth.getPrincipal());
			return new LoginResponseDTO(token);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username);
	}
}
