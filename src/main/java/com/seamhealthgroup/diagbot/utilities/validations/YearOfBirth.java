package com.seamhealthgroup.diagbot.utilities.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BirthYearValidator.class)
public @interface YearOfBirth {

	String message() default "Year of birth must be earlier than this year or this year ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
