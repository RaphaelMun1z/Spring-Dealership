package com.merco.dealership.entities.enums;

public enum VehicleStatus {
	NEW(1), USED(2), SEMINOVO(3), OTHERS(4);

	private int code;

	private VehicleStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static VehicleStatus valueOf(int code) {
		for (VehicleStatus value : VehicleStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid VehicleStatus code.");
	}
}
