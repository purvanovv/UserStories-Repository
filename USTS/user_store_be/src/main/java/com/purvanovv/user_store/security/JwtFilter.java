package com.purvanovv.user_store.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.purvanovv.user_store.exception.JwtAuthorzieException;

public class JwtFilter extends GenericFilter {

	private JwtProvider jwtProvider;

	public JwtFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {

		String requestUrl = ((HttpServletRequest) req).getRequestURI();
		if (!requestUrl.equals("/authorize/signin")) {
			try {
				String token = jwtProvider.resolveToken((HttpServletRequest) req);
				if (token != null && jwtProvider.validateToken(token)) {
					Authentication auth = jwtProvider.getAuthentication(token);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (JwtAuthorzieException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		filterChain.doFilter(req, res);
	}
}
