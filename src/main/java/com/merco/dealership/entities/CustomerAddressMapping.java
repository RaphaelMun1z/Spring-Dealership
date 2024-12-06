package com.merco.dealership.entities;

import java.io.Serializable;
import java.util.Objects;

import com.merco.dealership.entities.pk.CustomerAddressMappingPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_customer_addresses_map")
public class CustomerAddressMapping implements Serializable {
	private static final long serialVersionUID = 1L;

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
		CustomerAddressMapping other = (CustomerAddressMapping) obj;
		return Objects.equals(id, other.id);
	}

}
