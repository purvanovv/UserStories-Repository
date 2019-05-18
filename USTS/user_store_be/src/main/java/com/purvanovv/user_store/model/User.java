package com.purvanovv.user_store.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User implements UserDetails{
	private Integer id;

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private List<UserAuthority> authorities;

	public User() {
		this.authorities = new ArrayList<>();
	}
	
	public User(Integer id, String username, String firstName, String lastName,
			List<UserAuthority> authorities) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.authorities = authorities;
	}

	public void setAuthorities(List<UserAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserAuthority> getAuthorities() {
		return authorities;
	}

	public void addAuthorities(List<UserAuthority> authorities) {
		this.authorities = authorities;

	}

	public void addRole(UserAuthority role) {
		this.authorities.add(role);
	}

	public boolean checkPassword(String password) {
		// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// return encoder.matches(password, getPassword());
		return getPassword().equals(password);
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
