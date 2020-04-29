package com.study.qq.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import com.study.qq.api.QQ;
import com.study.qq.api.QQImpl;
import com.study.qq.config.QQOauth2Template;

/**
 * 服务提供商
 * 认证服务
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ>{

	private String appId;
	
	private static String authorizeUrl = "https://graph.qq.com/oauth2.0/authorize" ;
	private static String accessTokenUrl = "https://graph.qq.com/oauth2.0/token";
	/**
	 * clientId QQ号码
	 * clientSecret QQ密码
	 * authorizeUrl 去认证的地址url
	 * accessTokenUrl 带授权码去申请令牌的url
	 */
	public QQServiceProvider(String clientId, String clientSecret) {
		// OAuth2Template 去实现了去服务器认证
		super(new QQOauth2Template(clientId, clientSecret, authorizeUrl, accessTokenUrl));
		this.appId=clientId;
	}

	
	/**
	 *  去获取userInfo的API */
	@Override
	public QQ getApi(String accessToken) {
		return new QQImpl(accessToken, appId);
	}

}
