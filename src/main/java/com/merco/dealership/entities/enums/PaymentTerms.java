package com.merco.dealership.entities.enums;

public enum PaymentTerms {
	CASH(1), BANK_TRANSFER(2), CREDIT_CARD(3), DEBIT_CARD(4), PIX(5), CHECK(6), INSTALLMENTS_WITHOUT_INTEREST(7),
	INSTALLMENTS_WITH_INTEREST(8), FINANCED_BY_BANK(9), FINANCED_BY_DEALERSHIP(10), TRADE_IN(11),
	PARTIAL_CASH_PARTIAL_FINANCING(12), OTHER(13);

	private int code;

	private PaymentTerms(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static PaymentTerms valueOf(int code) {
		for (PaymentTerms value : PaymentTerms.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid PaymentTerms code.");
	}
}
