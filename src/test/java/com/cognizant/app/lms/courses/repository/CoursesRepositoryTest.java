package com.cognizant.app.lms.courses.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.cognizant.app.lms.courses.entity.CourseEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CoursesRepositoryTest {

	@Autowired
	private CoursesRepository coursesRepository;
	
	CourseEntity courseEntity;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@BeforeEach
	public void setup() {
		courseEntity = new CourseEntity(
				1222,
				"55555",
				"DESD-Digital Embedded System",
				"12",
				"Embedded technologies and for designing and verification of the elecronic modules",
				"Embedded Technologies",
				"www.desd.com");
		coursesRepository.save(courseEntity);
	}
	
	@AfterEach
	public void cleanup() {
		coursesRepository.delete(courseEntity);
	}
	
	@Test
	public void testGetCourseByCourseId() {
		CourseEntity receivedCourseEntity = coursesRepository.findByCourseId("55555");
		assertEquals("55555", receivedCourseEntity.getCourseId());
		assertEquals("DESD-Digital Embedded System", receivedCourseEntity.getCourseName());
		assertEquals("12", receivedCourseEntity.getCourseDuration());
		assertEquals("Embedded Technologies", receivedCourseEntity.getTechnology());
		assertEquals("www.desd.com", receivedCourseEntity.getLaunchURL());
	}
	
	
	@Test
	public void testGetCourseByNameContaining() {
		Optional<List<CourseEntity>> receivedCourseEntity = coursesRepository.findByCourseNameContaining("desd");
		
		assertNotNull(receivedCourseEntity.get());
		assertEquals(1, (receivedCourseEntity.get()).size());
		var ent = receivedCourseEntity.get().get(0);
		
		assertEquals("55555", ent.getCourseId());
		assertEquals("DESD-Digital Embedded System", ent.getCourseName());
		assertEquals("12", ent.getCourseDuration());
		assertEquals("Embedded Technologies", ent.getTechnology());
		assertEquals("www.desd.com", ent.getLaunchURL());
	}
	
	@Test
	public void testGetCourseByDescriptionContaining() {
		Optional<List<CourseEntity>> receivedCourseEntity = coursesRepository.findByCourseDescriptionContaining("Embedded Technologies");
		
		assertNotNull(receivedCourseEntity.get());
		assertEquals(1, (receivedCourseEntity.get()).size());
		var ent = receivedCourseEntity.get().get(0);
		
		assertEquals("55555", ent.getCourseId());
		assertEquals("DESD-Digital Embedded System", ent.getCourseName());
		assertEquals("12", ent.getCourseDuration());
		assertEquals("Embedded Technologies", ent.getTechnology());
		assertEquals("www.desd.com", ent.getLaunchURL());
	}
	
	@Test
	public void testGetCourseByDurationContaining() {
		Optional<List<CourseEntity>> receivedCourseEntity = coursesRepository.findByCourseDurationContaining("12");
		
		assertNotNull(receivedCourseEntity.get());
		assertEquals(9, (receivedCourseEntity.get()).size());
		assertThat(receivedCourseEntity.get().contains(courseEntity));	
	}
	
	@Test
	public void testGetCourseByTechnologyContaining() {
		Optional<List<CourseEntity>> receivedCourseEntity = coursesRepository.findByTechnologyContaining("Embedd");
		
		assertNotNull(receivedCourseEntity.get());
		assertEquals(1, (receivedCourseEntity.get()).size());
		var ent = receivedCourseEntity.get().get(0);
		
		assertEquals("55555", ent.getCourseId());
		assertEquals("DESD-Digital Embedded System", ent.getCourseName());
		assertEquals("12", ent.getCourseDuration());
		assertEquals("Embedded Technologies", ent.getTechnology());
		assertEquals("www.desd.com", ent.getLaunchURL());
	}
	
	
}
