package com.merco.dealership.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.merco.dealership.dto.req.BranchAddressRequestDTO;
import com.merco.dealership.dto.req.BranchRequestDTO;
import com.merco.dealership.dto.res.BranchAddressResponseDTO;
import com.merco.dealership.dto.res.BranchResponseDTO;
import com.merco.dealership.dto.req.LoginRequestDTO;
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
@DisplayName("Testes de Integração - Controller de Filiais")
class BranchIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private RequestSpecification specification;
    private RequestSpecification specificationAddress;
    private ObjectMapper objectMapper;

    private BranchResponseDTO branchCriada;
    private String branchAddressId;

    @BeforeAll
    @DisplayName("Configuração inicial do ambiente de testes")
    void configurarAmbiente() {

        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        // REMOVIDO: RestAssured.basePath = "/api";

        LoginRequestDTO usuario = new LoginRequestDTO("admin@auto.com", "Auto123@");

        String accessToken =
                given()
                        .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(usuario)
                        .when()
                        .post("/auth/login") // Sem /api
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(LoginResponseDTO.class)
                        .getToken()
                        .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/branches") // Sem /api
                .setPort(port)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        specificationAddress = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/branches-address") // Sem /api
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

    // ─── create address (pré-requisito para criar branch) ─────────────────────────

    @Test
    @Order(2)
    @DisplayName("Deve criar um endereço de filial com sucesso")
    void deveCriarEnderecoFilialComSucesso() throws IOException {

        BranchAddressRequestDTO enderecoRequest = new BranchAddressRequestDTO();
        enderecoRequest.setStreet("Rua Teste");
        enderecoRequest.setNumber(123);
        enderecoRequest.setDistrict("Centro");
        enderecoRequest.setCity("São Paulo");
        enderecoRequest.setState("SP");
        enderecoRequest.setCountry("Brasil");
        enderecoRequest.setCep("12345-678");
        enderecoRequest.setComplement("Sala 1");

        var content =
                given()
                        .spec(specificationAddress)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(enderecoRequest)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .extract()
                        .asString();

        BranchAddressResponseDTO enderecoCriado =
                objectMapper.readValue(content, BranchAddressResponseDTO.class);

        assertNotNull(enderecoCriado);
        assertNotNull(enderecoCriado.getId());
        assertFalse(enderecoCriado.getId().isEmpty());

        assertEquals("Rua Teste", enderecoCriado.getStreet());
        assertEquals(123, enderecoCriado.getNumber());
        assertEquals("Centro", enderecoCriado.getDistrict());
        assertEquals("São Paulo", enderecoCriado.getCity());
        assertEquals("SP", enderecoCriado.getState());
        assertEquals("Brasil", enderecoCriado.getCountry());
        assertEquals("12345-678", enderecoCriado.getCep());
        assertEquals("Sala 1", enderecoCriado.getComplement());

        branchAddressId = enderecoCriado.getId();
    }

    // ─── create branch ────────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("Deve criar uma filial com sucesso quando os dados forem válidos")
    void deveCriarFilialComSucesso() throws IOException {

        BranchRequestDTO branchRequest = new BranchRequestDTO();
        branchRequest.setName("Branch Test");
        branchRequest.setPhoneNumber("(11) 91234-5678");
        branchRequest.setEmail("branch@test.com");
        branchRequest.setManagerName("Manager Test");
        branchRequest.setOpeningHours("08:00 - 18:00");
        branchRequest.setBranchType("HEADQUARTERS");
        branchRequest.setStatus("ACTIVE");
        branchRequest.setCreatedAt(LocalDate.now());
        branchRequest.setUpdatedAt(LocalDate.now());
        branchRequest.setBranchAddressId(branchAddressId);

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(branchRequest)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .extract()
                        .asString();

        branchCriada = objectMapper.readValue(content, BranchResponseDTO.class);

        assertNotNull(branchCriada);
        assertNotNull(branchCriada.getId());
        assertFalse(branchCriada.getId().isEmpty());

        assertEquals("Branch Test", branchCriada.getName());
        assertEquals("(11) 91234-5678", branchCriada.getPhoneNumber());
        assertEquals("branch@test.com", branchCriada.getEmail());
        assertEquals("Manager Test", branchCriada.getManagerName());
        assertEquals("08:00 - 18:00", branchCriada.getOpeningHours());
        assertEquals("HEADQUARTERS", branchCriada.getBranchType());
        assertEquals("ACTIVE", branchCriada.getStatus());

        assertNotNull(branchCriada.getAddress());
        assertEquals("Rua Teste", branchCriada.getAddress().getStreet());
        assertEquals(123, branchCriada.getAddress().getNumber());
        assertEquals("Centro", branchCriada.getAddress().getDistrict());
        assertEquals("São Paulo", branchCriada.getAddress().getCity());
        assertEquals("SP", branchCriada.getAddress().getState());
        assertEquals("Brasil", branchCriada.getAddress().getCountry());
        assertEquals("12345-678", branchCriada.getAddress().getCep());
        assertEquals("Sala 1", branchCriada.getAddress().getComplement());
    }

    // ─── findById ─────────────────────────────────────────────────────────────────

    @Test
    @Order(4)
    @DisplayName("Deve buscar a filial criada por ID com sucesso")
    void deveBuscarFilialPorId() throws IOException {

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", branchCriada.getId())
                        .when()
                        .get("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        BranchResponseDTO filialEncontrada =
                objectMapper.readValue(content, BranchResponseDTO.class);

        assertNotNull(filialEncontrada);
        assertEquals(branchCriada.getId(), filialEncontrada.getId());
        assertEquals("Branch Test", filialEncontrada.getName());
        assertEquals("branch@test.com", filialEncontrada.getEmail());
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("Deve listar filiais com sucesso")
    void deveListarFiliaisComSucesso() {

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @Order(6)
    @DisplayName("Deve atualizar parcialmente a filial com sucesso")
    void deveAtualizarFilialParcialmente() throws IOException {

        BranchRequestDTO patchRequest = new BranchRequestDTO();
        patchRequest.setName("Branch Test Atualizada");
        patchRequest.setStatus("INACTIVE");

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", branchCriada.getId())
                        .body(patchRequest)
                        .when()
                        .patch("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        BranchResponseDTO filialAtualizada =
                objectMapper.readValue(content, BranchResponseDTO.class);

        assertNotNull(filialAtualizada);
        assertEquals("Branch Test Atualizada", filialAtualizada.getName());
        assertEquals("INACTIVE", filialAtualizada.getStatus());
        assertEquals("branch@test.com", filialAtualizada.getEmail());
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @Order(7)
    @DisplayName("Deve deletar a filial com sucesso")
    void deveDeletarFilialComSucesso() {

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", branchCriada.getId())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    // ─── findById após delete ─────────────────────────────────────────────────────

    @Test
    @Order(8)
    @DisplayName("Deve retornar 404 ao buscar filial deletada")
    void deveRetornar404AoBuscarFilialDeletada() {

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", branchCriada.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(404);
    }
}