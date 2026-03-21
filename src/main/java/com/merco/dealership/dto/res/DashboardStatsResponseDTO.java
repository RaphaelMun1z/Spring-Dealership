package com.merco.dealership.dto.res;

public class DashboardStatsResponseDTO {

    private Integer totalVehicles;
    private Integer totalInventory;
    private Integer totalSales;
    private Integer totalCustomers;

    public DashboardStatsResponseDTO() {}

    public DashboardStatsResponseDTO(
            Integer totalVehicles,
            Integer totalInventory,
            Integer totalSales,
            Integer totalCustomers
    ) {
        this.totalVehicles  = totalVehicles;
        this.totalInventory = totalInventory;
        this.totalSales     = totalSales;
        this.totalCustomers = totalCustomers;
    }

    public Integer getTotalVehicles()  { return totalVehicles;  }
    public Integer getTotalInventory() { return totalInventory; }
    public Integer getTotalSales()     { return totalSales;     }
    public Integer getTotalCustomers() { return totalCustomers; }

    public void setTotalVehicles(Integer totalVehicles)   { this.totalVehicles  = totalVehicles;  }
    public void setTotalInventory(Integer totalInventory) { this.totalInventory = totalInventory; }
    public void setTotalSales(Integer totalSales)         { this.totalSales     = totalSales;     }
    public void setTotalCustomers(Integer totalCustomers) { this.totalCustomers = totalCustomers; }
}
