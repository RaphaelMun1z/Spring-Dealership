package com.merco.dealership.entities.enums;

public enum AppointmentStatus {
	PENDING(1), COMPLETED(2), CANCELLED(3);

	private int code;

	private AppointmentStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static AppointmentStatus valueOf(int code) {
		for (AppointmentStatus value : AppointmentStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid AppointmentStatus code.");
	}
}
