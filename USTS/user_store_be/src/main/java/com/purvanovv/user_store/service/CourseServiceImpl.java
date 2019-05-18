package com.purvanovv.user_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.Course;
import com.purvanovv.user_store.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseRepository courseRepository;

	@Override
	public List<Course> getAllCourses() throws DatabaseException {
		return courseRepository.getAllCourses();
	}

	@Override
	public List<Course> getAllMyCourses(int userId) throws DatabaseException {
		return courseRepository.getAllCoursesForUser(userId);
	}

	
}
