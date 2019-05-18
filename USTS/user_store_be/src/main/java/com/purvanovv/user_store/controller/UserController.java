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
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.model.UserDetails;
import com.purvanovv.user_store.service.TransactionService;
import com.purvanovv.user_store.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final static Logger performanceLog = LoggerFactory
			.getLogger("performance." + MethodHandles.lookup().lookupClass().getCanonicalName());

	@Autowired
	UserService userService;

	@Autowired
	TransactionService transactionService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/getAllUsers")
	public ResponseEntity<List<UserDetails>> getAllUsers(@AuthenticationPrincipal User user) throws DatabaseException {
		long opStart = System.currentTimeMillis();
		transactionService.addTransactionAction(user.getId(), "GET_ALL_USERS");
		ResponseEntity<List<UserDetails>> response = new ResponseEntity<List<UserDetails>>(userService.getAllUsers(), HttpStatus.OK);
		performanceLog.info("Call to UserService for getAllUsers {} ms", System.currentTimeMillis() - opStart);
		return response;
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping("/getUserDetails")
	public ResponseEntity<UserDetails> getUserDetails(@AuthenticationPrincipal User user) throws DatabaseException {
		long opStart = System.currentTimeMillis();
		transactionService.addTransactionAction(user.getId(), "GET_USER_DETAILS");
		ResponseEntity<UserDetails> response = new ResponseEntity<UserDetails>(userService.getUserDetails(user.getId()),
				HttpStatus.OK);
		performanceLog.info("Call to UserService for getUserDetails {} ms", System.currentTimeMillis() - opStart);
		return response;
	}
}
