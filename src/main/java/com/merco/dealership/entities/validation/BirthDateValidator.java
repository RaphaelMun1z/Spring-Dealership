package com.merco.dealership.entities.validation;

import java.time.LocalDate;

import com.merco.dealership.entities.validation.constraints.BirthDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		LocalDate today = LocalDate.now();
		return value.isBefore(today);
	}

}
