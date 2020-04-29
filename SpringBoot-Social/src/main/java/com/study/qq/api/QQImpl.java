package com.study.qq.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 资源服务 
 * 获取用户信息
 * */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

	// 获取用户id(QQ号码)
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    
    // 获取用户信息(access_token由父类提供)
    private static final String URL_GET_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    
    // 申请QQ登录成功后，分给应用的Appid
	private String appId;
	
	// 用户Id(QQ号码)
	private String openId;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public QQImpl(String accessToken, String appId) {
		// 在使用父类的restTemplate发请求时把accessToken作为查询参数放进QQ_URL_GET_OPENID,
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		
		this.appId = appId;
		String url = String.format(URL_GET_OPENID, accessToken);
		String result = super.getRestTemplate().getForObject(url, String.class);
		
		System.out.println(result);
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = objectMapper.readValue(result, Map.class);
		} catch (Exception e) {
			new RuntimeException("json 转 openID 异常", e);
		} 
		
		this.openId = (String) map.get("openid"); 
		
	}
	
	
	@Override
	public QQuser getUserInfo() {
		// 官方https://graph.qq.com/user/get_user_info?accessToken=%s&oauth_consumer_key=%s&openid=%s
		// accessToken由父类AbstractOAuth2ApiBinding处理
		String url = String.format(URL_GET_INFO, appId, openId);
		QQuser userInfo = null ;
		//获取的用户信息
		String user = super.getRestTemplate().getForObject(url, String.class);
		try {
			userInfo = objectMapper.readValue(user, QQuser.class);
		} catch (Exception e) {
			new RuntimeException("json 转 userinfo 异常", e);
		}
		
		userInfo.setOpenId(openId);
		return userInfo;
	}

}
