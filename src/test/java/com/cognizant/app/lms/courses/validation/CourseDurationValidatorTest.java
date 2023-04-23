package com.cognizant.app.lms.courses.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ModelMap;

@SpringBootTest
class CourseDurationValidatorTest {

	@Autowired
	private CourseDurationValidator courseDurationValidator;
	
	
	@Test
	public void  testValidatorForNullDuration() {
		String courseDuration = null;
		boolean result = courseDurationValidator.isValid(courseDuration, null);
		
		assertEquals(false, result);
	}

	@Test
	public void  testValidatorForValidDuration() {
		String courseDuration = "9";
		boolean result = courseDurationValidator.isValid(courseDuration, null);
		
		assertEquals(true, result);
	}

	@Test
	public void  testValidatorForInvalidDuration() {
		String courseDuration = "EF";
		boolean result = courseDurationValidator.isValid(courseDuration, null);
		
		assertEquals(false, result);
	}
}
