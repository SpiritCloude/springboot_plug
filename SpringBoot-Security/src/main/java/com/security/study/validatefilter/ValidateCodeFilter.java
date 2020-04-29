package com.security.study.validatefilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.study.security.AuthFailHandler;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter{

	@Autowired
	AuthFailHandler authFailureHandler;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if ( request.getRequestURI().equals("/auth/form") ) {
			
			try {
				validate(request);
			} catch (AuthenticationException e) {
				authFailureHandler.onAuthenticationFailure(request, response, e);
			}
			
		};
		
		filterChain.doFilter(request, response);
	}

	private void validate(HttpServletRequest request) {
		//验证图形验证码
		String code = request.getParameter("code");
		
		//redis获得验证码  或 session
		
	}

}
