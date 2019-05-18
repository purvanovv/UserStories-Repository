package com.purvanovv.user_store.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.Course;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Course> getAllCourses() throws DatabaseException {
		try {
			String sql = "select id,course_title,start_date,end_date,datediff(end_date,start_date) as days from courses;";
			return namedParameterJdbcTemplate.query(sql, rs -> {
				List<Course> courses = new ArrayList<>();
				while (rs.next()) {
					Course course = new Course();
					course.setId(rs.getInt("id"));
					course.setCourseTitle(rs.getString("course_title"));
					course.setStartDate(rs.getDate("start_date").toLocalDate());
					course.setEndDate(rs.getDate("end_date").toLocalDate());
					course.setDurationInDays(rs.getInt("days"));
					courses.add(course);
				}
				return courses;
			});
		} catch (Exception e) {
			throw new DatabaseException("Can't get courses!");
		}
	}

	@Override
	public List<Course> getAllCoursesForUser(int userId) throws DatabaseException {
		try {
			String sql = "select id,course_title,start_date,end_date,datediff(end_date,start_date) as days from courses c join user_courses uc on uc.course_id = c.id where uc.user_id = :userId;";
			Map<String, Object> sqlParams = new HashMap<>();
			sqlParams.put("userId", userId);

			return namedParameterJdbcTemplate.query(sql,sqlParams, rs -> {
				List<Course> courses = new ArrayList<>();
				while (rs.next()) {
					Course course = new Course();
					course.setId(rs.getInt("id"));
					course.setCourseTitle(rs.getString("course_title"));
					course.setStartDate(rs.getDate("start_date").toLocalDate());
					course.setEndDate(rs.getDate("end_date").toLocalDate());
					course.setDurationInDays(rs.getInt("days"));
					courses.add(course);
				}
				return courses;
			});
		} catch (Exception e) {
			throw new DatabaseException("Can't get courses!");
		}

	}

}
