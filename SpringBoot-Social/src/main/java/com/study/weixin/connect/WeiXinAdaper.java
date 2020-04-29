package com.study.weixin.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.study.weixin.api.WeiXin;
import com.study.weixin.api.WeiXinUser;

/**
 * 微信API适配器，将从微信API拿到的用户数据模型转换为Spring Social的标准用户数据模型。
 */
public class WeiXinAdaper implements ApiAdapter<WeiXin> {

	private String openId;
	
	public WeiXinAdaper(String openId){
	        this.openId = openId;
	}

	public WeiXinAdaper() {
	}

	/**
	 *	 用来测试当前的API是否可用
	 */
	@Override
	public boolean test(WeiXin api) {
		return true;
	}

	/**
	 * 	将微信的用户信息映射到ConnectionValues标准的数据化结构上
	 */
	@Override
	public void setConnectionValues(WeiXin api, ConnectionValues values) {
		WeiXinUser profile = api.getUserInfo(openId);
		values.setProviderUserId(profile.getOpenid());
		values.setDisplayName(profile.getNickname());
		values.setImageUrl(profile.getHeadimgurl());
	}

	@Override
	public UserProfile fetchUserProfile(WeiXin api) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatus(WeiXin api, String message) {
		// TODO Auto-generated method stub

	}

}
