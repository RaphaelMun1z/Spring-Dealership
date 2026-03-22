package com.merco.dealership.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.merco.dealership.dto.req.AdmPatchRequestDTO;
import com.merco.dealership.dto.req.AdmRegisterRequestDTO;
import com.merco.dealership.dto.req.LoginRequestDTO;
import com.merco.dealership.dto.res.AdmResponseDTO;
import com.merco.dealership.dto.res.LoginResponseDTO;

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
@DisplayName("Testes de Integração - Controller de Administradores")
class AdmIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private RequestSpecification specification;
    private ObjectMapper objectMapper;
    private AdmResponseDTO admCriado;

    @BeforeAll
    void configurarAmbiente() {

        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        LoginRequestDTO usuario = new LoginRequestDTO("admin@auto.com", "Auto123@");

        String accessToken =
                given()
                        .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
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
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/adm")
                .setPort(port)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    // ─── container ────────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("Deve iniciar o container PostgreSQL corretamente")
    void deveIniciarContainerPostgres() {
        assertTrue(Initializer.postgresql.isCreated());
        assertTrue(Initializer.postgresql.isRunning());
    }

    // ─── create ───────────────────────────────────────────────────────────────────

    @Test
    @Order(2)
    @DisplayName("Deve criar um administrador com sucesso")
    void deveCriarAdmComSucesso() throws IOException {

        AdmRegisterRequestDTO request = new AdmRegisterRequestDTO();
        request.setName("Adm Test");
        request.setEmail("adm.test@dealer.com");
        request.setPhone("(11) 91234-5678");
        request.setPassword("senha123");

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(request)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .extract()
                        .asString();

        admCriado = objectMapper.readValue(content, AdmResponseDTO.class);

        assertNotNull(admCriado);
        assertNotNull(admCriado.getId());
        assertFalse(admCriado.getId().isEmpty());
        assertEquals("Adm Test", admCriado.getName());
        assertEquals("adm.test@dealer.com", admCriado.getEmail());
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("Deve listar administradores com sucesso")
    void deveListarAdmsComSucesso() {

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    // ─── findById ─────────────────────────────────────────────────────────────────

    @Test
    @Order(4)
    @DisplayName("Deve buscar o administrador criado por ID com sucesso")
    void deveBuscarAdmPorId() throws IOException {

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", admCriado.getId())
                        .when()
                        .get("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        AdmResponseDTO admEncontrado = objectMapper.readValue(content, AdmResponseDTO.class);

        assertNotNull(admEncontrado);
        assertEquals(admCriado.getId(), admEncontrado.getId());
        assertEquals("Adm Test", admEncontrado.getName());
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("Deve atualizar parcialmente o administrador com sucesso")
    void deveAtualizarAdmParcialmente() throws IOException {

        AdmPatchRequestDTO patchRequest = new AdmPatchRequestDTO();
        patchRequest.setName("Adm Test Atualizado");

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", admCriado.getId())
                        .body(patchRequest)
                        .when()
                        .patch("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        AdmResponseDTO admAtualizado = objectMapper.readValue(content, AdmResponseDTO.class);

        assertNotNull(admAtualizado);
        assertEquals("Adm Test Atualizado", admAtualizado.getName());
        assertEquals("adm.test@dealer.com", admAtualizado.getEmail());
    }
}