package com.purvanovv.user_store.security;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.purvanovv.user_store.exception.JwtAuthorzieException;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.model.UserAuthority;
import com.purvanovv.user_store.model.UserTokenDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
	@Value("${security.jwt.token.secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds; // 1h

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public UserTokenDTO createToken(User user) {
		UserTokenDTO userToken = new UserTokenDTO();
		userToken.setUser(user);
		
		Claims claims = Jwts.claims().setSubject(user.getUsername());
		claims.put("authorities", user.getAuthorities());
		claims.put("firstName", user.getFirstName());
		claims.put("lastName", user.getLastName());
		claims.put("username", user.getUsername());
		claims.put("userId", user.getId());
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		String token = Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).setId(user.getId().toString())
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
		userToken.setToken(token);
		return userToken;
	}

	public boolean validateToken(String token) throws JwtAuthorzieException {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new JwtAuthorzieException("Expired or invalid JWT token");
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		List<HashMap<String, String>> authoritiesMap = (List<HashMap<String, String>>) claims.get("authorities");
		List<UserAuthority> authorities = new ArrayList<>();
		for (HashMap<String, String> pair : authoritiesMap) {
			UserAuthority authority = new UserAuthority(pair.get("authority"));
			authorities.add(authority);
		}

		User user = new User((Integer) claims.get("userId"), (String) claims.get("username"),
				(String) claims.get("firstName"), (String) claims.get("lastName"), authorities);

		return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
