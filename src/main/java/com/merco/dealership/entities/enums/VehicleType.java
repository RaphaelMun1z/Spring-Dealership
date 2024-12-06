package com.merco.dealership.entities.enums;

public enum VehicleType {
	CAR(1), MOTORCYCLE(2), VAN(3), BUS(4), TRUCK(5), BOAT(6), OTHER_VEHICLE_TYPE(7);

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
