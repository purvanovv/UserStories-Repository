package com.purvanovv.user_store.service;

import com.purvanovv.user_store.exception.DatabaseException;

public interface TransactionService {
	public void addTransactionAction(int loggedUserId,String action) throws DatabaseException;
}
