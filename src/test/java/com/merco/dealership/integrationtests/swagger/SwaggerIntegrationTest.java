package com.merco.dealership.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.merco.dealership.configs.TestConfigs;
import com.merco.dealership.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.RestAssured;

@SpringBootApplication
@SpringBootTest(classes = SwaggerIntegrationTest.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {
	@LocalServerPort
	private String port;

	@BeforeEach
	void setUp() {
		RestAssured.port = Integer.parseInt(port);
	}

	@Test
	void connectionEstablished() {
		assertTrue(Initializer.postgresql.isCreated());
		assertTrue(Initializer.postgresql.isRunning());
	}

	@Test
	public void shouldDisplaySwaggerUiPage() {
		var content = given().auth().basic("user", "password")
				.basePath("/api/swagger-ui/index.html").port(TestConfigs.SERVER_PORT).when().get().then()
				.statusCode(200).extract().body().asString();
		assertTrue(content.contains("Swagger UI"));
	}

}
