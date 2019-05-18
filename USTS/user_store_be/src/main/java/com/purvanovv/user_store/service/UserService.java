package com.purvanovv.user_store.service;

import java.util.List;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.model.UserDetails;

public interface UserService {
	public List<UserDetails> getAllUsers() throws DatabaseException;

	public UserDetails getUserDetails(int userId) throws DatabaseException;
}
