package com.purvanovv.user_store.service;

import java.util.List;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.Course;

public interface CourseService {
	public List<Course> getAllCourses() throws DatabaseException;
	
	public List<Course> getAllMyCourses(int userId) throws DatabaseException;
}
