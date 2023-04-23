package com.cognizant.app.lms.courses.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourseFieldNotNullValidatorTest {

	@Autowired
	private CourseFieldNotNullValidator courseFieldNotNullValidator;


	@Test
	public void testValidatorForNullCourseField() {
		String courseField = null;
		boolean result = courseFieldNotNullValidator.isValid(courseField, null);
		
		assertEquals(false,result);
	}
	
	@Test
	public void testValidatorForEmptyCourseField() {
		String courseField = "";
		boolean result = courseFieldNotNullValidator.isValid(courseField, null);
		
		assertEquals(false,result);
	}
	
	@Test
	public void testValidatorForValidCourseField() {
		String courseField = "WER";
		boolean result = courseFieldNotNullValidator.isValid(courseField, null);
		
		assertEquals(true,result);
	}
}

