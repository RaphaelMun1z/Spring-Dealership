package com.merco.dealership.entities.enums;

public enum ContractStatus {
	SIGNED(1), CANCELLED(2), EXPIRED(3), PENDING(4);

	private int code;

	private ContractStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ContractStatus valueOf(int code) {
		for (ContractStatus value : ContractStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid ContractStatus code.");
	}
}
