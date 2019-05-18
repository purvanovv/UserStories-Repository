package com.purvanovv.user_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.model.UserDetails;
import com.purvanovv.user_store.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<UserDetails> getAllUsers() throws DatabaseException {
		return userRepository.getAllUsers();
	}

	@Override
	public UserDetails getUserDetails(int userId) throws DatabaseException {
		return userRepository.getUserDetails(userId);
	}
	
	

}
