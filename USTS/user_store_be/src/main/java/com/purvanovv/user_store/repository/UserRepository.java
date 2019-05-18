package com.purvanovv.user_store.repository;

import java.util.List;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.model.UserAuthority;
import com.purvanovv.user_store.model.UserDetails;

public interface UserRepository {
	public List<UserDetails> getAllUsers() throws DatabaseException;

	public User findUserByUsername(String username) throws DatabaseException;

	public List<UserAuthority> getAuthoritiesForUser(Integer userId) throws DatabaseException;
	
	public UserDetails getUserDetails(int userId) throws DatabaseException;
}
