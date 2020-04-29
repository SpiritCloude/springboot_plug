package com.study.weixin.api;

/**
 * weixin API
 */
public interface WeiXin {
	
	/**
	 *	获取用户信息 */
	WeiXinUser getUserInfo(String openId);
}
