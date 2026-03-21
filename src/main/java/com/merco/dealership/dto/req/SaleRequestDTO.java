package com.merco.dealership.dto.req;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class SaleRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "O ID do cliente é obrigatório")
    private String customerId;

    @NotBlank(message = "O ID do vendedor é obrigatório")
    private String sellerId;

    @NotBlank(message = "O ID do veículo no estoque é obrigatório")
    private String inventoryId;

    @NotNull(message = "A data da venda é obrigatória")
    private LocalDate saleDate;

    @NotBlank(message = "O método de pagamento é obrigatório")
    private String paymentMethod;

    private Integer installmentsNumber;

    @NotNull(message = "O valor bruto é obrigatório")
    @PositiveOrZero(message = "O valor não pode ser negativo")
    private Double grossAmount;

    private Double appliedDiscount;

    private Double netAmount;

    @NotBlank(message = "O recibo é obrigatório")
    private String receipt;

    public SaleRequestDTO() {
    }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }

    public String getInventoryId() { return inventoryId; }
    public void setInventoryId(String inventoryId) { this.inventoryId = inventoryId; }

    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Integer getInstallmentsNumber() { return installmentsNumber; }
    public void setInstallmentsNumber(Integer installmentsNumber) { this.installmentsNumber = installmentsNumber; }

    public Double getGrossAmount() { return grossAmount; }
    public void setGrossAmount(Double grossAmount) { this.grossAmount = grossAmount; }

    public Double getAppliedDiscount() { return appliedDiscount; }
    public void setAppliedDiscount(Double appliedDiscount) { this.appliedDiscount = appliedDiscount; }

    public Double getNetAmount() { return netAmount; }
    public void setNetAmount(Double netAmount) { this.netAmount = netAmount; }

    public String getReceipt() { return receipt; }
    public void setReceipt(String receipt) { this.receipt = receipt; }
}