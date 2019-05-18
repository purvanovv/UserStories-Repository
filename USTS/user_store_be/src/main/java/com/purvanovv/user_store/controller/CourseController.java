package com.purvanovv.user_store.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.Course;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	private final static Logger performanceLog = LoggerFactory
			.getLogger("performance." + MethodHandles.lookup().lookupClass().getCanonicalName());

	@Autowired
	CourseService courseService;

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping("/getAllCourses")
	public ResponseEntity<List<Course>> getAllCourses(@AuthenticationPrincipal User user) throws DatabaseException {
		long opStart = System.currentTimeMillis();
		ResponseEntity<List<Course>> response = new ResponseEntity<List<Course>>(courseService.getAllCourses(),
				HttpStatus.OK);
		performanceLog.info("Call to CourseService for getAllCourses {} ms", System.currentTimeMillis() - opStart);
		return response;
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping("/getAllMyCourses")
	public ResponseEntity<List<Course>> getAllMyCourses(@AuthenticationPrincipal User user) throws DatabaseException {
		long opStart = System.currentTimeMillis();
		ResponseEntity<List<Course>> response = new ResponseEntity<List<Course>>(
				courseService.getAllMyCourses(user.getId()), HttpStatus.OK);
		performanceLog.info("Call to CourseService for getAllMyCourses {} ms", System.currentTimeMillis() - opStart);
		return response;
	}
}
