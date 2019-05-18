package com.purvanovv.user_store.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.LogInfo;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.service.LogService;
import com.purvanovv.user_store.service.TransactionService;

@RestController
@RequestMapping("/log")
public class LogController {

	private final static Logger performanceLog = LoggerFactory
			.getLogger("performance." + MethodHandles.lookup().lookupClass().getCanonicalName());

	@Autowired
	private LogService logService;

	@Autowired
	private TransactionService transactionService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/getLogs", method = RequestMethod.GET)
	public ResponseEntity<List<LogInfo>> getAllLogs(@AuthenticationPrincipal User user) throws DatabaseException {
		long opStart = System.currentTimeMillis();
		transactionService.addTransactionAction(user.getId(), "GET_ALL_LOGS");
		ResponseEntity<List<LogInfo>> response = new ResponseEntity<List<LogInfo>>(logService.getAllLogs(),
				HttpStatus.OK);
		performanceLog.info("Call to LogService for getAllLogs {} ms", System.currentTimeMillis() - opStart);
		return response;
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/getAllLogsForUser", method = RequestMethod.GET)
	public ResponseEntity<List<LogInfo>> getLogsForUser(@AuthenticationPrincipal User user) throws DatabaseException {
		long opStart = System.currentTimeMillis();
		transactionService.addTransactionAction(user.getId(), "GET_LOGS_FOR_USER");
		ResponseEntity<List<LogInfo>> response = new ResponseEntity<List<LogInfo>>(
				logService.getAllLogsForUser(user.getId()), HttpStatus.OK);
		performanceLog.info("Call to LogService for get getAllLogsForUser {} ms", System.currentTimeMillis() - opStart);
		return response;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/getTopLogEvents", method = RequestMethod.GET)
	public ResponseEntity<Set<String>> getTopLogEvents() throws DatabaseException {
		ResponseEntity<Set<String>> response = new ResponseEntity<Set<String>>(logService.getTopEvents(),
				HttpStatus.OK);
		return response;
	}

}
