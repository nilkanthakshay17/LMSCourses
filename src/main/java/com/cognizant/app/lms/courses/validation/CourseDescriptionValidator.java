package com.cognizant.app.lms.courses.validation;

import org.springframework.stereotype.Component;

import com.cognizant.app.lms.courses.annotation.CourseDescription;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class CourseDescriptionValidator implements ConstraintValidator<CourseDescription, String>{

	@Override
	public boolean isValid(String courseDescriptionValue, ConstraintValidatorContext context) {
		if (null != courseDescriptionValue && courseDescriptionValue.length() >= 100)
			return true;
		else
			return false;
	}
}
