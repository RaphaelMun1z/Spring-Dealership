package com.merco.dealership.dto;

import java.time.LocalDate;

import com.merco.dealership.entities.Contract;
import com.merco.dealership.entities.Customer;
import com.merco.dealership.entities.Sale;
import com.merco.dealership.entities.Seller;
import com.merco.dealership.entities.Vehicle;

public class SaleResponseDTO {
	private String id;
	private LocalDate saleDate;
	private Double grossAmount;
	private Double netAmount;
	private Double appliedDiscount;
	private String paymentMethod;
	private int installmentsNumber;
	private String receipt;
	private Seller seller;
	private Customer customer;
	private Contract contract;
	private Vehicle vehicle;

	public SaleResponseDTO() {
	}

	public SaleResponseDTO(Sale sale) {
		this.id = sale.getId();
		this.saleDate = sale.getSaleDate();
		this.grossAmount = sale.getGrossAmount();
		this.netAmount = sale.getNetAmount();
		this.appliedDiscount = sale.getAppliedDiscount();
		this.paymentMethod = sale.getPaymentMethod();
		this.installmentsNumber = sale.getInstallmentsNumber();
		this.receipt = sale.getReceipt();
		this.seller = sale.getSeller();
		this.customer = sale.getCustomer();
		this.contract = sale.getContract();
		this.vehicle = sale.getVehicle();
	}

	public String getId() {
		return id;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public Double getGrossAmount() {
		return grossAmount;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public Double getAppliedDiscount() {
		return appliedDiscount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public int getInstallmentsNumber() {
		return installmentsNumber;
	}

	public String getReceipt() {
		return receipt;
	}

	public Seller getSeller() {
		return seller;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Contract getContract() {
		return contract;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

}
