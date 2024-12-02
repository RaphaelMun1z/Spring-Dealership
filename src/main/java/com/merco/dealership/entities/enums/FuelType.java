package com.merco.dealership.entities.enums;

public enum FuelType {
	GASOLINE(1), DIESEL(2), ELECTRIC(3), HYBRID(4), ETHANOL(5), LPG(6), CNG(7), PROPANE(8), OTHERS(9);

	private int code;

	private FuelType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static FuelType valueOf(int code) {
		for (FuelType value : FuelType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid FuelType code.");
	}
}
