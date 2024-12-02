package com.merco.dealership.entities.enums;

public enum VehicleCategory {
	SUV(1), SEDAN(2), HATCHBACK(3), SPORTS(4), UTILITARIAN(5), COUPE(6), CONVERTIBLE(7), WAGON(8), PICKUP(9), VAN(10),
	MOTORHOME(11), ELECTRIC(12), HYBRID(13), OTHERS(14);

	private int code;

	private VehicleCategory(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static VehicleCategory valueOf(int code) {
		for (VehicleCategory value : VehicleCategory.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid VehicleCategory code.");
	}
}
