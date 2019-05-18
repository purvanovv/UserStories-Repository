package com.purvanovv.user_store.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.Transaction;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Transaction getLastTransactionByUserId(int loggedUserId) throws DatabaseException {
		try {
			String sql = "select id,user_id,status,create_date from transactions where user_id = :userId order by id desc limit 1;";
			Map<String, Object> sqlParams = new HashMap<String, Object>();
			sqlParams.put("userId", loggedUserId);
			return namedParameterJdbcTemplate.query(sql, sqlParams, rs -> {
				Transaction transaction = new Transaction();
				while (rs.next()) {
					transaction.setId(rs.getInt("id"));
					transaction.setUserId(rs.getInt("user_id"));
					transaction.setStatus(rs.getBoolean("status"));
					transaction.setCratedDate(rs.getDate("create_date"));
				}
				return transaction;
			});
		} catch (Exception e) {
			throw new DatabaseException("Can't get transaction by userId!");
		}
	}

	@Override
	public void addTransactionAction(int transactionId, String action) throws DatabaseException {
		try {
			String sql = "insert into transaction_action(transaction_id,action_name) values (:transactionId,:action);";
			SqlParameterSource sqlParams = new MapSqlParameterSource().addValue("transactionId", transactionId)
					.addValue("action", action);
			namedParameterJdbcTemplate.update(sql, sqlParams);

		} catch (Exception e) {
			throw new DatabaseException("Can't insert transaction action!");
		}
	}

	@Override
	public Integer addNewTransaction(int loggedUserId, boolean status) throws DatabaseException {
		try {
			String sql = "insert into transactions(user_id,status,create_date) values (:userId,:status,NOW())";
			SqlParameterSource sqlParams = new MapSqlParameterSource().addValue("userId", loggedUserId)
					.addValue("status", status);
			KeyHolder key = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(sql, sqlParams, key);
			return key.getKey().intValue();
		} catch (Exception e) {
			throw new DatabaseException("Can't insert transaction!");
		}

	}

	@Override
	public List<Set<String>> getMapTransactionActions(int userId) throws DatabaseException {
		try {
			String sql = "select action_name from transaction_action where transaction_id = :transactionId;";
			List<Integer> transactionsId = getTransactionsId(userId);
			List<Set<String>> transActions = new ArrayList<>();

			for (Integer transactionId : transactionsId) {
				Map<String, Object> sqlParams = new HashMap<String, Object>();
				sqlParams.put("transactionId", transactionId);

				Set<String> actions = namedParameterJdbcTemplate.query(sql, sqlParams, rs -> {
					Set<String> actionsTemp = new HashSet<>();
					while (rs.next()) {
						actionsTemp.add(rs.getString("action_name"));
					}
					return actionsTemp;
				});
				transActions.add(actions);
			}

			return transActions;
		} catch (Exception e) {
			throw new DatabaseException("Can't get transactions actions!");
		}

	}

	private List<Integer> getTransactionsId(int userId) throws DatabaseException {
		try {
			String sql = "select id from transactions where user_id = :userId order by id asc;";
			Map<String, Object> sqlParams = new HashMap<String, Object>();
			sqlParams.put("userId", userId);

			List<Integer> transactions = namedParameterJdbcTemplate.query(sql, sqlParams, rs -> {
				List<Integer> transactionsTemp = new ArrayList<>();
				while (rs.next()) {
					transactionsTemp.add(rs.getInt("id"));
				}
				return transactionsTemp;
			});
			return transactions;
		} catch (Exception e) {
			throw new DatabaseException("Can't get transactions!");
		}
	}
}
