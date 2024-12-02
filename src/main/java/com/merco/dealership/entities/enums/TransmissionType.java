package com.merco.dealership.entities.enums;

public enum TransmissionType {
	MANUAL(1), AUTOMATIC(2), SEMI_AUTOMATIC(3), CVT(4), DUAL_CLUTCH(5), OTHERS(6);

	private int code;

	private TransmissionType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static TransmissionType valueOf(int code) {
		for (TransmissionType value : TransmissionType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid TransmissionType code.");
	}
}
