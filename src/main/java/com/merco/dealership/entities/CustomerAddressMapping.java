package com.merco.dealership.entities;

import com.merco.dealership.entities.pk.CustomerAddressMappingPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_customer_addresses_mapping")
public class CustomerAddressMapping {
	@EmbeddedId
	private CustomerAddressMappingPK id = new CustomerAddressMappingPK();

	public CustomerAddressMapping() {

	}

	public CustomerAddressMapping(Customer customer, CustomerAddress customerAddress) {
		super();
		this.id.setCustomer(customer);
		this.id.setCustomerAddress(customerAddress);
	}

	public CustomerAddressMappingPK getId() {
		return id;
	}

	public Customer getCustomer() {
		return id.getCustomer();
	}

	public void setCustomer(Customer customer) {
		id.setCustomer(customer);
	}

	public CustomerAddress getCustomerAddress() {
		return id.getCustomerAddress();
	}

	public void setCustomerAddress(CustomerAddress customerAddress) {
		id.setCustomerAddress(customerAddress);
	}

}
