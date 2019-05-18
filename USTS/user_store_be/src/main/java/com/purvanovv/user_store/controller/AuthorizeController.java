package com.purvanovv.user_store.controller;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.exception.WrongCredentialsException;
import com.purvanovv.user_store.model.LoginDTO;
import com.purvanovv.user_store.model.UserTokenDTO;
import com.purvanovv.user_store.service.AuthorizeService;

@RestController
@RequestMapping("/authorize")
public class AuthorizeController {

	private final static Logger performanceLog = LoggerFactory
			.getLogger("performance." + MethodHandles.lookup().lookupClass().getCanonicalName());

	@Autowired
	AuthorizeService authorizeService;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<UserTokenDTO> login(@RequestBody LoginDTO loginDto)
			throws WrongCredentialsException, DatabaseException {
		long opStart = System.currentTimeMillis();
		ResponseEntity<UserTokenDTO> response = ResponseEntity.status(HttpStatus.OK)
				.body(authorizeService.createToken(loginDto));
		performanceLog.info("Call to AuthorizeService for login {} ms", System.currentTimeMillis() - opStart);
		return response;
	}

}
