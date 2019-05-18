package com.purvanovv.user_store.model;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

	String authority;

	public UserAuthority() {
	}

	public UserAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
