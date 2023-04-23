package com.cognizant.app.lms.courses.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourseDescriptionValidatorTest {

	@Autowired
	private CourseDescriptionValidator courseDescriptionValidator;
	
	@Test
	public void testValidatorForNullDescription() {
		String courseDescription = null;
		
		boolean result = courseDescriptionValidator.isValid(courseDescription, null);
		
		assertEquals(false, result);
	}
	
	@Test
	public void testValidatorForValidDescription() {
		String courseDescription = "This course includes the latest technologies for advanced computing and data analysis, advanced training on java, advanced java, database etc.";
		
		boolean result = courseDescriptionValidator.isValid(courseDescription, null);
		
		assertEquals(true, result);
	}
	
	
	
	@Test
	public void testValidatorForInvalidDescription() {
		String courseDescription = "This course includes the latest technologies.";
		
		boolean result = courseDescriptionValidator.isValid(courseDescription, null);
		
		assertEquals(false, result);
	}

}
