package com.study.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.study.qq.api.QQ;
import com.study.qq.api.QQuser;

/**
 * QQApi去适配OAuth2协议
 */
public class QQAdapter implements ApiAdapter<QQ> {

	@Override
	public boolean test(QQ api) {
		// 测试当前api可用性
		return true;
	}

	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {
		// QQUserInfo转换适配OAuth2协议
		QQuser userInfo = api.getUserInfo();
		
		values.setDisplayName(userInfo.getNickname());
		values.setImageUrl(userInfo.getFigureurl_qq_1());
		values.setProfileUrl(null);
		values.setProviderUserId(userInfo.getOpenId());;
		
	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatus(QQ api, String message) {
		// TODO Auto-generated method stub
		
	}

}
