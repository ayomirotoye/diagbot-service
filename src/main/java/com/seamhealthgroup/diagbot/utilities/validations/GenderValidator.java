package com.seamhealthgroup.diagbot.utilities.validations;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenderValidator implements ConstraintValidator<Gender, String> {

	List<String> genderPattern = Arrays.asList(new String[] {"male", "female"});

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		try {
			if (value != null) {
				return genderPattern.contains(value.toLowerCase());
			}
		} catch (Exception e) {
			log.error("DATE NOT PARSEABLE AS DATE FROMAT");
		}
		return false;
	}

}
