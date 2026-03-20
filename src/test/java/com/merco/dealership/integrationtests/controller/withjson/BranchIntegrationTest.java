package com.merco.dealership.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.merco.dealership.dto.BranchAddressResponseDTO;
import com.merco.dealership.dto.BranchResponseDTO;
import com.merco.dealership.dto.LoginRequestDTO;
import com.merco.dealership.dto.LoginResponseDTO;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merco.dealership.configs.TestConfigs;
import com.merco.dealership.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("integracao")
@DisplayName("Testes de Integração - Controller de Filiais")
class BranchIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private RequestSpecification specification;
    private ObjectMapper objectMapper;
    private BranchResponseDTO branch;

    @BeforeAll
    @DisplayName("Configuração inicial do ambiente de testes")
    void configurarAmbiente() {

        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "/api";

        LoginRequestDTO usuario =
                new LoginRequestDTO("admin@dealer.com", "irineu123");

        String accessToken =
                given()
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(usuario)
                        .when()
                        .post("/auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(LoginResponseDTO.class)
                        .getToken()
                        .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/api/branches")
                .setPort(port)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Deve iniciar o container PostgreSQL corretamente")
    void deveIniciarContainerPostgres() {
        assertTrue(Initializer.postgresql.isCreated());
        assertTrue(Initializer.postgresql.isRunning());
    }

    @Test
    @Order(2)
    @DisplayName("Deve criar uma filial com sucesso quando os dados forem válidos")
    void deveCriarFilialComSucesso() throws IOException {

        mockFilialValida();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(branch)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .extract()
                        .asString();

        BranchResponseDTO filialCriada =
                objectMapper.readValue(content, BranchResponseDTO.class);

        assertNotNull(filialCriada);
        assertNotNull(filialCriada.getId());
        assertFalse(filialCriada.getId().isEmpty());

        assertEquals("Branch Test", filialCriada.getName());
        assertEquals("(11) 91234-5678", filialCriada.getPhoneNumber());
        assertEquals("branch@test.com", filialCriada.getEmail());
        assertEquals("Manager Test", filialCriada.getManagerName());
        assertEquals("08:00 - 18:00", filialCriada.getOpeningHours());
        assertEquals("HEADQUARTERS", filialCriada.getBranchType());
        assertEquals("ACTIVE", filialCriada.getStatus());

        assertEquals(LocalDate.now(), filialCriada.getCreatedAt());
        assertEquals(LocalDate.now(), filialCriada.getUpdatedAt());

        assertNotNull(filialCriada.getAddress());
        assertEquals("Rua Teste", filialCriada.getAddress().getStreet());
        assertEquals(123, filialCriada.getAddress().getNumber());
        assertEquals("Centro", filialCriada.getAddress().getDistrict());
        assertEquals("São Paulo", filialCriada.getAddress().getCity());
        assertEquals("SP", filialCriada.getAddress().getState());
        assertEquals("Brasil", filialCriada.getAddress().getCountry());
        assertEquals("12345-678", filialCriada.getAddress().getCep());
        assertEquals("Sala 1", filialCriada.getAddress().getComplement());
    }

    private void mockFilialValida() {

        branch = new BranchResponseDTO();

        branch.setName("Branch Test");
        branch.setPhoneNumber("(11) 91234-5678");
        branch.setEmail("branch@test.com");
        branch.setManagerName("Manager Test");
        branch.setOpeningHours("08:00 - 18:00");
        branch.setBranchType("HEADQUARTERS");
        branch.setStatus("ACTIVE");
        branch.setCreatedAt(LocalDate.now());
        branch.setUpdatedAt(LocalDate.now());

        BranchAddressResponseDTO endereco = new BranchAddressResponseDTO();
        endereco.setStreet("Rua Teste");
        endereco.setNumber(123);
        endereco.setDistrict("Centro");
        endereco.setCity("São Paulo");
        endereco.setState("SP");
        endereco.setCountry("Brasil");
        endereco.setCep("12345-678");
        endereco.setComplement("Sala 1");

        branch.setAddress(endereco);
    }
}