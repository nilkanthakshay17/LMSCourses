package com.cognizant.app.lms.courses.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.app.lms.courses.dto.CourseDTO;
import com.cognizant.app.lms.courses.exception.CoursesServiceException;
import com.cognizant.app.lms.courses.model.CourseRequestModel;
import com.cognizant.app.lms.courses.model.CourseResponseModel;
import com.cognizant.app.lms.courses.model.UserLoginRequest;
import com.cognizant.app.lms.courses.service.CoursesServiceInt;
import com.cognizant.app.lms.courses.util.CoursesConstant;



@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "http://localhost:3000")
public class CoursesController {
	
	@Autowired
	private CoursesServiceInt coursesServiceInt;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CoursesController(){
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@GetMapping("/status")
	public String getStatus() {
		return "Working....";
	}

	@PostMapping
	public ResponseEntity<CourseResponseModel> registerTheCourse(@RequestBody @Validated CourseRequestModel courseRequestModel){
		CourseDTO courseDtoToRegister = modelMapper.map(courseRequestModel, CourseDTO.class);
		
		CourseDTO registerdCourseDto = coursesServiceInt.registerCourse(courseDtoToRegister);
		
		CourseResponseModel registeredCourseResponse = modelMapper.map(registerdCourseDto, CourseResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredCourseResponse);
	}
	
	@GetMapping
	public ResponseEntity<List<CourseResponseModel>> getAllCourses(){
		List<CourseDTO> allCourseDtos = coursesServiceInt.getAllCourses();
		
		List<CourseResponseModel> allCourseResponse = new ArrayList<>();
		
		for(CourseDTO cd :allCourseDtos) {
			allCourseResponse.add(modelMapper.map(cd, CourseResponseModel.class));
		}
		return ResponseEntity.status(HttpStatus.OK).body(allCourseResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CourseResponseModel> getCourseById(@PathVariable(name = "id")String id){
		CourseDTO courseDto = coursesServiceInt.getCourseById(id);
		CourseResponseModel courseResponse = modelMapper.map(courseDto, CourseResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK).body(courseResponse);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<CourseResponseModel> updateCourseById(@RequestBody CourseRequestModel updateRequest, @PathVariable(name = "id")String id){
		CourseDTO courseDtoToUpdate = modelMapper.map(updateRequest, CourseDTO.class);
		CourseDTO updatedCourseDto = coursesServiceInt.updateCourseById(courseDtoToUpdate,id);
		CourseResponseModel updatedCourseResponse = modelMapper.map(updatedCourseDto, CourseResponseModel.class);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCourseResponse);
	}
	
	@DeleteMapping
	public String deleteAllCourses() {
		return coursesServiceInt.deleteAllCourses();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,CourseResponseModel>> deleteCourseById(@PathVariable(name = "id")String id){
		CourseDTO deletedCourseDto = coursesServiceInt.deleteCourseById(id);
		CourseResponseModel deletedCourseResponse = modelMapper.map(deletedCourseDto, CourseResponseModel.class);
		Map<String,CourseResponseModel> deleteResMap = new HashMap<>();
		deleteResMap.put("Deleted Course-", deletedCourseResponse);
		return ResponseEntity.status(HttpStatus.OK).body(deleteResMap);
	}
	
	@PostMapping(value = "/auth/login",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
	        produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	
	public String loginReq(@RequestBody UserLoginRequest req) {
		return "Login Courses";
	}
	
//	@GetMapping("/name")
//	public ResponseEntity<?> getCoursesByName(@RequestParam(name = "name") String name){
//		
//		List<CourseResponseModel> courseList = new ArrayList<>();
//		System.out.println(name.trim().toLowerCase());
//		var dtos = coursesServiceInt.getAllCoursesByName(name.trim().toLowerCase());
//		for(CourseDTO dt:dtos) {
//			courseList.add(modelMapper.map(dt, CourseResponseModel.class));
//		}
//		
//		return ResponseEntity.status(HttpStatus.OK).body(courseList);
//	}
//	
//	@GetMapping("/description")
//	public ResponseEntity<?> getCoursesByDescription(@RequestParam(name = "description") String description){
//		
//		List<CourseResponseModel> courseList = new ArrayList<>();
//		System.out.println(description.trim().toLowerCase());
//		var dtos = coursesServiceInt.getAllCoursesByDescription(description.trim().toLowerCase());
//		for(CourseDTO dt:dtos) {
//			courseList.add(modelMapper.map(dt, CourseResponseModel.class));
//		}
//		
//		return ResponseEntity.status(HttpStatus.OK).body(courseList);
//	}
//	
//	
//	@GetMapping("/duration")
//	public ResponseEntity<?> getCoursesByDuration(@RequestParam(name = "duration") String duration){
//		
//		List<CourseResponseModel> courseList = new ArrayList<>();
//		System.out.println(duration.trim().toLowerCase());
//		var dtos = coursesServiceInt.getAllCoursesByDuration(duration.trim().toLowerCase());
//		for(CourseDTO dt:dtos) {
//			courseList.add(modelMapper.map(dt, CourseResponseModel.class));
//		}
//		
//		return ResponseEntity.status(HttpStatus.OK).body(courseList);
//	}
//	
//	@GetMapping("/technology")
//	public ResponseEntity<?> getCoursesByTechnology(@RequestParam(name = "technology") String technology){
//		
//		List<CourseResponseModel> courseList = new ArrayList<>();
//		System.out.println(technology.trim().toLowerCase());
//		var dtos = coursesServiceInt.getAllCoursesByTechnology(technology.trim().toLowerCase());
//		for(CourseDTO dt:dtos) {
//			courseList.add(modelMapper.map(dt, CourseResponseModel.class));
//		}
//		
//		return ResponseEntity.status(HttpStatus.OK).body(courseList);
//	}
	
	@GetMapping("/{course_param}/{search_param}")
	public ResponseEntity<?> getCourseInfo(@PathVariable(name = "course_param")String courseParam, @PathVariable(name = "search_param")String searchParam){

		List<CourseResponseModel> courseList = new ArrayList<>();
		List<CourseDTO> dtos =null;
		switch(courseParam) {
		case CoursesConstant.COURSE_NAME: {
			 dtos = coursesServiceInt.getAllCoursesByName(searchParam);
			break;
		}
		case CoursesConstant.COURSE_DESCRIPTION: {
			 dtos = coursesServiceInt.getAllCoursesByDescription(searchParam);
			break;
		}
		case CoursesConstant.COURSE_DURATION: {
			 dtos = coursesServiceInt.getAllCoursesByDuration(searchParam);
			break;
		}
		case CoursesConstant.TECHNOLOGY: {
			 dtos = coursesServiceInt.getAllCoursesByTechnology(searchParam);
			break;
		}
		default: {
			throw new CoursesServiceException("Wrong URI path - /course/"+courseParam);
		}
		}
		for(CourseDTO dt:dtos) {
			courseList.add(modelMapper.map(dt, CourseResponseModel.class));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(courseList);
			 
	}
}
