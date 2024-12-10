package com.merco.dealership.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations {
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.headers(headers -> headers
						.xssProtection(
								xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
						.contentSecurityPolicy(cps -> cps.policyDirectives("script-src 'self' .....")))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/swagger-ui.html", "/swagger-ui/*", "/v3/api-docs/**",
								"/swagger-resources/**", "/h2-console/**")
						.permitAll().requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers("/adm/**").hasAnyRole("ADM").requestMatchers("/sellers/**").hasAnyRole("ADM")
						.requestMatchers("/customers/**").hasAnyRole("ADM", "SELLER")
						.requestMatchers("/customers-address/**").hasAnyRole("ADM", "SELLER")
						.requestMatchers("/sales/**").hasAnyRole("ADM", "SELLER").requestMatchers("/appointments/**")
						.hasAnyRole("ADM", "SELLER").requestMatchers("/contracts/**").hasAnyRole("ADM", "SELLER")
						.requestMatchers("/branches/**").hasAnyRole("ADM", "SELLER")
						.requestMatchers("/branches-address/**").hasAnyRole("ADM", "SELLER")
						.requestMatchers("/inventory-items/**").hasAnyRole("ADM", "SELLER")
						.requestMatchers("/vehicles/**").hasAnyRole("ADM", "SELLER")
						.requestMatchers("/vehicle-specific-details/**").hasAnyRole("ADM", "SELLER")
						.requestMatchers("/users/**").authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
