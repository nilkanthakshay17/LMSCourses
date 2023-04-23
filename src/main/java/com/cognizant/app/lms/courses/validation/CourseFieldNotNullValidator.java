package com.cognizant.app.lms.courses.validation;

import org.springframework.stereotype.Component;

import com.cognizant.app.lms.courses.annotation.CourseFieldNotNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class CourseFieldNotNullValidator implements ConstraintValidator<CourseFieldNotNull, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (null == value || "" == value)
			return false;
		else
			return true;
	}	
}
