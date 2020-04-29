package com.security.study.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 	需要身份认证的
 */
@RestController
public class AuthController {
	protected final Log logger = LogFactory.getLog(getClass());
	
	// 请求的url无权限, redirect当前controller，
	RequestCache requestCache = new HttpSessionRequestCache();
	
	RedirectStrategy redirectStrategy =  new DefaultRedirectStrategy();
	
	@GetMapping("/auth")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		
		// 获得引发Redirect的请求的Url(被拦截的url)
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if (savedRequest != null) {
			String redirectUrl = savedRequest.getRedirectUrl();
			
			logger.debug("无权限的URL---"+redirectUrl);
			
			//去登录页
			try {
				redirectStrategy.sendRedirect(request, response, "/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "自定义登录页";
	}
}
