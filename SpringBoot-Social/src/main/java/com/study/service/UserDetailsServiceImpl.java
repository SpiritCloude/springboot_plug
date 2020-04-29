package com.study.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;


@Component
public class UserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {

	/**
	 *  原生登录 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return buildUser(username);
	}

	/**
	 *  QQ登录 */
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		
		return buildUser(userId);
	}
	
	public SocialUserDetails buildUser(String userId){
		return new SocialUser("admin", new BCryptPasswordEncoder().encode("123456"), true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
	
}
