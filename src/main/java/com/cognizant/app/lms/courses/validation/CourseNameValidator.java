package com.cognizant.app.lms.courses.validation;

import org.springframework.stereotype.Component;

import com.cognizant.app.lms.courses.annotation.CourseName;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class CourseNameValidator implements ConstraintValidator<CourseName, String> {

	@Override
	public boolean isValid(String courseNameValue, ConstraintValidatorContext context) {
		if (null != courseNameValue && courseNameValue.length() >= 20)
			return true;
		else
			return false;
	}

}
