package com.merco.dealership.entities.pk;

import java.io.Serializable;
import java.util.Objects;

import com.merco.dealership.entities.Customer;
import com.merco.dealership.entities.CustomerAddress;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CustomerAddressMappingPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "customer_address_id")
	private CustomerAddress customerAddress;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerAddress getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(CustomerAddress customerAddress) {
		this.customerAddress = customerAddress;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, customerAddress);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerAddressMappingPK other = (CustomerAddressMappingPK) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(customerAddress, other.customerAddress);
	}

}
