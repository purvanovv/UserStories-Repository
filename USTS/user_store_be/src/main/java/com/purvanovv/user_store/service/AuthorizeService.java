package com.purvanovv.user_store.service;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.exception.WrongCredentialsException;
import com.purvanovv.user_store.model.LoginDTO;
import com.purvanovv.user_store.model.UserTokenDTO;

public interface AuthorizeService {
	public UserTokenDTO createToken(LoginDTO loginDto) throws WrongCredentialsException, DatabaseException;
}
