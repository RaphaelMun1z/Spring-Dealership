package com.merco.dealership.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.merco.dealership.dto.req.LoginRequestDTO;
import com.merco.dealership.dto.req.VehicleSpecificDetailRequestDTO;
import com.merco.dealership.dto.res.LoginResponseDTO;
import com.merco.dealership.dto.res.VehicleSpecificDetailResponseDTO;

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
@DisplayName("Testes de Integração - Controller de Detalhes Específicos de Veículos")
class VehicleSpecificDetailIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private RequestSpecification specification;
    private ObjectMapper objectMapper;
    private VehicleSpecificDetailResponseDTO detalheCriado;

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
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/vehicle-specific-details")
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
    @DisplayName("Deve criar um detalhe específico com sucesso")
    void deveCriarDetalheComSucesso() throws IOException {

        VehicleSpecificDetailRequestDTO request = new VehicleSpecificDetailRequestDTO();
        request.setDetail("Teto solar panorâmico");

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

        detalheCriado = objectMapper.readValue(content, VehicleSpecificDetailResponseDTO.class);

        assertNotNull(detalheCriado);
        assertNotNull(detalheCriado.getId());
        assertFalse(detalheCriado.getId().isEmpty());
        assertEquals("Teto solar panorâmico", detalheCriado.getDetail());
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("Deve listar detalhes com sucesso")
    void deveListarDetalhesComSucesso() {

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
    @DisplayName("Deve buscar o detalhe criado por ID com sucesso")
    void deveBuscarDetalhePorId() throws IOException {

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", detalheCriado.getId())
                        .when()
                        .get("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        VehicleSpecificDetailResponseDTO detalheEncontrado =
                objectMapper.readValue(content, VehicleSpecificDetailResponseDTO.class);

        assertNotNull(detalheEncontrado);
        assertEquals(detalheCriado.getId(), detalheEncontrado.getId());
        assertEquals("Teto solar panorâmico", detalheEncontrado.getDetail());
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("Deve atualizar parcialmente o detalhe com sucesso")
    void deveAtualizarDetalheParcialmente() throws IOException {

        VehicleSpecificDetailRequestDTO patchRequest = new VehicleSpecificDetailRequestDTO();
        patchRequest.setDetail("Teto solar panorâmico elétrico");

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", detalheCriado.getId())
                        .body(patchRequest)
                        .when()
                        .patch("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        VehicleSpecificDetailResponseDTO detalheAtualizado =
                objectMapper.readValue(content, VehicleSpecificDetailResponseDTO.class);

        assertNotNull(detalheAtualizado);
        assertEquals("Teto solar panorâmico elétrico", detalheAtualizado.getDetail());
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @Order(6)
    @DisplayName("Deve deletar o detalhe com sucesso")
    void deveDeletarDetalheComSucesso() {

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", detalheCriado.getId())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    // ─── findById após delete ─────────────────────────────────────────────────────

    @Test
    @Order(7)
    @DisplayName("Deve retornar 404 ao buscar detalhe deletado")
    void deveRetornar404AoBuscarDetalheDeletado() {

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", detalheCriado.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(404);
    }
}