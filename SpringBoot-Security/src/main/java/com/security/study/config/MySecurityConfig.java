package com.security.study.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.security.study.security.AuthFailHandler;
import com.security.study.security.AuthSuccessHandler;
import com.security.study.service.UserDetailsServiceImpl;
import com.security.study.validatefilter.ValidateCodeFilter;

@EnableWebSecurity // 启用Web安全的注解
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启基于方法的安全认证机制，controller启用注解机制的安全确认
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	UserDetailsServiceImpl userDetailsServiceImpl;

//	@Autowired
//	AuthSuccessHandler authSuccessHandler;

//	@Autowired
//	AuthFailHandler authFailureHandler;

	@Autowired
	ValidateCodeFilter validateCodeFilter;

	/*
	 * 定制请求的授权规则
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.authorizeRequests() // 开启 HttpSecurity 配置
//		  	.antMatchers("/index").permitAll()
//		  	.antMatchers("/show1").hasRole("vip1")
//		    .antMatchers("/show2", "/saveUser", "/addUser","/delete").hasRole("vip2");
		 
		http
//			.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests() // 开启 HttpSecurity 配置
			.antMatchers("/").permitAll().anyRequest().authenticated(); // 所有请求都需要认证

		// 跨站请求伪造
		// http.csrf().disable();

		/**
		 * open auto config login /login 来到登陆页 重定向到/login?error表示登陆失败
		 */
		http.formLogin()
//		 .loginPage("/auth") // controller
//		 .loginProcessingUrl("/auth/form") // 登录页表单提交的路径
//		 .successHandler(authSuccessHandler)
//		 .failureHandler(authFailureHandler)
		;

		/**
		 *	 开启自动注销 访问/logout 清除session
		 */
		http.logout();

		/**
		 *	 开启记住我功能 登陆成功以后，将cookie发给浏览器保存，以后访问页面带上这个cookie，只要通过检查就可以免登陆 点击注销会删除cookie
		 */
		http.rememberMe().rememberMeParameter("remeber");

		/**
		 * session管理
		 * SessionCreationPolicy.
		 * 			STATELESS永不创建
		 * 			NEVER Security will never create an HttpSession, but will use the HttpSession if it already exists
		 * 			IF_REQUIRED如果需要就创建
		 * 
		 */
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
	}

	/**
	 *	 定义认证规则
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// super.configure(auth);
		/**
		 * auth.inMemoryAuthentication() .passwordEncoder(passwordEncoder()) // 在Spring
		 * Security页改变了密码的格式
		 * .withUser("admin").password(passwordEncoder().encode("admin")).roles("vip1")
		 * .and()
		 * .withUser("123").password(passwordEncoder().encode("123")).roles("vip2");
		 **/

		// auth.jdbcAuthentication()...
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**");
	}

//	@Bean
//	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
//		StrictHttpFirewall firewall = new StrictHttpFirewall(); // 此处可添加别的规则,目前只设置双斜杠允许双 
//		firewall.setAllowUrlEncodedDoubleSlash(true); return firewall;
//	}

}
