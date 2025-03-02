package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.merco.dealership.entities.enums.ContractStatus;
import com.merco.dealership.entities.enums.PaymentTerms;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_contracts")
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

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
	private Integer paymentTerms;

	private Integer contractStatus;
	private String notes;

	@NotNull(message = "Required field")
	private String attachments;

	@NotNull(message = "Required field")
	@OneToOne
	@JoinColumn(name = "sale_id")
	private Sale sale;

	public Contract() {

	}

	public Contract(String id, @NotNull(message = "Required field") String contractNumber,
			@NotNull(message = "Required field") Sale sale, @NotNull(message = "Required field") String contractType,
			@NotNull(message = "Required field") LocalDate contractDate,
			@NotNull(message = "Required field") LocalDate deliveryDate,
			@NotNull(message = "Required field") Double totalAmount,
			@NotNull(message = "Required field") PaymentTerms paymentTerms, ContractStatus contractStatus, String notes,
			@NotNull(message = "Required field") String attachments) {
		super();
		this.id = id;
		this.contractNumber = contractNumber;
		this.sale = sale;
		this.contractType = contractType;
		this.contractDate = contractDate;
		this.deliveryDate = deliveryDate;
		this.totalAmount = totalAmount;
		setPaymentTerms(paymentTerms);
		setContractStatus(contractStatus);
		this.notes = notes;
		this.attachments = attachments;
	}

	public String getId() {
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
		return PaymentTerms.valueOf(paymentTerms);
	}

	public void setPaymentTerms(PaymentTerms paymentTerms) {
		if (paymentTerms != null) {
			this.paymentTerms = paymentTerms.getCode();
		}
	}

	public ContractStatus getContractStatus() {
		return ContractStatus.valueOf(contractStatus);
	}

	public void setContractStatus(ContractStatus contractStatus) {
		if (contractStatus != null) {
			this.contractStatus = contractStatus.getCode();
		}
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

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		return Objects.equals(id, other.id);
	}

}
