package com.security.study.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.security.study.bean.JwtUser;
import com.security.study.bean.User;
import com.security.study.dao.RoleMapper;
import com.security.study.dao.UserMapper;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private UserMapper userMapper;
	
	@Resource
	private RoleMapper roleMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
		//load 用户信息
		User user = userMapper.findByUserName(username);
		JwtUser jwtUser = new JwtUser();
		jwtUser.setUsername(user.getUsername());
		jwtUser.setPassword(user.getPassword());
		//load 用户角色列表
		String roles = roleMapper.findRolesById(user.getId());
		//load 通过角色列表加载资源权限列表
		List<String> permission = roleMapper.findAuthByRoles(roles);
		//set 角色列表中角色有前缀ROLE_
		roles = "ROLE_"+roles;
		
		permission.add(roles);
		
		//List<String> authorties 类型转换为  Collection<? extends GrantedAuthority> Authorities 
		jwtUser.setAuthorities(
				AuthorityUtils.commaSeparatedStringToAuthorityList(
						String.join(",", permission)
				)
		);
		jwtUser.getAuthorities().forEach(System.out::println);
		return jwtUser;
	}

}
