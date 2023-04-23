package com.cognizant.app.lms.courses.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cognizant.app.lms.courses.validation.CourseDurationValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Constraint(validatedBy = CourseDurationValidator.class)
public @interface CourseDuration {
	
	public String message() default "Course Duration should be a numeric value";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
