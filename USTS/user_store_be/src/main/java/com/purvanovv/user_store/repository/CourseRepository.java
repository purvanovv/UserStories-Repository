package com.purvanovv.user_store.repository;

import java.util.List;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.Course;

public interface CourseRepository {
	public List<Course> getAllCourses() throws DatabaseException;
	
	public List<Course> getAllCoursesForUser(int userId) throws DatabaseException;
}
