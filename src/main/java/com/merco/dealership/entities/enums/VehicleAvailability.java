package com.merco.dealership.entities.enums;

public enum VehicleAvailability {
	AVAILABLE(1), SOLD(2), PENDING(3), RESERVED(4), IN_NEGOTIATION(5), OTHERS(6);

	private int code;

	private VehicleAvailability(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static VehicleAvailability valueOf(int code) {
		for (VehicleAvailability value : VehicleAvailability.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid VehicleAvailability code.");
	}
}
