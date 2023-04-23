package com.cognizant.app.lms.courses.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import org.springframework.validation.annotation.Validated;

import com.cognizant.app.lms.courses.validation.CourseFieldNotNullValidator;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
@Constraint(validatedBy = CourseFieldNotNullValidator.class)
@Validated
public @interface CourseFieldNotNull {
	public String message() default "Field can not be null or empty";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
