package com.purvanovv.user_store.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.model.UserAuthority;
import com.purvanovv.user_store.model.UserDetails;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserDetails> getAllUsers() throws DatabaseException {
		try {
			String sql = "select id,username,first_name,last_name,phone_number,email,date_of_birth,city,country from users;";
			List<UserDetails> users = namedParameterJdbcTemplate.query(sql, rs -> {
				List<UserDetails> templUsers = new ArrayList<UserDetails>();
				while (rs.next()) {
					UserDetails user = new UserDetails();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setPhoneNumber(rs.getString("phone_number"));
					user.setEmail(rs.getString("email"));
					user.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
					user.setCity(rs.getString("city"));
					user.setCountry(rs.getString("country"));
					templUsers.add(user);
				}
				return templUsers;
			});

			for (UserDetails user : users) {
				List<UserAuthority> authorities = getAuthoritiesForUser(user.getId());
				user.setAuthorities(authorities);
			}
			return users;
		} catch (Exception e) {
			throw new DatabaseException("Can't get users!");
		}
	}

	@Override
	public User findUserByUsername(String username) throws DatabaseException {
		try {
			String sql = "select u.id,u.username,u.password,u.first_name,u.last_name from users u where u.username = :username;";
			Map<String, String> sqlParams = new HashMap<String, String>();
			sqlParams.put("username", username);
			User findUser = namedParameterJdbcTemplate.query(sql, sqlParams, rs -> {
				User userFromDb = new User();
				while (rs.next()) {
					userFromDb.setId(rs.getInt("id"));
					userFromDb.setUsername(rs.getString("username"));
					userFromDb.setPassword(rs.getString("password"));
					userFromDb.setFirstName(rs.getString("first_name"));
					userFromDb.setLastName(rs.getString("last_name"));
				}
				return userFromDb;
			});

			List<UserAuthority> userRoles = getAuthoritiesForUser(findUser.getId());
			findUser.addAuthorities(userRoles);
			return findUser;
		} catch (Exception e) {
			throw new DatabaseException("Can't get user!");
		}
	}

	@Override
	public List<UserAuthority> getAuthoritiesForUser(Integer userId) throws DatabaseException {
		try {
			String sql = "select distinct(r.id),r.role_name from roles r join user_role ur on ur.role_id = r.id and ur.user_id = :userId;";
			Map<String, Integer> sqlParams = new HashMap<String, Integer>();
			sqlParams.put("userId", userId);
			List<UserAuthority> findRoles = namedParameterJdbcTemplate.query(sql, sqlParams, rs -> {
				List<UserAuthority> rolesFromDb = new ArrayList<UserAuthority>();
				while (rs.next()) {
					UserAuthority role = new UserAuthority();
					role.setAuthority(rs.getString("role_name"));
					rolesFromDb.add(role);
				}
				return rolesFromDb;
			});
			return findRoles;
		} catch (Exception e) {
			throw new DatabaseException("Can't get user authorities!");
		}
	}

	@Override
	public UserDetails getUserDetails(int userId) throws DatabaseException {
		try {
			String sql = "select username,first_name,last_name,phone_number,email,date_of_birth,city,country from users where id = :userId limit 1;";
			Map<String, Integer> sqlParams = new HashMap<String, Integer>();
			sqlParams.put("userId", userId);
			return namedParameterJdbcTemplate.query(sql, sqlParams, rs -> {
				UserDetails user = new UserDetails();
				while (rs.next()) {
					user.setUsername(rs.getString("username"));
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setPhoneNumber(rs.getString("phone_number"));
					user.setEmail(rs.getString("email"));
					user.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
					user.setCity(rs.getString("city"));
					user.setCountry(rs.getString("country"));
				}
				return user;
			});

		} catch (Exception e) {
			throw new DatabaseException("Can't get user details!");
		}
	}

}
