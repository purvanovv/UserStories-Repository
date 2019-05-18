package com.purvanovv.user_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purvanovv.user_store.constants.TransactionStatuses;
import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.Transaction;
import com.purvanovv.user_store.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public void addTransactionAction(int loggedUserId, String action) throws DatabaseException {
		Transaction transaction = transactionRepository.getLastTransactionByUserId(loggedUserId);

		if (transaction.getCratedDate() != null && transaction.getStatus() == TransactionStatuses.NOT_COMMIT) {
			transactionRepository.addTransactionAction(transaction.getId(), action);
		} else {
			int transactionId = transactionRepository.addNewTransaction(loggedUserId, false);
			transactionRepository.addTransactionAction(transactionId, action);
		}

	}

}
