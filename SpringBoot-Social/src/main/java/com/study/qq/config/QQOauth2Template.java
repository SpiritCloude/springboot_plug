package com.study.qq.config;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class QQOauth2Template extends OAuth2Template {

	public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		super.setUseParametersForClientAuthentication(true);
	}
	
	//qq返回值是string不是json所以重写
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		String respinseStr = super.getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		String[] params = StringUtils.split(respinseStr, "&");
		
		Map<String, String> map = new HashMap<String, String>(4);  
        for (int i = 0; i < params.length; i++) {  
            String[] p = params[i].split("=");  
            if (p.length == 2) {  
                map.put(p[0], p[1]);  
            }  
        }  
		String accessToken = map.get("access_token"); 
		String refreshToken = map.get("refresh_token");
		Long expiresIn = new Long(map.get("expires_in"));
		return new AccessGrant(accessToken, null, refreshToken, expiresIn);
	}

	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		//添加登录成功处理html页面的处理器
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}
}
