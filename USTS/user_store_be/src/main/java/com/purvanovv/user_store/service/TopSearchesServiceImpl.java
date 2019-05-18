package com.purvanovv.user_store.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purvanovv.user_store.constants.Constants;
import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.repository.TransactionRepository;

import pascal_frequent_generator.AprioriFrequentItemsetGenerator;
import pascal_frequent_generator.FrequentItemsetData;

@Service
public class TopSearchesServiceImpl implements TopSearchesService {
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Set<String> getTopSearches(int userId) throws DatabaseException {
		List<Set<String>> itemsetList = transactionRepository.getMapTransactionActions(userId);

		AprioriFrequentItemsetGenerator<String> generator = new AprioriFrequentItemsetGenerator<>();
		FrequentItemsetData<String> frequendItemsets = generator.generate(itemsetList, Constants.MINIMUM_SUPPORT);

		return frequendItemsets.getFrequentItemsetList().get(frequendItemsets.getFrequentItemsetList().size() - 1);
	}
}
