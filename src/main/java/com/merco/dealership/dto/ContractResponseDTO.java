package com.merco.dealership.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.Contract;
import com.merco.dealership.entities.enums.ContractStatus;
import com.merco.dealership.entities.enums.PaymentTerms;
import com.merco.dealership.mapper.Mapper;

public class ContractResponseDTO  extends RepresentationModel<ContractResponseDTO> {
	private String id;
	private String contractNumber;
	private String contractType;
	private LocalDate contractDate;
	private LocalDate deliveryDate;
	private Double totalAmount;
	private PaymentTerms paymentTerms;
	private ContractStatus contractStatus;
	private String notes;
	private String attachments;
	private SaleResponseDTO sale;

	public ContractResponseDTO() {
	}

	public ContractResponseDTO(Contract contract) {
		this.id = contract.getId();
		this.contractNumber = contract.getContractNumber();
		this.contractType = contract.getContractType();
		this.contractDate = contract.getContractDate();
		this.deliveryDate = contract.getDeliveryDate();
		this.totalAmount = contract.getTotalAmount();
		this.paymentTerms = contract.getPaymentTerms();
		this.contractStatus = contract.getContractStatus();
		this.notes = contract.getNotes();
		this.attachments = contract.getAttachments();
		this.sale = Mapper.modelMapper(contract.getSale(), SaleResponseDTO.class);
	}

	public String getResourceId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public SaleResponseDTO getSale() {
		return sale;
	}

	public void setSale(SaleResponseDTO sale) {
		this.sale = sale;
	}

}
