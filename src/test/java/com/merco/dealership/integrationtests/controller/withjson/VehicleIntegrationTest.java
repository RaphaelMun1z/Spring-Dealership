package com.merco.dealership.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.merco.dealership.dto.req.BranchAddressRequestDTO;
import com.merco.dealership.dto.req.BranchRequestDTO;
import com.merco.dealership.dto.req.LoginRequestDTO;
import com.merco.dealership.dto.req.VehicleRequestDTO;
import com.merco.dealership.dto.res.BranchAddressResponseDTO;
import com.merco.dealership.dto.res.BranchResponseDTO;
import com.merco.dealership.dto.res.LoginResponseDTO;
import com.merco.dealership.dto.res.VehicleResponseDTO;

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
@DisplayName("Testes de Integração - Controller de Veículos")
class VehicleIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private RequestSpecification specification;
    private RequestSpecification specificationBranch;
    private RequestSpecification specificationBranchAddress;
    private ObjectMapper objectMapper;

    private VehicleResponseDTO vehicleCriado;
    private String branchId;

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
                .setBasePath("/vehicles")
                .setPort(port)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        specificationBranchAddress = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/branches-address")
                .setPort(port)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        specificationBranch = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/branches")
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

    // ─── pré-requisito: criar branch ──────────────────────────────────────────────

    @Test
    @Order(2)
    @DisplayName("Deve criar branch pré-requisito para o veículo")
    void deveCriarBranchPreRequisito() throws IOException {

        // Passo 1: criar endereço
        BranchAddressRequestDTO enderecoRequest = new BranchAddressRequestDTO();
        enderecoRequest.setStreet("Rua Veiculos");
        enderecoRequest.setNumber(999);
        enderecoRequest.setDistrict("Centro");
        enderecoRequest.setCity("Sao Paulo");
        enderecoRequest.setState("Sao Paulo");
        enderecoRequest.setCountry("Brasil");
        enderecoRequest.setCep("99999-000");

        String enderecoContent =
                given()
                        .spec(specificationBranchAddress)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(enderecoRequest)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .extract()
                        .asString();

        BranchAddressResponseDTO enderecoCriado =
                objectMapper.readValue(enderecoContent, BranchAddressResponseDTO.class);

        assertNotNull(enderecoCriado.getId());

        // Passo 2: criar branch com o endereço
        BranchRequestDTO branchRequest = new BranchRequestDTO();
        branchRequest.setName("Branch Veiculos");
        branchRequest.setPhoneNumber("(11) 91234-5678");
        branchRequest.setEmail("branch.veiculos@test.com");
        branchRequest.setManagerName("Manager Veiculos");
        branchRequest.setOpeningHours("08:00 - 18:00");
        branchRequest.setBranchType("DEALERSHIP");
        branchRequest.setStatus("ACTIVE");
        branchRequest.setCreatedAt(LocalDate.now());
        branchRequest.setUpdatedAt(LocalDate.now());
        branchRequest.setBranchAddressId(enderecoCriado.getId());

        String branchContent =
                given()
                        .spec(specificationBranch)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .body(branchRequest)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .extract()
                        .asString();

        BranchResponseDTO branchCriada =
                objectMapper.readValue(branchContent, BranchResponseDTO.class);

        assertNotNull(branchCriada.getId());
        assertFalse(branchCriada.getId().isEmpty());

        branchId = branchCriada.getId();
    }

    // ─── create ───────────────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("Deve criar um veículo (CAR) com sucesso")
    void deveCriarVehicleComSucesso() throws IOException {

        assertNotNull(branchId, "branchId não pode ser nulo — verifique se o @Order(2) passou");

        VehicleRequestDTO request = new VehicleRequestDTO();
        request.setType("CAR");
        request.setBrand("Toyota");
        request.setModel("Corolla");
        request.setColor("Prata");
        request.setMileage(0.0);
        request.setWeight(1400.0);
        request.setNumberOfCylinders(4);
        request.setInfotainmentSystem("Toyota Connect");
        request.setFuelTankCapacity(50.0);
        request.setEnginePower(170.0);
        request.setPassengerCapacity(5);
        request.setSalePrice(120000.0);
        request.setDescription("Sedan executivo");
        request.setManufactureYear(LocalDate.of(2024, 1, 1));
        request.setCategory("SEDAN");
        request.setFuelType("GASOLINE");
        request.setStatus("NEW");
        request.setAvailability("AVAILABLE");
        request.setBranchId(branchId);
        request.setTransmissionType("AUTOMATIC");
        request.setDoors(4);

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

        vehicleCriado = objectMapper.readValue(content, VehicleResponseDTO.class);

        assertNotNull(vehicleCriado);
        assertNotNull(vehicleCriado.getId());
        assertEquals("Toyota", vehicleCriado.getBrand());
        assertEquals("Corolla", vehicleCriado.getModel());
        assertEquals("Prata", vehicleCriado.getColor());
        assertEquals(120000.0, vehicleCriado.getSalePrice());
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @Order(4)
    @DisplayName("Deve listar veículos com sucesso")
    void deveListarVehiclesComSucesso() {

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
    @Order(5)
    @DisplayName("Deve buscar o veículo criado por ID com sucesso")
    void deveBuscarVehiclePorId() throws IOException {

        assertNotNull(vehicleCriado, "vehicleCriado não pode ser nulo — verifique se o @Order(3) passou");

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", vehicleCriado.getId())
                        .when()
                        .get("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        VehicleResponseDTO vehicleEncontrado = objectMapper.readValue(content, VehicleResponseDTO.class);

        assertNotNull(vehicleEncontrado);
        assertEquals(vehicleCriado.getId(), vehicleEncontrado.getId());
        assertEquals("Toyota", vehicleEncontrado.getBrand());
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @Order(6)
    @DisplayName("Deve atualizar parcialmente o veículo com sucesso")
    void deveAtualizarVehicleParcialmente() throws IOException {

        assertNotNull(vehicleCriado, "vehicleCriado não pode ser nulo — verifique se o @Order(3) passou");

        VehicleRequestDTO patchRequest = new VehicleRequestDTO();
        patchRequest.setColor("Preto");
        patchRequest.setSalePrice(115000.0);

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .pathParam("id", vehicleCriado.getId())
                        .body(patchRequest)
                        .when()
                        .patch("/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        VehicleResponseDTO vehicleAtualizado = objectMapper.readValue(content, VehicleResponseDTO.class);

        assertNotNull(vehicleAtualizado);
        assertEquals("Preto", vehicleAtualizado.getColor());
        assertEquals(115000.0, vehicleAtualizado.getSalePrice());
        assertEquals("Toyota", vehicleAtualizado.getBrand());
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @Order(7)
    @DisplayName("Deve deletar o veículo com sucesso")
    void deveDeletarVehicleComSucesso() {

        assertNotNull(vehicleCriado, "vehicleCriado não pode ser nulo — verifique se o @Order(3) passou");

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", vehicleCriado.getId())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    // ─── findById após delete ─────────────────────────────────────────────────────

    @Test
    @Order(8)
    @DisplayName("Deve retornar 404 ao buscar veículo deletado")
    void deveRetornar404AoBuscarVehicleDeletado() {

        assertNotNull(vehicleCriado, "vehicleCriado não pode ser nulo — verifique se o @Order(3) passou");

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", vehicleCriado.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(404);
    }
}