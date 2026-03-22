package com.merco.dealership.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.merco.dealership.dto.req.LoginRequestDTO;
import com.merco.dealership.dto.req.SellerPatchRequestDTO;
import com.merco.dealership.dto.req.SellerRegisterRequestDTO;
import com.merco.dealership.dto.res.LoginResponseDTO;
import com.merco.dealership.dto.res.SellerResponseDTO;

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
@DisplayName("Testes de Integração - Controller de Vendedores")
class SellerIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private RequestSpecification specification;
    private ObjectMapper objectMapper;
    private SellerResponseDTO sellerCriado;

    @BeforeAll
    void configurarAmbiente() {

        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "/api";

        LoginRequestDTO usuario = new LoginRequestDTO("admin@dealer.com", "irineu123");

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
                .setBasePath("/api/sellers")
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
    @DisplayName("Deve criar um vendedor com sucesso")
    void deveCriarSellerComSucesso() throws IOException {

        SellerRegisterRequestDTO request = new SellerRegisterRequestDTO();
        request.setName("Seller Test");
        request.setEmail("seller.test@dealer.com");
        request.setPhone("(11) 97777-8888");
        request.setPassword("senha123");
        request.setHireDate(LocalDate.of(2023, 1, 15));
        request.setSalary(5000.0);
        request.setCommissionRate(0.05);
        request.setStatus("ACTIVE");

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

        sellerCriado = objectMapper.readValue(content, SellerResponseDTO.class);

        assertNotNull(sellerCriado);
        assertNotNull(sellerCriado.getId());
        assertFalse(sellerCriado.getId().isEmpty());
        assertEquals("Seller Test", sellerCriado.getName());
        assertEquals("seller.test@dealer.com", sellerCriado.getEmail());
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("Deve listar vendedores com sucesso")
    void deveListarSellersComSucesso() {

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
    @DisplayName("Deve buscar o vendedor criado por ID com sucesso")
    void deveBuscarSellerPorId() throws IOException {

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", sellerCriado.getId())
                        .when()
                        .get("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        SellerResponseDTO sellerEncontrado = objectMapper.readValue(content, SellerResponseDTO.class);

        assertNotNull(sellerEncontrado);
        assertEquals(sellerCriado.getId(), sellerEncontrado.getId());
        assertEquals("Seller Test", sellerEncontrado.getName());
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("Deve atualizar parcialmente o vendedor com sucesso")
    void deveAtualizarSellerParcialmente() throws IOException {

        SellerPatchRequestDTO patchRequest = new SellerPatchRequestDTO();
        patchRequest.setName("Seller Test Atualizado");
        patchRequest.setStatus("INACTIVE");

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", sellerCriado.getId())
                        .body(patchRequest)
                        .when()
                        .patch("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        SellerResponseDTO sellerAtualizado = objectMapper.readValue(content, SellerResponseDTO.class);

        assertNotNull(sellerAtualizado);
        assertEquals("Seller Test Atualizado", sellerAtualizado.getName());
        assertEquals("seller.test@dealer.com", sellerAtualizado.getEmail());
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @Order(6)
    @DisplayName("Deve deletar o vendedor com sucesso")
    void deveDeletarSellerComSucesso() {

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", sellerCriado.getId())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    // ─── findById após delete ─────────────────────────────────────────────────────

    @Test
    @Order(7)
    @DisplayName("Deve retornar 404 ao buscar vendedor deletado")
    void deveRetornar404AoBuscarSellerDeletado() {

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", sellerCriado.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(404);
    }
}