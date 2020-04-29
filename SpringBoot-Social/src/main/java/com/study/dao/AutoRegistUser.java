package com.study.dao;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import com.study.entity.SocialUserInfo;

/**
 * 	自动把QQ用户注册到userconnection
 */
@Component
public class AutoRegistUser implements ConnectionSignUp {

	/**
	 * 	根据社交用户信息默认创建用户并返回用户唯一标识 */
	@Override
	public String execute(Connection<?> connection) {
		SocialUserInfo socialUserInfo = new SocialUserInfo();

		socialUserInfo.setUserId(connection.getKey().getProviderUserId());
		socialUserInfo.setProviderId(connection.getKey().getProviderId());
		socialUserInfo.setProviderUserId(connection.getKey().getProviderUserId());
		socialUserInfo.setImageUrl(connection.getImageUrl());
		socialUserInfo.setDisplayName(connection.getDisplayName());
		socialUserInfo.setProfileUrl(connection.getProfileUrl());
		
		return connection.getKey().getProviderUserId();
	}

}
