package com.purvanovv.user_store.service;

import java.util.Set;

import com.purvanovv.user_store.exception.DatabaseException;

public interface TopSearchesService {
	public Set<String> getTopSearches(int userId) throws DatabaseException;
}
