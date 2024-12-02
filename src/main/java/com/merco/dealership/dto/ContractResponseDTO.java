package com.merco.dealership.dto;

import java.time.LocalDate;

import com.merco.dealership.entities.Contract;
import com.merco.dealership.entities.Customer;
import com.merco.dealership.entities.InventoryItem;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.entities.enums.ContractStatus;
import com.merco.dealership.entities.enums.PaymentTerms;

public class ContractResponseDTO {
	private String id;
	private String contractNumber;
	private Customer customer;
	private String contractType;
	private LocalDate contractDate;
	private LocalDate deliveryDate;
	private Double totalAmount;
	private PaymentTerms paymentTerms;
	private ContractStatus contractStatus;
	private String notes;
	private String attachments;
	private InventoryItem inventoryItem;
	private Sale sale;

	public ContractResponseDTO() {
	}

	public ContractResponseDTO(Contract Contract) {
		this.id = Contract.getId();
		this.contractNumber = Contract.getContractNumber();
		this.customer = Contract.getCustomer();
		this.contractType = Contract.getContractType();
		this.contractDate = Contract.getContractDate();
		this.deliveryDate = Contract.getDeliveryDate();
		this.totalAmount = Contract.getTotalAmount();
		this.paymentTerms = Contract.getPaymentTerms();
		this.contractStatus = Contract.getContractStatus();
		this.notes = Contract.getNotes();
		this.attachments = Contract.getAttachments();
		this.inventoryItem = Contract.getInventoryItem();
		this.sale = Contract.getSale();
	}

	public String getId() {
		return id;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getContractType() {
		return contractType;
	}

	public LocalDate getContractDate() {
		return contractDate;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public PaymentTerms getPaymentTerms() {
		return paymentTerms;
	}

	public ContractStatus getContractStatus() {
		return contractStatus;
	}

	public String getNotes() {
		return notes;
	}

	public String getAttachments() {
		return attachments;
	}

	public InventoryItem getInventoryItem() {
		return inventoryItem;
	}

	public Sale getSale() {
		return sale;
	}

}
