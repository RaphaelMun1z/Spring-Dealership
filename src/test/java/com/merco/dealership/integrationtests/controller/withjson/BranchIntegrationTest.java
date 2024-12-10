package com.merco.dealership.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonParseException;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.merco.dealership.configs.TestConfigs;
import com.merco.dealership.dto.BranchResponseDTO;
import com.merco.dealership.dto.LoginRequestDTO;
import com.merco.dealership.dto.LoginResponseDTO;
import com.merco.dealership.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootApplication
@SpringBootTest(classes = BranchIntegrationTest.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BranchIntegrationTest extends AbstractIntegrationTest {
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static BranchResponseDTO branch;

	@LocalServerPort
	private String port;

	@BeforeEach
	void setup1() {
		RestAssured.port = Integer.parseInt(port);
	}

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		branch = new BranchResponseDTO();
	}

	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		LoginRequestDTO user = new LoginRequestDTO("irineu@gmail.com", "irineu123");

		System.out.println("-------------------------");
		try {
			var accessToken = given().basePath("/api/auth/login")
					.port(TestConfigs.SERVER_PORT).body(user).when().post().then().statusCode(200).extract().body()
					.as(LoginResponseDTO.class).getToken();
			//var accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYWRtIiwiaWF0IjoxNzMzNzU4MTE3LCJleHAiOjE3MzM4MDEzMTcsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hcGkiLCJzdWIiOiJpcmluZXVAZ21haWwuY29tIn0.179rPyN9toRVIv_lckwzuTEAJ-GEGtV7XBf3ts1EmNA";

			specification = new RequestSpecBuilder()
					.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
					.setBasePath("/api/branches").setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL)).build();
		} catch (Exception e) {
			System.out.println("===================Erro: " + e.getMessage());
		}
	}

	@Test
	@Order(1)
	public void connectionEstablished() throws JsonMappingException, JsonProcessingException {
		assertTrue(Initializer.postgresql.isCreated());
		assertTrue(Initializer.postgresql.isRunning());
	}

	@Test
	@Order(2)
	public void testCreate() throws JsonParseException, JsonMappingException, IOException {
		mockEntity();

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL).body(branch).when().post().then()
				.statusCode(200).extract().body().asString();

		BranchResponseDTO createdBranch = objectMapper.readValue(content, BranchResponseDTO.class);
		branch = createdBranch;

		assertNotNull(createdBranch);

		assertNotNull(createdBranch.getId());
		// assertNotNull(createdBranch.getAddress());
		assertNotNull(createdBranch.getPhoneNumber());
		assertNotNull(createdBranch.getEmail());
		assertNotNull(createdBranch.getManagerName());
		assertNotNull(createdBranch.getOpeningHours());
		assertNotNull(createdBranch.getBranchType());
		assertNotNull(createdBranch.getStatus());
		assertNotNull(createdBranch.getCreatedAt());
		assertNotNull(createdBranch.getUpdatedAt());

		assertTrue(createdBranch.getId().length() > 0);

		// assertEquals(null, createdBranch.getAddress());
		assertEquals("PhoneNumber - Test", createdBranch.getPhoneNumber());
		assertEquals("Email - Test", createdBranch.getEmail());
		assertEquals("ManagerName - Test", createdBranch.getManagerName());
		assertEquals("OpeningHours - Test", createdBranch.getOpeningHours());
		assertEquals("BranchType - Test", createdBranch.getBranchType());
		assertEquals("Status - Test", createdBranch.getStatus());
		assertEquals(LocalDate.now(), createdBranch.getCreatedAt());
		assertEquals(LocalDate.now(), createdBranch.getUpdatedAt());
	}

	private void mockEntity() throws JsonMappingException, JsonProcessingException {
		// branch.setAddress(null);
		branch.setPhoneNumber("PhoneNumber - Test");
		branch.setEmail("Email - Test");
		branch.setManagerName("ManagerName - Test");
		branch.setOpeningHours("OpeningHours - Test");
		branch.setBranchType("BranchType - Test");
		branch.setStatus("Status - Test");
		branch.setCreatedAt(LocalDate.now());
		branch.setUpdatedAt(LocalDate.now());
	}

}
