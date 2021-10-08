package com.seamhealthgroup.diagbot.utilities.validations;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BirthYearValidator implements ConstraintValidator<YearOfBirth, Integer> {

	String datePattern = "yyyy";

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {

		try {
			if (value != null) {
				return value < LocalDate.now().getYear();
			}
		} catch (Exception e) {
			log.error("DATE NOT PARSEABLE AS DATE FROMAT");
		}
		return false;
	}

}
