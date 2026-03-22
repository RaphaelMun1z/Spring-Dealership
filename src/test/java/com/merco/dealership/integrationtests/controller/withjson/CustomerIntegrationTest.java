package com.merco.dealership.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.merco.dealership.dto.req.CustomerRequestDTO;
import com.merco.dealership.dto.req.LoginRequestDTO;
import com.merco.dealership.dto.res.CustomerResponseDTO;
import com.merco.dealership.dto.res.LoginResponseDTO;
import com.merco.dealership.entities.enums.ClientType;

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
@DisplayName("Testes de Integração - Controller de Clientes")
class CustomerIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private RequestSpecification specification;
    private ObjectMapper objectMapper;
    private CustomerResponseDTO customerCriado;

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
                .setBasePath("/customers")
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
    @DisplayName("Deve criar um cliente com sucesso")
    void deveCriarCustomerComSucesso() throws IOException {

        CustomerRequestDTO request = new CustomerRequestDTO();
        request.setName("Customer Test");
        request.setCpf("123.456.789-00");
        request.setEmail("customer.test@email.com");
        request.setPhone("(11) 91234-5678");
        request.setBirthDate(LocalDate.of(1990, 5, 20));
        request.setRegistrationDate(LocalDate.now());
        request.setClientType(ClientType.INDIVIDUAL);
        request.setValidCnh(true);

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

        customerCriado = objectMapper.readValue(content, CustomerResponseDTO.class);

        assertNotNull(customerCriado);
        assertNotNull(customerCriado.getId());
        assertFalse(customerCriado.getId().isEmpty());
        assertEquals("Customer Test", customerCriado.getName());
        assertEquals("customer.test@email.com", customerCriado.getEmail());
        assertEquals("123.456.789-00", customerCriado.getCpf());
        assertTrue(customerCriado.getValidCnh());
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("Deve listar clientes com sucesso")
    void deveListarCustomersComSucesso() {

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
    @DisplayName("Deve buscar o cliente criado por ID com sucesso")
    void deveBuscarCustomerPorId() throws IOException {

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", customerCriado.getId())
                        .when()
                        .get("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        CustomerResponseDTO customerEncontrado = objectMapper.readValue(content, CustomerResponseDTO.class);

        assertNotNull(customerEncontrado);
        assertEquals(customerCriado.getId(), customerEncontrado.getId());
        assertEquals("Customer Test", customerEncontrado.getName());
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @DisplayName("Deve atualizar parcialmente o cliente com sucesso")
    void deveAtualizarCustomerParcialmente() throws IOException {

        CustomerRequestDTO patchRequest = new CustomerRequestDTO();
        patchRequest.setName("Customer Test Atualizado");
        patchRequest.setPhone("(11) 99999-9999");

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", customerCriado.getId())
                        .body(patchRequest)
                        .when()
                        .patch("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        CustomerResponseDTO customerAtualizado = objectMapper.readValue(content, CustomerResponseDTO.class);

        assertNotNull(customerAtualizado);
        assertEquals("Customer Test Atualizado", customerAtualizado.getName());
        assertEquals("customer.test@email.com", customerAtualizado.getEmail());
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @Order(6)
    @DisplayName("Deve deletar o cliente com sucesso")
    void deveDeletarCustomerComSucesso() {

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", customerCriado.getId())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    // ─── findById após delete ─────────────────────────────────────────────────────

    @Test
    @Order(7)
    @DisplayName("Deve retornar 404 ao buscar cliente deletado")
    void deveRetornar404AoBuscarCustomerDeletado() {

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", customerCriado.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(404);
    }
}