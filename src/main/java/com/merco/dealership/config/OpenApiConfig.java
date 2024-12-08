package com.merco.dealership.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("Dealership").version("v1").description("Dealership project")
				.termsOfService("https://portfolio.merco.com/dealership")
				.license(new License().name("Apache 2.0").url("https://portfolio.merco.com/dealership")));
	}
}
