package com.study.weixin.connect;

import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * code换取网页授权access_token*/
public class WeiXinOAuth2Template extends OAuth2Template {
	private String clientId;
	private String clientSecret;
	private String accessTokenUrl;
	private String refreshTokenUrl ="https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	public WeiXinOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		//请求中添加client_id和client_secret参数
		super.setUseParametersForClientAuthentication(true);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.accessTokenUrl = authorizeUrl;
		this.refreshTokenUrl = accessTokenUrl;
	}

	@Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri,
                                         MultiValueMap<String, String> parameters) {

        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);
        
        //获取access_token： https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        accessTokenRequestUrl.append("?appid="+clientId);
        accessTokenRequestUrl.append("&secret="+clientSecret);
        accessTokenRequestUrl.append("&code="+authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");
        accessTokenRequestUrl.append("&redirect_uri="+redirectUri);

        return getAccessToken(accessTokenRequestUrl);
    }

	@Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {

        StringBuilder flushToken = new StringBuilder(refreshTokenUrl);
        //刷新access_token：https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
        flushToken.append("?appid="+clientId);
        flushToken.append("&grant_type=refresh_token");
        flushToken.append("&refresh_token="+refreshToken);

        return getAccessToken(flushToken);
    }

	private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {
		System.err.println("weixin获取access_token, 请求URL: "+accessTokenRequestUrl.toString());
 
        String response = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);

        System.err.println("weixin获取access_token, 响应内容: "+response);

        Map<String, String> result = null;
        try {
            result = new ObjectMapper().readValue(response, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回错误码时直接返回空
        if(result.get("errcode") != null){
            String errcode = result.get("errcode");
            String errmsg = result.get("errmsg");
            throw new RuntimeException("获取access token失败, errcode:"+errcode+", errmsg:"+errmsg);
        }

        WeiXinAccessGrant accessToken = new WeiXinAccessGrant(
        		result.get("access_token"),
        		result.get("scope"),
        		result.get("refresh_token"),
        		new Long(result.get("expires_in")),
        		result.get("openid"));

        return accessToken;
    }

    /**
     * 	构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     */
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid="+clientId+"&scope=snsapi_login";
        return url;
    }

    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }

    /**
     * 	微信返回的contentType是html/text，添加相应的HttpMessageConverter来处理。
     */
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
