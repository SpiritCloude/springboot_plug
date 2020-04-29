package com.study.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocialProperties {

	@Autowired
	QQproperties qq;

	@Autowired
	WeiXinProperties weiXin;

	public QQproperties getQq() {
		return qq;
	}

	public WeiXinProperties getWeiXin() {
		return weiXin;
	}
}
