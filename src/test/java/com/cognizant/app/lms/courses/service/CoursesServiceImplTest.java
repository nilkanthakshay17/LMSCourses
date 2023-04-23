package com.cognizant.app.lms.courses.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.app.lms.courses.dto.CourseDTO;
import com.cognizant.app.lms.courses.entity.CourseEntity;
import com.cognizant.app.lms.courses.repository.CoursesRepository;

@SpringBootTest
class CoursesServiceImplTest {

	@Autowired
	private CoursesServiceImpl coursesServiceImpl;

	@MockBean
	private CoursesRepository coursesRepository;
	
	@BeforeEach
	public void setup() {
		CourseEntity courseEntity = new CourseEntity(
				1456,
				"99999",
				"DAC-Digital Advanced Computing",
				"999",
				"This course contains all the latest technologies and related stack for advanced computing",
				"Java999",
				"www.dac999.com");
		List<CourseEntity> entList = new ArrayList<>();
		entList.add(courseEntity);
		Optional<List<CourseEntity>> opEntlist = Optional.of(entList);
		Mockito.when(coursesRepository.findByCourseId("99999")).thenReturn(courseEntity);
		Mockito.when(coursesRepository.findByCourseNameContaining("DAC")).thenReturn(opEntlist);
		Mockito.when(coursesRepository.findByCourseDescriptionContaining("advanced computing")).thenReturn(opEntlist);
		Mockito.when(coursesRepository.findByCourseDurationContaining("999")).thenReturn(opEntlist);
		Mockito.when(coursesRepository.findByTechnologyContaining("Java999")).thenReturn(opEntlist);
	}
	
	@Test
	public void testFindByCourseId() {
		CourseDTO courseDto = coursesServiceImpl.getCourseById("99999");
		
		assertEquals("99999", courseDto.getCourseId());
		assertEquals("DAC-Digital Advanced Computing", courseDto.getCourseName());
		assertEquals("999", courseDto.getCourseDuration());
		assertEquals("Java999", courseDto.getTechnology());
		assertEquals("www.dac999.com", courseDto.getLaunchURL());
	}
	
	@Test
	public void testFindCourseByNameContaining() {
		List<CourseDTO> courseDto = coursesServiceImpl.getAllCoursesByName("DAC");
		
		assertEquals("99999", courseDto.get(0).getCourseId());
		assertEquals("DAC-Digital Advanced Computing", courseDto.get(0).getCourseName());
		assertEquals("999", courseDto.get(0).getCourseDuration());
		assertEquals("Java999", courseDto.get(0).getTechnology());
		assertEquals("www.dac999.com", courseDto.get(0).getLaunchURL());
	}
	
	@Test
	public void testFindCourseByDescriptionContaining() {
		List<CourseDTO> courseDto = coursesServiceImpl.getAllCoursesByDescription("advanced computing");
		
		assertEquals("99999", courseDto.get(0).getCourseId());
		assertEquals("DAC-Digital Advanced Computing", courseDto.get(0).getCourseName());
		assertEquals("999", courseDto.get(0).getCourseDuration());
		assertEquals("Java999", courseDto.get(0).getTechnology());
		assertEquals("www.dac999.com", courseDto.get(0).getLaunchURL());
	}
	
	@Test
	public void testFindCourseByDurationContaining() {
		List<CourseDTO> courseDto = coursesServiceImpl.getAllCoursesByDuration("999");
		
		assertEquals("99999", courseDto.get(0).getCourseId());
		assertEquals("DAC-Digital Advanced Computing", courseDto.get(0).getCourseName());
		assertEquals("999", courseDto.get(0).getCourseDuration());
		assertEquals("Java999", courseDto.get(0).getTechnology());
		assertEquals("www.dac999.com", courseDto.get(0).getLaunchURL());
	}
	
	@Test
	public void testFindCourseByTechnologyContaining() {
		List<CourseDTO> courseDto = coursesServiceImpl.getAllCoursesByTechnology("Java999");
		
		assertEquals("99999", courseDto.get(0).getCourseId());
		assertEquals("DAC-Digital Advanced Computing", courseDto.get(0).getCourseName());
		assertEquals("999", courseDto.get(0).getCourseDuration());
		assertEquals("Java999", courseDto.get(0).getTechnology());
		assertEquals("www.dac999.com", courseDto.get(0).getLaunchURL());
	}
}
