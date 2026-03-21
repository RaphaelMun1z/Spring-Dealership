package com.merco.dealership.services;

import com.merco.dealership.dto.res.DashboardStatsResponseDTO;
import com.merco.dealership.repositories.CustomerRepository;
import com.merco.dealership.repositories.InventoryItemRepository;
import com.merco.dealership.repositories.SaleRepository;
import com.merco.dealership.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final VehicleRepository vehicleRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;

    public DashboardService(
            VehicleRepository vehicleRepository,
            InventoryItemRepository inventoryItemRepository,
            SaleRepository saleRepository,
            CustomerRepository customerRepository
    ) {
        this.vehicleRepository       = vehicleRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.saleRepository          = saleRepository;
        this.customerRepository      = customerRepository;
    }

    public DashboardStatsResponseDTO getSummary() {
        return new DashboardStatsResponseDTO(
                (int) vehicleRepository.count(),
                (int) inventoryItemRepository.count(),
                (int) saleRepository.count(),
                (int) customerRepository.count()
        );
    }
}
