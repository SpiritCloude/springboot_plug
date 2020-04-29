package com.study.weixin.api;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeiXinImpl extends AbstractOAuth2ApiBinding implements WeiXin {

	// getUserInfo(access_token由父类提供)https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
    private static final String URL_GET_INFO = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN&openid=";
    
    public WeiXinImpl(String accessToken) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
	}
    
    /**
     * 	 默认注册的HttpMessageConverter字符集为ISO-8859-1,而微信返回的是UTF-8,因此必须覆盖原来的方法 */
	@Override
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters= super.getMessageConverters();
		messageConverters.remove(0);
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return messageConverters;
	}
    
	/**
	 *	获取用户信息 */
	@Override
	public WeiXinUser getUserInfo(String openId) {
		// https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
		String url = URL_GET_INFO + openId;
		String response = super.getRestTemplate().getForObject(url, String.class);
		WeiXinUser weiXinUser = null;
		try {
			weiXinUser = new ObjectMapper().readValue(response,WeiXinUser.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
		return weiXinUser;
	}

}
