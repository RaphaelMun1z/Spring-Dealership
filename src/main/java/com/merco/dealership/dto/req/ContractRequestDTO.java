package com.merco.dealership.dto.req;

import java.time.LocalDate;

import com.merco.dealership.entities.enums.ContractStatus;
import com.merco.dealership.entities.enums.PaymentTerms;

import jakarta.validation.constraints.NotNull;

public class ContractRequestDTO {

    @NotNull(message = "Required field")
    private String contractNumber;

    @NotNull(message = "Required field")
    private String contractType;

    @NotNull(message = "Required field")
    private LocalDate contractDate;

    @NotNull(message = "Required field")
    private LocalDate deliveryDate;

    @NotNull(message = "Required field")
    private Double totalAmount;

    @NotNull(message = "Required field")
    private PaymentTerms paymentTerms;

    private ContractStatus contractStatus;

    private String notes;

    @NotNull(message = "Required field")
    private String attachments;

    @NotNull(message = "Required field")
    private String saleId;

    public ContractRequestDTO() {
    }

    public ContractRequestDTO(String contractNumber, String contractType, LocalDate contractDate,
                              LocalDate deliveryDate, Double totalAmount, PaymentTerms paymentTerms, ContractStatus contractStatus,
                              String notes, String attachments, String saleId) {
        this.contractNumber = contractNumber;
        this.contractType = contractType;
        this.contractDate = contractDate;
        this.deliveryDate = deliveryDate;
        this.totalAmount = totalAmount;
        this.paymentTerms = paymentTerms;
        this.contractStatus = contractStatus;
        this.notes = notes;
        this.attachments = attachments;
        this.saleId = saleId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentTerms getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(PaymentTerms paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }
}