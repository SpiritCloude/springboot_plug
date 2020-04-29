package com.security.study.bean;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class JwtUser implements UserDetails {
	
	String Password;
	String Username;
	boolean AccountNonExpired = true; // 帐户未过期
	boolean AccountNonLocked = true; // 帐户未锁定
	
	boolean CredentialsNonExpired = true; // 凭据未过期；
	boolean Enabled = true; // 已启用
	Collection<? extends GrantedAuthority> Authorities;//用户权限集
	//Set<SimpleGrantedAuthority> Authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Authorities;
	}

	@Override
	public String getPassword() {
		return Password;
	}

	@Override
	public String getUsername() {
		return Username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return AccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return AccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return CredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return Enabled;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		AccountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		AccountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		CredentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Authorities = authorities;
	}

	
	
	@Override
	public String toString() {
		return "JwtUser [Password=" + Password + ", Username=" + Username + ", AccountNonExpired=" + AccountNonExpired
				+ ", AccountNonLocked=" + AccountNonLocked + ", CredentialsNonExpired=" + CredentialsNonExpired
				+ ", Enabled=" + Enabled + ", Authorities=" + Authorities + "]";
	}

}
