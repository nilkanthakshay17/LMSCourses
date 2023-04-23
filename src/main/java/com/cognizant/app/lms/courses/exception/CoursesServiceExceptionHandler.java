package com.cognizant.app.lms.courses.exception;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CoursesServiceExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public HashMap<String,String> handleMethodArgumentNotValidaException(MethodArgumentNotValidException exception){
		
		HashMap<String, String> errorMap = new HashMap<>();
		exception.getBindingResult().getFieldErrors().forEach(
				errorValue-> {
					errorMap.put(errorValue.getField(), errorValue.getDefaultMessage());
				}
		);
		return errorMap;
	}
	

	@ExceptionHandler(CoursesServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleCoursesServiceException(CoursesServiceException exception) {
		return exception.getMessage();
	}
}
