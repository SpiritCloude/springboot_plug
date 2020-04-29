package com.study.filteradapter;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * QQ登录过滤器
 * 
 */
public class SocialFilterConfigurerAdapter extends SpringSocialConfigurer{

	private String filterProcessesUrl;
	
	public SocialFilterConfigurerAdapter(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
	
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
		filter.setFilterProcessesUrl(filterProcessesUrl);
		
		return (T) filter;
	}
	
}
