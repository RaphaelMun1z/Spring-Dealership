package com.merco.dealership.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.merco.dealership.entities.Sale;
import com.merco.dealership.mapper.Mapper;

public class SaleResponseDTO extends RepresentationModel<SaleResponseDTO> {
	private String id;
	private LocalDate saleDate;
	private Double grossAmount;
	private Double netAmount;
	private Double appliedDiscount;
	private String paymentMethod;
	private int installmentsNumber;
	private String receipt;
	private SellerResponseDTO seller;
	private CustomerResponseDTO customer;
	private InventoryItemResponseDTO inventoryItem;

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
		this.seller = Mapper.modelMapper(sale.getSeller(), SellerResponseDTO.class);
		this.customer = Mapper.modelMapper(sale.getCustomer(), CustomerResponseDTO.class);
		this.inventoryItem = Mapper.modelMapper(sale.getInventoryItem(), InventoryItemResponseDTO.class);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public SellerResponseDTO getSeller() {
		return seller;
	}

	public void setSeller(SellerResponseDTO seller) {
		this.seller = seller;
	}

	public CustomerResponseDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerResponseDTO customer) {
		this.customer = customer;
	}

	public InventoryItemResponseDTO getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(InventoryItemResponseDTO inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

}
