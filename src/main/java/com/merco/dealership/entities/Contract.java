package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@OneToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;

	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;

	@NotNull(message = "Required field")
	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@NotNull(message = "Required field")
	private String contractType;

	@NotNull(message = "Required field")
	private LocalDate contractDate;

	@NotNull(message = "Required field")
	private LocalDate deliveryDate;

	@NotNull(message = "Required field")
	private Double totalAmount;

	@NotNull(message = "Required field")
	private String paymentTerms;

	private String contractStatus;
	private String notes;

	@NotNull(message = "Required field")
	private String attachments;

	public Contract() {

	}

	public Contract(String id, @NotNull(message = "Required field") String contractNumber,
			@NotNull(message = "Required field") Vehicle vehicle, @NotNull(message = "Required field") Seller seller,
			@NotNull(message = "Required field") Customer customer,
			@NotNull(message = "Required field") String contractType,
			@NotNull(message = "Required field") LocalDate contractDate,
			@NotNull(message = "Required field") LocalDate deliveryDate,
			@NotNull(message = "Required field") Double totalAmount,
			@NotNull(message = "Required field") String paymentTerms, String contractStatus, String notes,
			@NotNull(message = "Required field") String attachments) {
		super();
		this.id = id;
		this.contractNumber = contractNumber;
		this.vehicle = vehicle;
		this.seller = seller;
		this.customer = customer;
		this.contractType = contractType;
		this.contractDate = contractDate;
		this.deliveryDate = deliveryDate;
		this.totalAmount = totalAmount;
		this.paymentTerms = paymentTerms;
		this.contractStatus = contractStatus;
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

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
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
