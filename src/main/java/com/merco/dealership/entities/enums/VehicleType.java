package com.merco.dealership.entities.enums;

public enum VehicleType {
	CAR(1), MOTORCYCLE(2), TRUCK(3), BUS(4), VAN(5), BOAT(6), MOTORHOME(7), BICYCLE(8), OTHERS(9);

	private int code;

	private VehicleType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static VehicleType valueOf(int code) {
		for (VehicleType value : VehicleType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid VehicleType code.");
	}
}
