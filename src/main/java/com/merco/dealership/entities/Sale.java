package com.merco.dealership.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_sales")
public class Sale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	private String vehicleID;

	@NotNull(message = "Required field")
	private String clientID;

	@NotNull(message = "Required field")
	private String sellerID;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@NotNull(message = "Required field")
	private LocalDate saleDate;

	@NotNull(message = "Required field")
	private Double grossAmount;

	private Double netAmount;

	private Double appliedDiscount;

	@NotNull(message = "Required field")
	private String paymentMethod;

	private int installmentsNumber;

	@NotNull(message = "Required field")
	private String receipt;

	public Sale() {
	}

	public Sale(String id, @NotNull(message = "Required field") String vehicleID,
			@NotNull(message = "Required field") String clientID, @NotNull(message = "Required field") String sellerID,
			@NotNull(message = "Required field") LocalDate saleDate,
			@NotNull(message = "Required field") Double grossAmount, Double netAmount, Double appliedDiscount,
			@NotNull(message = "Required field") String paymentMethod, int installmentsNumber,
			@NotNull(message = "Required field") String receipt) {
		super();
		this.id = id;
		this.vehicleID = vehicleID;
		this.clientID = clientID;
		this.sellerID = sellerID;
		this.saleDate = saleDate;
		this.grossAmount = grossAmount;
		this.netAmount = netAmount;
		this.appliedDiscount = appliedDiscount;
		this.paymentMethod = paymentMethod;
		this.installmentsNumber = installmentsNumber;
		this.receipt = receipt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getSellerID() {
		return sellerID;
	}

	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public Double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(Double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public Double getAppliedDiscount() {
		return appliedDiscount;
	}

	public void setAppliedDiscount(Double appliedDiscount) {
		this.appliedDiscount = appliedDiscount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getInstallmentsNumber() {
		return installmentsNumber;
	}

	public void setInstallmentsNumber(int installmentsNumber) {
		this.installmentsNumber = installmentsNumber;
	}

	public String getInvoice() {
		return receipt;
	}

	public void setInvoice(String invoice) {
		this.receipt = invoice;
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
		Sale other = (Sale) obj;
		return Objects.equals(id, other.id);
	}
}
