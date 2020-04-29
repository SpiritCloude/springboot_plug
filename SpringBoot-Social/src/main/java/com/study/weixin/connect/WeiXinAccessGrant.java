package com.study.weixin.connect;

import org.springframework.social.oauth2.AccessGrant;

public class WeiXinAccessGrant extends AccessGrant {

    private String openId;
    
    public WeiXinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String openId) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openId = openId;
    }

	public String getOpenId() {
		return openId;
	}
}
   