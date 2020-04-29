package com.study.weixin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import com.study.properties.SocialProperties;
import com.study.properties.WeiXinProperties;
import com.study.qq.config.SocialAutoConfigurerAdapter;
import com.study.weixin.connect.WeiXinConnectionFactory;

/**
 * 微信登录配置
 */
@Configuration
@EnableConfigurationProperties(WeiXinProperties.class)
public class WeiXinAutoConfig  extends SocialAutoConfigurerAdapter {

	@Autowired
	SocialProperties socialProperties;
	
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new WeiXinConnectionFactory(socialProperties.getWeiXin().getProviderId(),
        								socialProperties.getWeiXin().getAppId(),
        								socialProperties.getWeiXin().getAppSecret());
    }
}
