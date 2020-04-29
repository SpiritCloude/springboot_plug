package com.study.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import com.study.properties.QQproperties;
import com.study.properties.SocialProperties;
import com.study.qq.api.QQ;
import com.study.qq.connect.QQConnectionFactory;

/**
 *	 如果系统中没有配置QQ的appId以及appSecret，此配置不起作用
 */
@Configuration
@EnableConfigurationProperties(QQproperties.class)
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

	@Autowired
	SocialProperties socialProperties;

	@Override
	protected ConnectionFactory<QQ> createConnectionFactory() {
        // 创建连接工厂,初始化参数
        return new QQConnectionFactory(
        		socialProperties.getQq().getProviderId(), 
        		socialProperties.getQq().getAppId(),
        		socialProperties.getQq().getAppSecret());
    }
}