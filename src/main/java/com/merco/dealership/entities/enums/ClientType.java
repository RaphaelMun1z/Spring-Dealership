package com.merco.dealership.entities.enums;

public enum ClientType {
	INDIVIDUAL(1), CORPORATE(2), OTHERS(3);

	private int code;

	private ClientType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ClientType valueOf(int code) {
		for (ClientType value : ClientType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid ClientType code.");
	}
}
