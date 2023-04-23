package com.cognizant.app.lms.courses.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.cognizant.app.lms.courses.entity.CourseEntity;

@Repository
@EnableJpaRepositories
public interface CoursesRepository extends JpaRepository<CourseEntity, Integer>{
	public CourseEntity findByCourseId(String courseId);
	

	public Optional<List<CourseEntity>> findByCourseNameContaining(String technology);
	
	public Optional<List<CourseEntity>> findByTechnologyContaining(String technology);
	
	public Optional<List<CourseEntity>> findByCourseDescriptionContaining(String description);
	
	public Optional<List<CourseEntity>> findByCourseDurationContaining(String technology);
}
