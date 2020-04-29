package com.study.config;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;
import com.study.properties.SocialProperties;
import com.study.dao.AutoRegistUser;
import com.study.filteradapter.SocialFilterConfigurerAdapter;

/**
 * Spring Social Configuration.
 */
@EnableSocial
@Configuration
public class SocialConfig extends SocialConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	SocialProperties socialProperties;

	@Autowired
	AutoRegistUser autoRegistUser;
	
	/**
	 *  Social登录存在数据库 */
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
				dataSource, connectionFactoryLocator, Encryptors.noOpText());
		//repository.setTablePrefix(tablePrefix); // 数据库表前缀
		repository.setConnectionSignUp(autoRegistUser); // 自动保存user
		return repository;
	}
	
	/**
	 *  Social过滤器 */
	@Bean
	public SpringSocialConfigurer socialConfigurer() {
		
		SocialFilterConfigurerAdapter socialFilter = new SocialFilterConfigurerAdapter(
														socialProperties.getQq().getFilterProcessUrl()); 
        socialFilter.signupUrl("/");
		return socialFilter;
    }

	/**
	 * 	 取出QQ服务器返回的userInfo的工具类
	 */
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator, 
						getUsersConnectionRepository(connectionFactoryLocator)) {
		};
	}

}
