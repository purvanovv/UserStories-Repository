package com.purvanovv.user_store.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.service.TopSearchesService;

@RestController
@RequestMapping("/topSearches")

public class TopSearchesController {
	@Autowired
	private TopSearchesService topSearchesService;

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping("/getTopSearches")
	public ResponseEntity<Set<String>> getTopSearches(@AuthenticationPrincipal User user) throws DatabaseException {
		ResponseEntity<Set<String>> response = new ResponseEntity<Set<String>>(
				topSearchesService.getTopSearches(user.getId()), HttpStatus.OK);
		return response;
	}
}
