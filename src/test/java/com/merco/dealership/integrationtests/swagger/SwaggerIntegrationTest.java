package com.merco.dealership.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.merco.dealership.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("integracao")
@DisplayName("Testes de Integração - Swagger UI")
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@LocalServerPort
	private int port;

	@BeforeEach
	@DisplayName("Configuração da porta e contexto do RestAssured")
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	@Order(1)
	@DisplayName("Deve iniciar o container PostgreSQL corretamente")
	void connectionEstablished() {
		assertTrue(Initializer.postgresql.isCreated());
		assertTrue(Initializer.postgresql.isRunning());
	}

	@Test
	@Order(2)
	@DisplayName("Deve carregar a página HTML do Swagger UI com status 200")
	public void shouldDisplaySwaggerUiPage() {

		var content = given()
				.basePath("/")
				.when()
				.get("/swagger-ui/index.html")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		assertTrue(content.contains("Swagger UI"));
	}
}