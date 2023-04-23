package com.cognizant.app.lms.courses.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.cognizant.app.lms.courses.validation.CourseNameValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Constraint(validatedBy = CourseNameValidator.class)
public @interface CourseName {

	public String message() default "Course Name should be minimum 20 characters";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
