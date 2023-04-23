package com.cognizant.app.lms.courses.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourseNameValidatorTest {

	@Autowired
	private CourseNameValidator courseNameValidator;
	
	@Test
	public void testValidatorForNullName() {
		String courseName= null;
		
		boolean result = courseNameValidator.isValid(courseName, null);
		
		assertEquals(false, result);
	}
	
	@Test
	public void testValidatorForValidName() {
		String courseName= "DAC-Digital Advanced Computing";
		
		boolean result = courseNameValidator.isValid(courseName, null);
		
		assertEquals(true, result);
	}
	
	@Test
	public void testValidatorForInvalidName() {
		String courseName= "DAC";
		
		boolean result = courseNameValidator.isValid(courseName, null);
		
		assertEquals(false, result);
	}
}
