package com.purvanovv.user_store.repository;

import java.util.List;
import java.util.Set;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.Transaction;

public interface TransactionRepository {

	public Transaction getLastTransactionByUserId(int loggedUserId) throws DatabaseException;
	
	public void addTransactionAction(int transactionId,String action) throws DatabaseException;

	public Integer addNewTransaction(int loggedUserId, boolean status) throws DatabaseException;
	
	public List<Set<String>> getMapTransactionActions(int userId) throws DatabaseException;

}
