package com.study.config;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;
import com.study.service.UserDetailsServiceImpl;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Resource
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	SpringSocialConfigurer socialConfigurer;
	
	/**
	 * 定制请求的授权规则
	 */	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
		http.apply(socialConfigurer)
				.and()
			.authorizeRequests()// 开启 HttpSecurity 配置
			.antMatchers("/", "/auth/qq", "/auth/weixin").permitAll()
            .anyRequest().authenticated() // 所有请求都需要认证
            	.and()
			.formLogin().permitAll()
				.and()
            .logout()
            	.and()
            .csrf().disable();;
    }

	/**
	 * 定义认证规则
	 */    
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
    }

	@Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring()
    		.antMatchers("/resources/**", "/static/**");;
    }

}
