package com.merco.dealership.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;

import com.merco.dealership.dto.req.SaleRequestDTO;
import com.merco.dealership.dto.res.SaleResponseDTO;
import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.entities.customerResources.Customer;
import com.merco.dealership.entities.users.Seller;
import com.merco.dealership.repositories.CustomerRepository;
import com.merco.dealership.repositories.InventoryItemRepository;
import com.merco.dealership.repositories.SaleRepository;
import com.merco.dealership.repositories.SellerRepository;
import com.merco.dealership.services.SaleService;
import com.merco.dealership.services.exceptions.DataViolationException;
import com.merco.dealership.services.exceptions.ResourceNotFoundException;
import com.merco.dealership.unittests.mapper.mocks.MockInventoryItem;
import com.merco.dealership.unittests.mapper.mocks.MockSale;

@ExtendWith(MockitoExtension.class)
@Tag("unidade")
@DisplayName("Testes de Unidade - Service de Vendas")
class SaleServiceTest {

    private MockSale input;
    private MockInventoryItem inputInventory;

    @InjectMocks
    private SaleService service;

    @Mock
    private SaleRepository repository;

    @Mock
    private InventoryItemRepository inventoryRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private PagedResourcesAssembler<SaleResponseDTO> assembler;

    @BeforeEach
    void setUpMocks() {
        input = new MockSale();
        inputInventory = new MockInventoryItem();
    }

    // ─── findAll ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar uma página de vendas com sucesso")
    void testFindAll() {
        // Arrange
        Page<Sale> page = new PageImpl<>(input.mockEntityList());

        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(assembler.toModel(any(Page.class), any(Link.class))).thenReturn(null);

        // Act
        service.findAll(PageRequest.of(0, 12));

        // Assert
        verify(repository, times(1)).findAll(any(Pageable.class));
        verify(assembler, times(1)).toModel(any(Page.class), any(Link.class));
    }

    // ─── findById ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve encontrar uma venda por ID com sucesso")
    void testFindById() {
        // Arrange
        Sale entity = input.mockEntity("id1");
        when(repository.findById("id1")).thenReturn(Optional.of(entity));

        // Act
        var result = service.findById("id1");

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals(50000.0, result.getGrossAmount());
        assertEquals("CREDIT_CARD", result.getPaymentMethod());
        verify(repository, times(1)).findById("id1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar venda com ID inexistente")
    void testFindByIdNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.findById("invalidId"));
    }

    // ─── create ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve criar uma venda com sucesso")
    void testCreate() {
        // Arrange
        SaleRequestDTO dto = input.mockRequestDTO("customerId1", "sellerId1", "inventoryId1");
        Sale persisted = input.mockEntity("id1");

        InventoryItem inventoryItem = inputInventory.mockEntity("inventoryId1");

        when(inventoryRepository.findById("inventoryId1")).thenReturn(Optional.of(inventoryItem));
        when(customerRepository.findById("customerId1")).thenReturn(Optional.of(new Customer()));
        when(sellerRepository.findById("sellerId1")).thenReturn(Optional.of(new Seller()));
        when(inventoryRepository.save(any(InventoryItem.class))).thenReturn(inventoryItem);
        when(repository.save(any(Sale.class))).thenReturn(persisted);

        // Act
        var result = service.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("id1", result.getId());
        assertEquals(50000.0, result.getGrossAmount());
        verify(repository, times(1)).save(any(Sale.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar venda com inventory item inexistente")
    void testCreateInventoryNotFound() {
        // Arrange
        SaleRequestDTO dto = input.mockRequestDTO("customerId1", "sellerId1", "invalidInventory");
        when(inventoryRepository.findById("invalidInventory")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar venda com veículo já vendido")
    void testCreateVehicleAlreadySold() {
        // Arrange
        SaleRequestDTO dto = input.mockRequestDTO("customerId1", "sellerId1", "inventoryId1");

        InventoryItem soldItem = inputInventory.mockEntity("inventoryId1");
        soldItem.setStockExitDate(LocalDate.now().minusDays(1));

        when(inventoryRepository.findById("inventoryId1")).thenReturn(Optional.of(soldItem));

        // Act & Assert
        assertThrows(DataViolationException.class, () -> service.create(dto));
    }

    // ─── delete ───────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar uma venda e restaurar o stock exit date do inventory item")
    void testDelete() {
        // Arrange
        Sale sale = input.mockEntity("id1");
        InventoryItem inventoryItem = inputInventory.mockEntity("inventoryId1");
        inventoryItem.setStockExitDate(LocalDate.now());
        sale.setInventoryItem(inventoryItem);

        when(repository.findById("id1")).thenReturn(Optional.of(sale));
        when(inventoryRepository.findById("inventoryId1")).thenReturn(Optional.of(inventoryItem));
        when(inventoryRepository.save(any(InventoryItem.class))).thenReturn(inventoryItem);

        // Act
        service.delete("id1");

        // Assert
        verify(repository, times(1)).delete(sale);
        verify(inventoryRepository, times(1)).save(any(InventoryItem.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar venda com ID inexistente")
    void testDeleteNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete("invalidId"));
    }

    // ─── patch ────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar uma venda parcialmente com sucesso")
    void testPatch() {
        // Arrange
        Sale existing = input.mockEntity("id1");
        Sale updated = input.mockEntity("id1");
        updated.setGrossAmount(60000.0);
        updated.setPaymentMethod("PIX");

        SaleRequestDTO patchDTO = new SaleRequestDTO();
        patchDTO.setGrossAmount(60000.0);
        patchDTO.setPaymentMethod("PIX");

        when(repository.findById("id1")).thenReturn(Optional.of(existing));
        when(repository.save(any(Sale.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals(60000.0, result.getGrossAmount());
        assertEquals("PIX", result.getPaymentMethod());
        verify(repository, times(1)).save(any(Sale.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar venda com ID inexistente")
    void testPatchNotFound() {
        // Arrange
        when(repository.findById("invalidId")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> service.patch("invalidId", new SaleRequestDTO()));
    }

    @Test
    @DisplayName("Deve atualizar o customer da venda com sucesso")
    void testPatchCustomer() {
        // Arrange
        Sale existing = input.mockEntity("id1");
        Sale updated = input.mockEntity("id1");
        Customer newCustomer = new Customer();
        updated.setCustomer(newCustomer);

        SaleRequestDTO patchDTO = new SaleRequestDTO();
        patchDTO.setCustomerId("customerId2");

        when(repository.findById("id1")).thenReturn(Optional.of(existing));
        when(customerRepository.findById("customerId2")).thenReturn(Optional.of(newCustomer));
        when(repository.save(any(Sale.class))).thenReturn(updated);

        // Act
        var result = service.patch("id1", patchDTO);

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).findById("customerId2");
        verify(repository, times(1)).save(any(Sale.class));
    }
}